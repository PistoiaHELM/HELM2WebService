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

import org.apache.commons.codec.binary.Base64;

import org.helm.chemtoolkit.CTKException;
import org.helm.notation.MonomerException;
import org.helm.notation.MonomerLoadingException;
import org.helm.notation2.Monomer;
import org.helm.notation2.MonomerFactory;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.ValidationException;
import org.helm.notation2.tools.WebService;
import org.jdom2.JDOMException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * RestImages
 *
 * @author hecht
 */
@Path("Image")
@Api(value = "/Image", description = "Image Generation")
public class RestImages {

  @Path("Monomer")
  @GET
  @Produces("image/png")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Image generation of the atom/bond representation of monomer", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Monomer image was successfully generated"), @ApiResponse(code = 400, message = "Error ininput")})
  public Response generateImageForMonomer(@ApiParam(value = "monomerId", required = true) @QueryParam("monomerId") String monomerID,
      @ApiParam(value = "polymerType", required = true) @QueryParam("polymerType") String polymerType,
      @ApiParam(value = "showRgroups") @QueryParam("showRgroups") boolean showRgroups) throws ChemistryException {
    WebService webservice = new WebService();
    Monomer monomer;
    try {
      monomer = MonomerFactory.getInstance().getMonomerStore().getMonomer(polymerType, monomerID);
    } catch (MonomerLoadingException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
    try {
      System.out.println("ShowRgroups " + showRgroups);
      return Response.status(Response.Status.OK).entity(webservice.generateImageForMonomer(monomer, showRgroups)).build();

    } catch (BuilderMoleculeException | CTKException | ChemistryException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

  }

  @Path("Monomer")
  @POST
  @Produces("text/plain")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Image generation of the atom/bond representation of monomer", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Monomer image was successfully generated"), @ApiResponse(code = 400, message = "Error in input")})
  public Response generateImageForMonomerPost(@ApiParam(value = "monomerId", required = true) @FormParam(value = "monomerId") String monomerID,
      @ApiParam(value = "polymerType", required = true) @FormParam(value = "polymerType") String polymerType, @FormParam(value = "showRgroups") boolean showRgroups) throws ChemistryException {
    WebService webservice = new WebService();
    Monomer monomer;
    try {
      monomer = MonomerFactory.getInstance().getMonomerStore().getMonomer(polymerType, monomerID);
    } catch (MonomerLoadingException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
    try {
      byte[] result = webservice.generateImageForMonomer(monomer, showRgroups);

      StringBuilder sb = new StringBuilder();
      sb.append("data:image/png;base64,");
      sb.append(org.apache.tomcat.util.codec.binary.StringUtils.newStringUtf8(Base64.encodeBase64(result, false)));
      return Response.status(Response.Status.OK).entity(sb.toString()).build();

    } catch (BuilderMoleculeException | CTKException | ChemistryException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

  }

  @Path("HELM/{c}")
  @GET
  @Produces("image/png")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Image generation of the atom/bond representation of the HELM molecule", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation image was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateImageForHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) throws JDOMException, MonomerException {
    WebService webservice = new WebService();
    try {
      return Response.status(Response.Status.OK).entity(webservice.generateImageForHELMMolecule(helmNotation)).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
  }

  @Path("HELM")
  @POST
  @Produces("text/plain")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Image generation of the atom/bond representation of the HELM molecule", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation image was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")})
  public Response generateImageForHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) throws JDOMException, MonomerException {
    WebService webservice = new WebService();
    try {
      byte[] result = webservice.generateImageForHELMMolecule(helm);
      StringBuilder sb = new StringBuilder();
      sb.append("data:image/png;base64,");
      sb.append(org.apache.tomcat.util.codec.binary.StringUtils.newStringUtf8(Base64.encodeBase64(result, false)));
      return Response.status(Response.Status.OK).entity(sb.toString()).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException | ChemistryException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
  }

}
