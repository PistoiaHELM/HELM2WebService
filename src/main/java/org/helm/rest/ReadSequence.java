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

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.FastaFormatException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.tools.WebService;
import org.jdom2.JDOMException;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * ReadSequence class to read sequence and transform this in HELMNotation
 *
 * @author hecht
 */

@Path("/Sequence")

@Api(value = "/Sequence", description = "Reads peptide/rna sequence and converts the input into HELMNotation")
public class ReadSequence {

  @Path("/PEPTIDE/{c}")
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Consumes({MediaType.APPLICATION_JSON})
  @ApiOperation(value = "Reads peptide sequence and generates HELMNotation", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in peptide sequence input")})
  public Response generateHELMPeptide(@ApiParam(value = "peptide", required = true) @PathParam("c") String peptide) throws ChemistryException {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      String result = webservice.readPeptide(peptide);
      json.put("HELMNotation", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (FastaFormatException | NotationException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/RNA/{c}")
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Consumes({MediaType.APPLICATION_JSON})
  @ApiOperation(value = "Reads rna sequence and generates HELMNotation", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in rna sequence input")})
  public Response generateHELMRNA(@ApiParam(value = "rna", required = true) @PathParam("c") String rna) throws ChemistryException {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      String result = webservice.readRNA(rna);
      json.put("HELMNotation", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (FastaFormatException | NotationException | IOException | JDOMException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

}
