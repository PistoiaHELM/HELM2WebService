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
 * RestImagesTest
 *
 * @author hecht
 */
public class RestImagesTest extends StandaloneServer {

  HttpServer server;

  @Test
  public void testGenerateImageForMonomer() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestImages.class).path(RestImages.class, "generateImageForMonomer");
    URI uri = builder.build();
    Response response = client.target(uri).queryParam("monomerId", "A").queryParam("polymerType", "PEPTIDE").queryParam("showRgroups", "true").request().get();
    System.out.println(response.toString());
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateImageForMonomerPost() {
    Client client = createClient();
    Form f = new Form();
    f.param("monomerId", "A");
    f.param("polymerType", "PEPTIDE");
    f.param("showRgroups", "true");
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestImages.class).path(RestImages.class, "generateImageForMonomerPost");
    URI uri = builder.build();
    Response response = client.target(uri).request(MediaType.TEXT_PLAIN).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    System.out.println(response.toString());
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);

  }

  @Test
  public void testGenerateImageForHELM() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestImages.class).path(RestImages.class, "generateImageForHELM");
    URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
    Response response = client.target(uri).request().get();
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateImageForHELMPost() {
    String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
    Client client = createClient();
    Form f = new Form();
    f.param("HELMNotation", notation);
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(RestImages.class).path(RestImages.class, "generateImageForHELMPost");
    URI uri = builder.build();
    Response response = client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
    Assert.assertEquals(response.getStatus(), 200);
  }

}
