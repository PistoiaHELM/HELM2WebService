package org.helm.rest;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.utils.URIBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.helm.rest.Application;
import org.junit.After;
import org.junit.Before;

public class StandaloneServer {

  public final static URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9998).build();

  private HttpServer server;

  /**
   * Starts the server and works as {@code setUp} method for JUnit-based tests.
   */
  @Before
  public void startServer() {
    System.out.println("Starting Grizzly server...");
    server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new Application());
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
