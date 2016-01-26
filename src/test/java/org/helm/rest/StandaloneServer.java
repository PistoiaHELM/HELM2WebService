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

import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.helm.rest.Application;
import org.junit.After;
import org.junit.Before;

/**
 * StandaloneServer
 *
 * @author hecht
 */
public class StandaloneServer {

  public final static URI BASE_URI = UriBuilder.fromUri("http://localhost").port(9998).build();

  public final static String BASE_URL_EXTENDED = "http://localhost:9998/WebService/service";

  private HttpServer server;

  /**
   * Starts the server and works as {@code setUp} method for JUnit-based tests.
   */
  @Before
  public void startServer() {
    System.out.println("Starting Grizzly server...");
    server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new Application());
    System.out.println("ServerInformation " + server.toString());
    System.out.println(String.format("Jersey application started with WADL availabe at %sapplication.wadl", BASE_URI));
  }

  /**
   * Stops the server after a test method in a derived class completes.
   */
  @After
  public void stopServer() {
    server.shutdownNow();
  }

  /**
   * Creates a new REST client for interacting with the Grizzly based server.
   *
   * @return a configured instance of {@link javax.ws.rs.client.Client}.
   */
  public Client createClient() {

    return ClientBuilder.newBuilder().register(JacksonFeature.class).build();
  }

  /**
   * Main method for running the Grizzly server outside of JUnit.
   *
   * @param args the arguments to the new JVM process.
   */
  public static void main(String[] args) {
    System.out.println("Running standalone server. Press enter on console to shutdown server...");

    StandaloneServer server = new StandaloneServer();
    server.startServer();
    try {
      System.in.read();
    } catch (IOException e) {
      // ignored
    }
    server.stopServer();
  }
}
