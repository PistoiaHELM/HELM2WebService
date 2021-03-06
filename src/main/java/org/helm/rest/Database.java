/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.helm.rest;

import java.util.*;
import java.io.*;
import org.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
/**
 *
 * @author tyuan
 */
public class Database {
    int maxkey = 0;
    String path;
    String[] keys;
    String seperator = "|";
    ArrayList<String[]> rows = new ArrayList<String[]>();
    
    public Database(String path, String[] keys) {
        this.path = path;
        this.keys = keys;
        try {
            Load();
        }
        catch (Exception e) {
            // throw e;
        }
    }
    
    public String[] getKeys() {
        return keys;
    }
    
    public ArrayList<JSONObject> AsJSON() {
        ArrayList<JSONObject> list = new ArrayList();
        for (int i = 0; i < rows.size(); ++i)
            list.add(Row2Json(rows.get(i)));
        return list;
    }
    
    ArrayList<String[]> Filter(String key1, String value1, String key2, String value2, String startwithkey, String startwithvalue) {
        int f1 = StringUtils.isEmpty(value1) ? -1 : java.util.Arrays.asList(keys).indexOf(key1);
        int f2 = StringUtils.isEmpty(value2) ? -1 :java.util.Arrays.asList(keys).indexOf(key2);
        int f3 = StringUtils.isEmpty(startwithvalue) ? -1 :java.util.Arrays.asList(keys).indexOf(startwithkey);
        if (f1 < 0 && f2 < 0 && f3 < 0)
            return rows;
        
        if (!StringUtils.isEmpty(startwithvalue))
            startwithvalue = startwithvalue.toLowerCase();
        
        ArrayList<String[]> ret = new ArrayList();
        for (int i = 0; i < rows.size(); ++i) {
            String[] r = rows.get(i);
            if ((f1 < 0 || value1.equals(r[f1])) && (f2 < 0 || value2.equals(r[f2])) && (f3 < 0 || r[f3].toLowerCase().startsWith(startwithvalue)))
                ret.add(r);
        }
        return ret;
    }
    
    public JSONObject List(int page, int countperpage, String key1, String value1, String key2, String value2, String startwithkey, String startwithvalue) {
        if (page < 1)
            page = 1;
        if (countperpage < 1)
            countperpage = 10;
        
        ArrayList<String[]> rows2 = Filter(key1, value1, key2, value2, startwithkey, startwithvalue);
        int size = rows2.size();
        JSONObject ret =new JSONObject();
        ret.put("page", page);
        ret.put("pages", (size - (size % countperpage)) / countperpage + (size % countperpage == 0 ? 0 : 1));

        int st = (page - 1) * countperpage;
        ArrayList<JSONObject> list = new ArrayList();
        for (int i = 0; i < countperpage; ++i) {
            if (i + st>= size)
                break;
            list.add(Row2Json(rows2.get(i + st)));
        }
        ret.put("rows", list);
        return ret;
    }
    
    public JSONObject LoadRow(String key) {
        for (int i = 0; i < rows.size(); ++i) {
            String[] row = rows.get(i);
            if (row[0].equals(key))
                return Row2Json(row);
        }        
        return null;
    }
    
    JSONObject Row2Json(String[] row) {
        JSONObject ret = new JSONObject();
        for (int i = 0; i < keys.length; ++i)
            ret.put(keys[i], row[i]);
        return ret;
    }
    
    public JSONObject DelRecord(String key) {
        for (int i = 0; i < rows.size(); ++i) {
            String[] row = rows.get(i);
            if (row[0].equals(key)) {
                rows.remove(i);
                return new JSONObject();
            }
        }        
        return null; 
    }
    
    public JSONObject SaveRecord(String[] row) {
        if (row.length != keys.length)
            return null;
        
        if (row[0] == null || row[0].length() == 0) {
            // append
            row[0] = (++maxkey) + "";
            rows.add(row);
            return Row2Json(row);
        }
        else {
            String key = row[0];
            for (int i = 0; i < rows.size(); ++i) {
                String[] r = rows.get(i);
                if (r[0].equals(key)) {
                    rows.set(i, row);
                    return Row2Json(row);
                }
            }
            return null;
        }
    }
        
    int Load() throws Exception {
	try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            if (line == null)
                return 0;
            
            maxkey = Integer.parseInt(line);
            
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    String[] row = UnwrapRow(line);
                    if (row.length == keys.length) {
                        rows.add(row);
                    }
                }
            }
            bufferedReader.close();            
        } catch (IOException e) {
            throw e;
        }
        return rows.size();
    }
    
    String WrapRow(String[] row) {
        String s = "";
        for (int i = 0; i < row.length; ++i)
        {
            if (i > 0)
                s += seperator;
            s += row[i] == null ? "" : row[i];
        }
        return EncodeBase64(s);
    }
    
    String[] UnwrapRow(String s) {
        s = DecodeBase64(s);
        return Split(s, seperator);
    }
    
    public static String EncodeBase64(String s) {
        if (s == null)
            return null;
        byte[] encodedBytes = Base64.encodeBase64(s.getBytes());
        return new String(encodedBytes);
    }
    
    public static String DecodeBase64(String s) {
        if (s == null)
            return null;
        byte[] decodedBytes = Base64.decodeBase64(s);
        return new String(decodedBytes);
    }
    
    String[] Split(String s, String c) {
        ArrayList<String> ret = new ArrayList();
        int p0 = 0;
        int p = s.indexOf(c, p0);
        while (p >= 0) {
            if (p == p0)
                ret.add("");
            else
                ret.add(s.substring(p0, p));
            p0 = p + c.length();
            p = s.indexOf(c, p0);
        }
        ret.add(s.substring(p0));
        
        String[] ss = new String[ret.size()];
        ret.toArray(ss);
        return ss;
    }
    
    public void Save() throws Exception {
	try {
            BufferedWriter w = new BufferedWriter(new FileWriter(path));
            
            w.write(maxkey + "");
            w.newLine();
            w.flush();
            
            for (int i = 0; i < rows.size(); ++i) {
                if (i > 0)
                    w.newLine();
                String[] row = rows.get(i);
                w.write(WrapRow(row));
            }
            
            w.flush();
            w.close();
            
        } catch (IOException e) {
            throw e;
        }
    }
}
