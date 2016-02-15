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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.ValidationException;
import org.helm.notation2.tools.WebService;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * RestValidation
 *
 * @author hecht
 */
@Path("/Validation")

@Api(value = "/Validation", description = "Validate HELM input string")
public class RestValidation {
  @Path("{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Check for conformance to the specification & availability of monomers in the current monomer database. Be careful with special cases in the url", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation is valid"), @ApiResponse(code = 400, message = "HELMNotation is not valid")})
  public Response validateInputHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      webservice.validateHELM(helmNotation);
      json.put("HELMNotation", helmNotation);
      json.put("Validation", "valid");
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Check for conformance to the specification & availability of monomers in the current monomer database.", httpMethod = "POST", response = Response.class, responseContainer = "KJSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation is valid"), @ApiResponse(code = 400, message = "HELMNotation is not valid")})
  public Response validateInputHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      webservice.validateHELM(helm);
      json.put("HELMNotation", helm);
      json.put("Validation", "valid");
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

}
