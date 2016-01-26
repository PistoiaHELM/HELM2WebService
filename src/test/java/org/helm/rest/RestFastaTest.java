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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * RestFastaTest
 *
 * @author hecht
 */
public class RestFastaTest extends StandaloneServer {
  HttpServer server;

  @Test
  public void testGenerateFasta() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "generateFasta");
    URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
    Response response = client.target(uri).queryParam("peptide", ">pe\nA").request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateFastaPost() {
    String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
    Client client = createClient();
    Form f = new Form();
    f.param("HELMNotation", notation);
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "generateFastaPost");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateHELMInput() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "generateHELMInput");
    URI uri = builder.build();
    Response response = client.target(uri).queryParam("PEPTIDE", "A").request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateHELMInputPostRNA() {
    Client client = createClient();
    Form f = new Form();
    f.param("RNA", ">Pjk\nAA");
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "generateHELMInputPostRNA");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateHELMInputPostPEPTIDE() {
    Client client = createClient();
    Form f = new Form();
    f.param("PEPTIDE", ">Pjk\nAA");
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "generateHELMInputPostPEPTIDE");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testConvertIntoRNAAnalogSequence() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "convertIntoRNAAnalogSequence");
    URI uri = builder.build("RNA1{R(G)}$$$$V2.0");
    Response response = client.target(uri).request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testConvertIntoRNAAnalogSequencePost() {
    Client client = createClient();
    Form f = new Form();
    f.param("HELMNotation", "RNA1{R(G)}$$$$V2.0");
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "convertIntoRNAAnalogSequencePost");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testConvertIntoPEPTIDEAnalogSequence() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "convertIntoPEPTIDEAnalogSequence");
    URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
    Response response = client.target(uri).request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testConvertIntoPEPTIDEAnalogSequencePost() {
    Client client = createClient();
    Form f = new Form();
    f.param("HELMNotation", "PEPTIDE1{A}$$$$V2.0");
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestFasta.class).path(RestFasta.class, "convertIntoPEPTIDEAnalogSequencePost");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }
}
