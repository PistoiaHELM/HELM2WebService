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
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ExtinctionCoefficientException;
import org.helm.notation2.exception.ValidationException;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * RestCalculation
 *
 * @author hecht
 */
@Path("/Calculation")

@Api(value = "/Calculation", description = "Calculates something from the HELM Notation")
public class RestCalculation {

  @Path("/MolecularWeight/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Calculates the molecular weight of a non-amibuous HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular weight was successfully calculated from HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateMolecularWeight(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      Double result = webservice.calculateMolecularWeight(helmNotation);
      json.put("MolecularWeight", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/MolecularWeight")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Calculates the molecular weight of a non-amibuous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular weight was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateMolecularWeightPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      Double result = webservice.calculateMolecularWeight(helm);
      json.put("MolecularWeight", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();

    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      json.put("ErrorMessage", "" + e.getMessage());
      json.put("ErrorClass", "" + e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/MolecularFormula/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Calculates the molecular formula of a non-amibuous HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Moleuclar formula was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateMolecularFormula(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      String result = webservice.getMolecularFormula(helmNotation);
      json.put("MoleculcarFormula", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

  }

  @Path("/MolecularFormula")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Calculates the molecular formula of a non-amibuous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular Formula was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateMolecularFormulaPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      String result = webservice.getMolecularFormula(helm);
      json.put("MolecularFormula", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/ExtinctionCoefficient/{c}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Calculates the extinction coefficient of a non-amibuous HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Extinction coefficient was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateExtinctionCoefficient(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helmNotation);
      Float result = webservice.calculateExtinctionCoefficient(helmNotation);
      json.put("ExtinctionCoefficient", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | ExtinctionCoefficientException | IOException e) {
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @Path("/ExtinctionCoefficient")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Calculates the extinction coefficient of a non-amibuous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Extinction coefficient was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response calculateExtinctionCoefficientPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
    WebService webservice = new WebService();
    JSONObject json = new JSONObject();
    try {
      json.put("HELMNotation", helm);
      Float result = webservice.calculateExtinctionCoefficient(helm);
      json.put("ExtinctionCoefficient", result);
      return Response.status(Response.Status.OK).entity(json.toString()).build();
    } catch (ValidationException | ExtinctionCoefficientException | IOException e) {
      json = new JSONObject();
      json.put("ErrorMessage", e.getMessage());
      json.put("ErrorClass", e.getClass());
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

}
