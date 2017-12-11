HELM2WebService

How to install on Tomcat version 8.0 or later versions:

1. Download HELM2WebService war file at https://oss.sonatype.org/content/repositories/releases/org/pistoiaalliance/helm/helm2-webservice/2.2.0/helm2-webservice-2.2.0.war

2. Rename the war file to WebService.war

3. Copy WebService.war file to Tomcat webapps folder (e.g. C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps) 

4. Helm Web Editor can be loaded in browser by using URL: http://localhost:8080/WebService/hwe 


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
