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

import org.helm.chemtoolkit.CTKException;
import org.helm.notation2.WebService;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.HELM1FormatException;
import org.helm.notation2.exception.ValidationException;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * RestConversion
 *
 * @author hecht
 */
@Path("/Conversion")
@Api(value = "/Conversion", description = "Converts HELM into standard/canonical HELM and JSON-Output")
public class RestConversion {
  @Path("/Canonical/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Converts HELM Input into canonical HELM", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into canonical HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertHELMCanonical(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.convertStandardHELMToCanonicalHELM(helmNotation);
      json.put("CanonicalHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("Canonical/")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Converts HELM Input into canonical HELM", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into canonical HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertHELMCanonicalPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.convertStandardHELMToCanonicalHELM(helm);
      json.put("CanonicalHELM", result);

      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("Standard/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Converts HELM Input into standard HELM", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into standard HELMNotation"), @ApiResponse(code = 400, message = "Error in HLEM input")})
  public Response convertHELMStandard(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      String result = webservice.convertIntoStandardHELM(helmNotation);
      json.put("HELMNotation", helmNotation);
      json.put("StandardHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | IOException | CTKException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("Standard/")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Converts HELM Input into standard HELM", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into standard HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertHELMStandardPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.convertIntoStandardHELM(helm);
      json.put("StandardHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | IOException | CTKException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

    }
  }

  @Path("JSON/")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Converts HELM Input into JSON-Output", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "JSON-Output was successfully generated from the HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response createJSON(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.generateJSON(helm);
      json.put("JSON", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

    }
  }

}
