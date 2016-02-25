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

import org.apache.commons.codec.binary.Base64;
import org.helm.chemtoolkit.CTKException;
import org.helm.notation2.Monomer;
import org.helm.notation2.MonomerFactory;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.MonomerLoadingException;
import org.helm.notation2.exception.NotationException;
import org.helm.notation2.exception.ValidationException;
import org.helm.notation2.tools.WebService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @code RestSMILES
 *
 * @author hecht
 */
@Path("SMILES")
@Api(value = "/SMILES", description = "SMILES Generation")
public class RestSMILES {

  /** The Logger for this class */
  private static final Logger LOG = LoggerFactory.getLogger(RestSMILES.class);

  @Path("{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "SMILES generation for the whole HELM molecule", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SMILES was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateSMILESForHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.generateSMILESForHELM2(helmNotation);
      json.put("SMILES", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (BuilderMoleculeException | CTKException | ChemistryException | ValidationException | MonomerLoadingException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
    }

  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "SMILES generation for  the whole HELM molecule", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SMILES was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateSMILESForHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.generateSMILESForHELM2(helmNotation);
      json.put("SMILES", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (MonomerLoadingException | BuilderMoleculeException | CTKException | ChemistryException | ValidationException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

  }

  @Path("/Canonical/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Canonical SMILES generation for the whole HELM molecule", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SMILES was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateCanSMILESForHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.generateCanSMILESForHELM2(helmNotation);
      json.put("SMILES", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (BuilderMoleculeException | CTKException | ChemistryException | ValidationException | MonomerLoadingException | NotationException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
    }

  }

  @Path("/Canonical")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Canonical SMILES generation for  the whole HELM molecule", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SMILES was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateCanSMILESForHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.generateCanSMILESForHELM2(helmNotation);
      json.put("SMILES", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (MonomerLoadingException | BuilderMoleculeException | CTKException | ChemistryException | ValidationException | NotationException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

  }

}
