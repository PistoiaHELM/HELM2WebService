/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package org.helm.rest;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * RestValidationTest
 *
 * @author hecht
 */
public class RestValidationTest extends StandaloneServer {

  HttpServer server;

  @Test
  public void testValidateInputHELM() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestValidation.class).path(RestValidation.class, "validateInputHELM");
    String helmNotation;
    //helmNotation = "PEPTIDE1{A.[dR].G}$$$$V2.0";
    //helmNotation = "PEPTIDE1{A.[[*]N1CC[C@H]1C([*])=O |r,$_R1;;;;;;_R2;$|].G}$$$$V2.0";
    helmNotation = "PEPTIDE1{A.[[R1]N1CC[C@H]1C([R2])=O].[CCO]}$$$$V2.0";
    URI uri = builder.build(helmNotation);
    Response response = client.target(uri).request().get();
    String output = response.readEntity(String.class);
    System.out.println(uri);
    System.out.println(output);
    Assert.assertEquals(new JSONObject(output).getString("Validation"), "valid");
  }
  
  @Test
  public void testValidateInputHELMPost() {
    String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
    Client client = createClient();
    Form f = new Form();
    f.param("HELMNotation", notation);
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestValidation.class);
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    String output = response.readEntity(String.class);
    System.out.println(uri);
    System.out.println(output);
    Assert.assertEquals(new JSONObject(output).getString("Validation"), "valid");
  }

}
