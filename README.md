HELM2WebService

The WebService.war file is given in publish. 
To run this on tomcat or any other server, you have to add manually the ./helm repository to the server.

To call the WebService, here are some examples using java.


 protected static Response validation(String notation) throws URISyntaxException {
    String extendedURL = "/Validation";
    URI uri = new URIBuilder(url + extendedURL).build();
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    client.target(uri);
    Form f = new Form();
    f.add("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response convertStandard(String notation) throws URISyntaxException {
    String extendedURL = "/Conversion/Canonical";
    URI uri = new URIBuilder(url + extendedURL).build();
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    client.target(uri);
    Form f = new Form();
    f.add("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

  }

  protected static Response convertInput(String notation) throws URISyntaxException {
    String extendedURL = "/Conversion/Standard";
    URI uri = new URIBuilder(url + extendedURL).build();
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    client.target(uri);
    Form f = new Form();
    f.add("HELMNotation", notation);
    return client.target(uri).request().post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
  }