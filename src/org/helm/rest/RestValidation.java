
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

import org.helm.notation.MonomerException;
import org.helm.notation2.WebService;
import org.helm.notation2.exception.ParserException;
import org.helm.notation2.exception.ValidationException;
import org.jdom2.JDOMException;
import org.json.JSONObject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * ValidationRest
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation is valid"), @ApiResponse(code = 400, message = "HELMNotation is not valid")
  })
  public Response validateInputHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      webservice.validateHELM(helmNotation);
      json.put("HELMNotation", helmNotation);
      json.put("Validation", "valid");
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Check for conformance to the specification & availability of monomers in the current monomer database.", httpMethod = "POST", response = Response.class, responseContainer = "KJSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation is valid"), @ApiResponse(code = 400, message = "HELMNotation is not valid")
  })
  public Response validateInputHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      webservice.validateHELM(helm);
      json.put("HELMNotation", helm);
      json.put("Validation", "valid");
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

}
