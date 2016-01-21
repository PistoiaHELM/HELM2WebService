
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

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.helm.chemtoolkit.CTKException;
import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.MonomerLoadingException;
import org.helm.notation.model.Monomer;

import org.helm.notation2.WebService;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ParserException;
import org.helm.notation2.exception.ValidationException;
import org.jdom2.JDOMException;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

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
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Monomer image was successfully generated"), @ApiResponse(code = 400, message = "Error in input")
  })
  public Response generateImageForMonomer(@ApiParam(value = "monomerId", required = true) @QueryParam("monomerId") String monomerID,
      @ApiParam(value = "polymerType", required = true) @QueryParam("polymerType") String polymerType) {
    WebService webservice = new WebService();
    Monomer monomer;
    try {
      monomer = MonomerFactory.getInstance().getMonomerStore().getMonomer(polymerType, monomerID);
    } catch (MonomerLoadingException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
    try {
      return Response.status(Response.Status.OK).entity(webservice.generateImageForMonomer(monomer)).build();

    } catch (BuilderMoleculeException | CTKException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

  }

  @Path("Monomer")
  @POST
  @Produces("text/plain")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Image generation of the atom/bond representation of monomer", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Monomer image was successfully generated"), @ApiResponse(code = 400, message = "Error in input")
  })
  public Response generateImageForMonomerPost(@ApiParam(value = "monomerId", required = true) @FormParam(value = "monomerId") String monomerID,
      @ApiParam(value = "polymerType", required = true) @FormParam(value = "polymerType") String polymerType) {
    WebService webservice = new WebService();
    Monomer monomer;
    try {
      monomer = MonomerFactory.getInstance().getMonomerStore().getMonomer(polymerType, monomerID);
    } catch (MonomerLoadingException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
    try {
      byte[] result = webservice.generateImageForMonomer(monomer);

      StringBuilder sb = new StringBuilder();
      sb.append("data:image/png;base64,");
      sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(result, false)));
      return Response.status(Response.Status.OK).entity(sb.toString()).build();

    } catch (BuilderMoleculeException | CTKException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

  }

  @Path("HELM/{c}")
  @GET
  @Produces("image/png")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Image generation of the atom/bond representation of the HELM molecule", httpMethod = "GET", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation image was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response generateImageForHELM(@ApiParam(value = "HELMNotation", required = true) @PathParam("c") String helmNotation) throws JDOMException, MonomerException {
    WebService webservice = new WebService();
    try {
      return Response.status(Response.Status.OK).entity(webservice.generateImageForHELMMolecule(helmNotation)).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
  }

  @Path("HELM")
  @POST
  @Produces("text/plain")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Image generation of the atom/bond representation of the HELM molecule", httpMethod = "POST", response = Response.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "HELMNotation image was successfully generated"), @ApiResponse(code = 400, message = "Error in HELM input")
  })
  public Response generateImageForHELMPost(@ApiParam(value = "HELMNotation", required = true) @FormParam(value = "HELMNotation") String helm) throws JDOMException, MonomerException {
    WebService webservice = new WebService();
    try {
      byte[] result = webservice.generateImageForHELMMolecule(helm);
      StringBuilder sb = new StringBuilder();
      sb.append("data:image/png;base64,");
      sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(result, false)));
      return Response.status(Response.Status.OK).entity(sb.toString()).build();
    } catch (ValidationException | BuilderMoleculeException | CTKException | IOException e) {
      String message = e.getClass() + " " + e.getMessage();
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
  }

}
