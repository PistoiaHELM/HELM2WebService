
package org.helm.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.helm.notation2.WebService;
import org.helm.notation2.exception.FastaFormatException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.jdom2.JDOMException;
import org.json.JSONObject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in peptide sequence input")
  })
  public Response generateHELMPeptide(@ApiParam(value = "peptide", required = true) @PathParam("c") String peptide) {
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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in rna sequence input")
  })
  public Response generateHELMRNA(@ApiParam(value = "rna", required = true) @PathParam("c") String rna) {
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
