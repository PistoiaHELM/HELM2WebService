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

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.helm.rest.RestValidation;

/**
 * TestWebService
 *
 * @author hecht
 */
public class TestWebService extends StandaloneServer {

  private static final String url = "http://localhost:8080/WebService/service";

  // @Test
  public void testAllServices() throws URISyntaxException {
    Client client = createClient();
    System.out.println("Server is there");
    String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";

    System.out.println((validation(notation, client)).readEntity(String.class));
    System.out.println("Validation method");
    System.out.println(convertStandard(notation, client).readEntity(String.class));
    System.out.println(convertInput(notation, client).readEntity(String.class));
    System.out.println(calculateMolecularWeight(notation, client).readEntity(String.class));
    System.out.println(calculateMolecularFormula(notation, client).readEntity(String.class));
    System.out.println(calculateExtinctionCoefficient(notation, client).readEntity(String.class));
    System.out.println(produceFasta(notation, client).readEntity(String.class));

    System.out.println(readFasta("AG", "PEPTIDE", client).readEntity(String.class));
    System.out.println(convert(notation, client).readEntity(String.class));
  }

  protected static Response validation(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Validation";
    URI uri = new URIBuilder(url + extendedURL).build();
    Form f = new Form();
    f.param("HELMNotation", notation);

    return client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response convertStandard(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Conversion/Canonical";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response convertInput(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Conversion/Standard";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }

  protected static Response calculateMolecularWeight(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Calculation/MolecularWeight";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response calculateMolecularFormula(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Calculation/MolecularFormula";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response calculateExtinctionCoefficient(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Calculation/ExtinctionCoefficient";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }

  protected static Response produceFasta(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Fasta/Produce";
    URI uri = new URIBuilder(url + extendedURL).build();
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }

  protected static Response readFasta(String sequence, String type, Client client) throws URISyntaxException {
    String extendedURL = "/Fasta/Read" + "/" + type;
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param(type, sequence);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }

  protected static Response convert(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Fasta/Convert";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }

  protected static Response generateImage(String notation, Client client) throws URISyntaxException {
    String extendedURL = "/Image/HELM";
    URI uri = new URIBuilder(url + extendedURL).build();
    client.target(uri);
    Form f = new Form();
    f.param("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response validateGet() throws URISyntaxException {
    UriBuilder builder = UriBuilder.fromUri(url);
    builder.path(RestValidation.class).path(RestValidation.class, "validateInputHELM");
    URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    return client.target(uri).request().get();
  }

}
