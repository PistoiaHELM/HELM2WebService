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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.helm.notation.MonomerLoadingException;
import org.helm.notation2.WebService;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.FastaFormatException;
import org.helm.notation2.exception.HELM2HandledException;
import org.helm.notation2.exception.PeptideUtilsException;
import org.helm.notation2.exception.ValidationException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.jdom2.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * RestFasta
 *
 * @author hecht
 */
@Path("/Fasta")
@Api(value = "/Fasta", description = "Fasta-Services")
public class RestFasta {

  @Path("/Produce/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Converts HELM Input into Fasta", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "FastaFile was successfully generated from the HELMNotation "), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateFasta(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      System.out.println(helmNotation);
      String result = webservice.generateFasta(helmNotation);
      json.put("HELMNotation", helmNotation);
      json.put("FastaFile", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | FastaFormatException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Produce")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Converts HELM Input into Fasta", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "FastaFile was successfully generated from the HELMNotation"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateFastaPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.generateFasta(helm);
      json.put("FastaFile", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | FastaFormatException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Read")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Reads peptide Fasta-Sequence(s) and generates HELMNotation", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in sequence input")})
  public Response generateHELMInput(@ApiParam(value = "peptide", required = false) @QueryParam("PEPTIDE") String peptide, @ApiParam(value = "rna", required = false) @QueryParam("RNA") String rna)
      throws NotationException, IOException, JDOMException {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    if (peptide != null && rna == null) {
      try {
        json.put("HELMNotation", webservice.generateHELMFromFastaPeptide(peptide));
        return Response.status(Response.Status.OK).entity(json.toString()).build();
      } catch (FastaFormatException | JSONException | MonomerLoadingException e) {
        json.put("ErrorMessage", e.getMessage());
        json.put("ErrorClass", e.getClass());
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
      }
    } else if (peptide == null && rna != null) {
      try {
        json.put("HELMNotation", webservice.generateHELMFromFastaNucleotide(rna));
        return Response.status(Response.Status.OK).entity(json.toString()).build();
      } catch (FastaFormatException | JSONException | MonomerLoadingException e) {
        json.put("ErrorMessage", e.getMessage());
        json.put("ErrorClass", e.getClass());
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
      }
    } else {
      json.put("ErrorMessage", "Specify only one typ: RNA or PEPTIDE");
      return Response.status(Response.Status.BAD_REQUEST).entity("Specify only one typ: RNA or PEPTIDE").build();
    }
  }

  @Path("Read/RNA")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Reads rna Fasta-Sequence(s) and generates HELMNotation", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in input rna sequence")})
  public Response generateHELMInputPostRNA(@ApiParam(value = "rna", required = true) @FormParam(value = "RNA") String rna) throws NotationException, IOException, JDOMException {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", webservice.generateHELMFromFastaNucleotide(rna));
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (FastaFormatException | JSONException | MonomerLoadingException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("Read/PEPTIDE")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Reads peptide Fasta-Sequence(s) and generates HELMNotation", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation was successfully generated"), @ApiResponse(code = 400, message = "Error in input peptide sequence")})
  public Response generateHELMInputPostPEPTIDE(@ApiParam(value = "peptide", required = true) @FormParam(value = "PEPTIDE") String peptide) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", webservice.generateHELMFromFastaPeptide(peptide));
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (FastaFormatException | JSONException | MonomerLoadingException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Convert/RNA/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Reads HELMNotation and converts it into rna analogue sequence", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Natural analogue rna sequence was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertIntoRNAAnalogSequence(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String notation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("Seqeunce", webservice.generateNaturalAnalogSequenceRNA(notation));
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | JSONException | NotationException | HELM2HandledException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Convert/PEPTIDE/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Reads HELMNotation and converts it into peptide analogue sequence", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Natural analogue peptide sequence was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertIntoPEPTIDEAnalogSequence(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String notation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("Sequence", webservice.generateNaturalAnalogSequencePeptide(notation));
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | JSONException | IOException | NotationException | HELM2HandledException | PeptideUtilsException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Convert/RNA")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Reads HELMNotation and converts it into rna analogue sequence", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Natural analogue rna sequence was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertIntoRNAAnalogSequencePost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("Sequence", webservice.generateNaturalAnalogSequenceRNA(helm));
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (NotationException | JSONException | HELM2HandledException | ValidationException | IOException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/Convert/PEPTIDE")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Reads HELMNotation and converts it into peptide analogue sequence", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Natural analogue peptide sequence was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response convertIntoPEPTIDEAnalogSequencePost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("Sequence", webservice.generateNaturalAnalogSequencePeptide(helm));
      System.out.println("Hallo");
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | JSONException | IOException | NotationException | HELM2HandledException | PeptideUtilsException | ChemistryException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

}
