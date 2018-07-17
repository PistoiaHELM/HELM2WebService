/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * ****************************************************************************
 */
package org.helm.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.print.attribute.standard.Media;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;

/**
 * RestCalculationTest
 *
 * @author hecht
 */
public class RestCalculationTest extends StandaloneServer {
    HttpServer server;

    @Test
    public void testCalculateMolecularWeight() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateMolecularWeight");
        URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateMolecularWeightPost() {
        String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
        Client client = createClient();
        Form f = new Form();
        f.param("HELMNotation", notation);
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateMolecularWeightPost");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateMolecularFormula() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateMolecularFormula");
        URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateMolecularFormulaPost() {
        String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
        Client client = createClient();
        Form f = new Form();
        f.param("HELMNotation", notation);
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateMolecularFormulaPost");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateExtinctionCoefficient() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateExtinctionCoefficient");
        URI uri = builder.build("PEPTIDE1{A}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateExtinctionCoefficientPost() {
        String notation = "PEPTIDE1{A.G}|PEPTIDE2{F.F.R}$$$$V2.0";
        Client client = createClient();
        Form f = new Form();
        f.param("HELMNotation", notation);
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateExtinctionCoefficientPost");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateFingerprint() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateFingerprint");
        URI uri = builder.build("RNA1{R(A)P}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateFingerprintPut() {
        String notation = "RNA1{R(A)P}$$$$V2.0";
        Form f = new Form();
        f.param("HELMNotation", notation);
        Client client = createClient();
//        return UriBuilder.fromUri(url).queryParam(key, value).build();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateFingerprintPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateFingerprintNaturalAnalogs() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateFingerprintNaturalAnalogs");
        URI uri = builder.build("RNA1{R(A)P}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateFingerprintNaturalAnalogsPut() {
        String notation = "RNA1{R(A)P}$$$$V2.0";
        Form f = new Form();
        f.param("HELMNotation", notation);
        Client client = createClient();
//        return UriBuilder.fromUri(url).queryParam(key, value).build();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateFingerprintNaturalAnalogsPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateSimilarity() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateSimilarity");
        URI uri = builder.build("RNA1{R(A)P}$$$$V2.0", "RNA1{R(A)P.R}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateSimilarityPut() {
        String notation1 = "RNA1{R(A)P}$$$$V2.0";
        String notation2 = "RNA1{R(A)P.R}$$$$V2.0";
        Form f = new Form();
        f.param("HELMNotation1", notation1);
        f.param("HELMNotation2", notation2);
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateSimilarityPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateSimilarityNaturalAnalogs() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateSimilarityNaturalAnalogs");
        URI uri = builder.build("RNA1{R(A)P}$$$$V2.0", "RNA1{R(A)P.R}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testCalculateSimilarityNaturalAnalogsPut() {
        String notation1 = "RNA1{R(A)P}$$$$V2.0";
        String notation2 = "RNA1{R(A)P.R}$$$$V2.0";
        Form f = new Form();
        f.param("HELMNotation1", notation1);
        f.param("HELMNotation2", notation2);
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "calculateSimilarityNaturalAnalogsPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testSubstructureRelationship() {
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "checkSubstructureRelationship");
        URI uri = builder.build("RNA1{R(A)P}$$$$V2.0", "RNA1{R(A)P.R}$$$$V2.0");
        Response response = client.target(uri).request().get();
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testSubstructureRelationshipPut() {
        String notation1 = "RNA1{R(A)P}$$$$V2.0";
        String notation2 = "RNA1{R(A)P.R}$$$$V2.0";
        Form f = new Form();
        f.param("parentNotation", notation1);
        f.param("childNotation", notation2);
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "checkSubstructureRelationshipPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testSubstructureRelationshipPut2() {
        String notation1 = "RNA1{R(A)P}$$$$V2.0";
        String notation2 = "RNA1{R(C)P}$$$$V2.0";
        Form f = new Form();
        f.param("parentNotation", notation1);
        f.param("childNotation", notation2);
        Client client = createClient();
        UriBuilder builder = UriBuilder.fromUri(BASE_URI);
        builder.path(RestCalculation.class).path(RestCalculation.class, "checkSubstructureRelationshipPut");
        URI uri = builder.build();
        Response response = client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(response.getStatus(), 200);
    }


}
