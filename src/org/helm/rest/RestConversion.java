
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
import org.helm.notation.NotationException;
import org.helm.notation2.WebService;
import org.helm.notation2.exception.HELM1FormatException;
import org.helm.notation2.exception.ValidationException;
import org.json.JSONObject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into canonical HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response convertHELMCanonical(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.convertStandardHELMToCanonicalHELM(helmNotation);
      json.put("CanonicalHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | NotationException | IOException e) {
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into canonical HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response convertHELMCanonicalPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.convertStandardHELMToCanonicalHELM(helm);
      json.put("CanonicalHELM", result);

      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | NotationException | IOException e) {
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into standard HELMNotation"), @ApiResponse(code = 400, message = "Error in HLEM input")
  })
  public Response convertHELMStandard(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      String result = webservice.convertIntoStandardHELM(helmNotation);
      json.put("HELMNotation", helmNotation);
      json.put("StandardHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | NotationException | IOException | CTKException e) {
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully converted into standard HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response convertHELMStandardPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.convertIntoStandardHELM(helm);
      json.put("StandardHELM", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (HELM1FormatException | ValidationException | NotationException | IOException | CTKException e) {
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "JSON-Output was successfully generated from the HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response createJSON(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.generateJSON(helm);
      json.put("JSON", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

    }
  }

}
