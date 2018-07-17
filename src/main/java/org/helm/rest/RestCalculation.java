/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * ****************************************************************************
 */
package org.helm.rest;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.helm.chemtoolkit.CTKException;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.ExtinctionCoefficientException;
import org.helm.notation2.exception.ValidationException;
import org.helm.notation2.tools.HELM2NotationUtils;
import org.helm.notation2.tools.WebService;
import org.jboss.resteasy.annotations.Body;
import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import org.pistoiaalliance.helm.HELMSimilarityLibrary.Fingerprinter;
import org.pistoiaalliance.helm.HELMSimilarityLibrary.Similarity;
import org.pistoiaalliance.helm.HELMSimilarityLibrary.Subset;

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
    @ApiOperation(value = "Calculates the molecular weight of a non-ambiguous HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular weight was successfully calculated from HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateMolecularWeight(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helmNotation);
            Double result = webservice.calculateMolecularWeight(helmNotation);
            json.put("MolecularWeight", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
            json.put("ErrorMessage", "" + e.getMessage());
            json.put("ErrorClass", "" + e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/MolecularWeight")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the molecular weight of a non-ambiguous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular weight was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateMolecularWeightPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            Double result = webservice.calculateMolecularWeight(helm);
            json.put("MolecularWeight", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();

        } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
            json.put("ErrorMessage", "" + e.getMessage());
            json.put("ErrorClass", "" + e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/MolecularFormula/{c}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Calculates the molecular formula of a non-ambiguous  HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Moleuclar formula was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateMolecularFormula(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helmNotation);
            String result = webservice.getMolecularFormula(helmNotation);
            json.put("MoleculcarFormula", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @Path("/MolecularFormula")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the molecular formula of a non-ambiguous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecular Formula was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateMolecularFormulaPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            String result = webservice.getMolecularFormula(helm);
            json.put("MolecularFormula", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/ExtinctionCoefficient/{c}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Calculates the extinction coefficient of a non-ambiguous  HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Extinction coefficient was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateExtinctionCoefficient(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helmNotation);
            Float result = webservice.calculateExtinctionCoefficient(helmNotation);
            json.put("ExtinctionCoefficient", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | ExtinctionCoefficientException | IOException | ChemistryException e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/ExtinctionCoefficient")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the extinction coefficient of a non-ambiguous  HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Extinction coefficient was successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateExtinctionCoefficientPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            Float result = webservice.calculateExtinctionCoefficient(helm);
            json.put("ExtinctionCoefficient", result);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | ExtinctionCoefficientException | IOException | ChemistryException e) {
            json = new JSONObject();
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/MoleculeProperties/{c}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the extinction coefficient of a non-ambiguous HELM string", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "MoleculeProperties were successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateCombined(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            List<String> result = webservice.getMolecularProperties(helm);
            json.put("MolecularFormula", result.get(0));
            json.put("MolecularWeight", result.get(1));
            json.put("ExtinctionCoefficient", result.get(3));
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | ExtinctionCoefficientException | IOException | BuilderMoleculeException | CTKException | ChemistryException e) {
            json = new JSONObject();
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/MoleculeProperties")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates molecule properties of a non-ambiguous HELM string", httpMethod = "POST", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Molecule properties were successfully calculated from the HELM input"), @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateCombinedPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            List<String> result = webservice.getMolecularProperties(helm);
            json.put("MolecularFormula", result.get(0));
            json.put("MolecularWeight", result.get(1));
            json.put("ExtinctionCoefficient", result.get(3));
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (ValidationException | ExtinctionCoefficientException | IOException | BuilderMoleculeException | CTKException | ChemistryException e) {
            json = new JSONObject();
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Fingerprints/original/{c}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates a hashed fingerprint of a non-ambiguous HELM string", httpMethod = "GET",
            response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fingerprint of HELM input was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateFingerprint(@ApiParam(value = "HELMNotation", required = true)
                                         @PathParam("c") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            webservice.validateHELM(helm);
            BitSet fingerprint = Fingerprinter.calculateFingerprint(HELM2NotationUtils.readNotation(helm));
            json.put("Fingerprint", fingerprint);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Fingerprints/original")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates a hashed fingerprint of a non-ambiguous HELM string", httpMethod = "PUT",
            response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fingerprint of HELM input was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateFingerprintPut(@ApiParam(value = "HELMNotation", required = true)
                                            @FormParam(value = "HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            webservice.validateHELM(helm);
            BitSet fingerprint = Fingerprinter.calculateFingerprint(HELM2NotationUtils.readNotation(helm));
            json.put("Fingerprint", fingerprint);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json = new JSONObject();
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Fingerprints/naturalAnalogs/{c}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates a hashed fingerprint of a non-ambiguous HELM string and takes natural analogs of" +
            "modified monomers into account", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fingerprint natural analogs of HELM input was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateFingerprintNaturalAnalogs(@ApiParam(value = "HELMNotation", required = true)
                                                       @PathParam("c") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            webservice.validateHELM(helm);
            BitSet fingerprint = Fingerprinter.calculateFingerprintNaturalAnalogs(HELM2NotationUtils.readNotation(helm));
            json.put("Fingerprint", fingerprint);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Fingerprints/naturalAnalogs")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates a hashed fingerprint of a non-ambiguous HELM string and takes natural analogs of" +
            "modified monomers into account", httpMethod = "PUT", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fingerprint natural analogs of HELM input was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateFingerprintNaturalAnalogsPut(@ApiParam(value = "HELMNotation", required = true)
                                                          @FormParam("HELMNotation") String helm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation", helm);
            webservice.validateHELM(helm);
            BitSet fingerprint = Fingerprinter.calculateFingerprintNaturalAnalogs(HELM2NotationUtils.readNotation(helm));
            json.put("Fingerprint", fingerprint);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Similarity/original/{c}/{v}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the similarity of two HELM notations", httpMethod = "GET",
            response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Similarity was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateSimilarity(@ApiParam(value = "HELMNotation1", required = true)
                                        @PathParam("c") String helm1,
                                        @ApiParam(value = "HELMNotation2", required = true)
                                        @PathParam("v") String helm2) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", helm1);
            json.put("HELMNotation2", helm2);
            webservice.validateHELM(helm1);
            webservice.validateHELM(helm2);
            Double similarity = Similarity.calculateSimilarity(HELM2NotationUtils.readNotation(helm1),
                    HELM2NotationUtils.readNotation(helm2));
            json.put("Similarity", similarity);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Similarity/original")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the similarity of two HELM notations", httpMethod = "PUT",
            response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Similarity was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateSimilarityPut(@ApiParam(value = "HELMNotation1", required = true)
                                           @FormParam("HELMNotation1") String helm1,
                                           @ApiParam(value = "HELMNotation2", required = true)
                                           @FormParam("HELMNotation2") String helm2) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", helm1);
            json.put("HELMNotation2", helm2);
            webservice.validateHELM(helm1);
            webservice.validateHELM(helm2);
            Double similarity = Similarity.calculateSimilarity(HELM2NotationUtils.readNotation(helm1),
                    HELM2NotationUtils.readNotation(helm2));
            json.put("Similarity", similarity);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Similarity/naturalAnalogs/{c}/{v}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the similarity of two HELM notations with natural analogs taken into account",
            httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Similarity was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateSimilarityNaturalAnalogs(@ApiParam(value = "HELMNotation1", required = true)
                                                      @PathParam("c") String helm1,
                                                      @ApiParam(value = "HELMNotation2", required = true)
                                                      @PathParam("v") String helm2) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", helm1);
            json.put("HELMNotation2", helm2);
            webservice.validateHELM(helm1);
            webservice.validateHELM(helm2);
            Double similarity = Similarity.calculateSimilarityNatAnalogs(HELM2NotationUtils.readNotation(helm1),
                    HELM2NotationUtils.readNotation(helm2));
            json.put("Similarity", similarity);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Similarity/naturalAnalogs")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Calculates the similarity of two HELM notations with natural analogs taken into account",
            httpMethod = "PUT", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Similarity was successfully calculated"),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response calculateSimilarityNaturalAnalogsPut(@ApiParam(value = "HELMNotation1", required = true)
                                                         @FormParam("HELMNotation1") String helm1,
                                                         @ApiParam(value = "HELMNotation2", required = true)
                                                         @FormParam("HELMNotation2") String helm2) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", helm1);
            json.put("HELMNotation2", helm2);
            webservice.validateHELM(helm1);
            webservice.validateHELM(helm2);
            Double similarity = Similarity.calculateSimilarityNatAnalogs(HELM2NotationUtils.readNotation(helm1),
                    HELM2NotationUtils.readNotation(helm2));
            json.put("Similarity", similarity);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Substructure/{c}/{v}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Checks if a parent notation is a substructure of a child notation" +
            "modified monomers into account", httpMethod = "GET", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "HELM substructure relationship was successfully calculated."),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response checkSubstructureRelationship(@ApiParam(value = "parent notation", required = true)
                                                  @PathParam("c") String parentHelm,
                                                  @ApiParam(value = "child notation", required = true)
                                                  @PathParam("v") String childHelm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", parentHelm);
            json.put("HELMNotation2", childHelm);
            webservice.validateHELM(parentHelm);
            webservice.validateHELM(childHelm);
            Boolean isSubset = Subset.checkHelmRelationship(HELM2NotationUtils.readNotation(parentHelm),
                    HELM2NotationUtils.readNotation(childHelm));
            json.put("SubstructureRelationship", isSubset);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/Substructure")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Checks if a parent notation is a substructure of a child notation" +
            "modified monomers into account", httpMethod = "PUT", response = Response.class, responseContainer = "JSON")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "HELM substructure relationship was successfully calculated."),
            @ApiResponse(code = 400, message = "Error in HELM input")})
    public Response checkSubstructureRelationshipPut(@ApiParam(value = "parent notation", required = true)
                                                  @FormParam("parentNotation") String parentHelm,
                                                  @ApiParam(value = "child notation", required = true)
                                                  @FormParam("childNotation") String childHelm) {
        WebService webservice = new WebService();
        JSONObject json = new JSONObject();
        try {
            json.put("HELMNotation1", parentHelm);
            json.put("HELMNotation2", childHelm);
            webservice.validateHELM(parentHelm);
            webservice.validateHELM(childHelm);
            Boolean isSubset = Subset.checkHelmRelationship(HELM2NotationUtils.readNotation(parentHelm),
                    HELM2NotationUtils.readNotation(childHelm));
            json.put("SubstructureRelationship", isSubset);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            json.put("ErrorMessage", e.getMessage());
            json.put("ErrorClass", e.getClass());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
