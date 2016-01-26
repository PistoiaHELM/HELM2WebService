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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * ReadSequenceTest
 *
 * @author hecht
 */
public class ReadSequenceTest extends StandaloneServer {

  HttpServer server;

  @Test
  public void testGenerateHELMPeptide() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(ReadSequence.class).path(ReadSequence.class, "generateHELMPeptide");
    URI uri = builder.build("AA");
    Response response = client.target(uri).request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void testGenerateHELMRNA() {
    Client client = createClient();
    UriBuilder builder = UriBuilder.fromUri(BASE_URI);
    builder.path(ReadSequence.class).path(ReadSequence.class, "generateHELMRNA");
    URI uri = builder.build("AA");
    Response response = client.target(uri).request().get();
    System.out.println(response.readEntity(String.class));
    Assert.assertEquals(response.getStatus(), 200);

  }

}
