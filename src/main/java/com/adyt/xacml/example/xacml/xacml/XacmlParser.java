package com.adyt.xacml.example.xacml.xacml;

import com.adyt.xacml.example.xacml.domain.model.RequestModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XacmlParser {

  public static String createXACMLRequest(RequestModel requestModel) {
    return
        "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" "
            + "CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
            "<Attributes "
            + "Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
            "<Attribute "
            + "AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" "
            + "IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" +
            requestModel.getAction() +
            "</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes "
            + "Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
            "<Attribute "
            + "AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" "
            + "IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" +
            requestModel.getSubject()
            + "</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes "
            + "Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n" +
            "<Attribute "
            + "AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" "
            + "IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" +
            requestModel.getResource()
            + "</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "</Request>";
  }

  /**
   * Creates DOM representation of the XACML request
   *
   * @param response XACML request as a String object
   * @return XACML request as a DOM element
   */
  public static Element getXacmlResponse(String response) {

    ByteArrayInputStream inputStream;
    DocumentBuilderFactory dbf;
    Document doc;

    inputStream = new ByteArrayInputStream(response.getBytes());
    dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);

    try {
      doc = dbf.newDocumentBuilder().parse(inputStream);
    } catch (Exception e) {
      System.err.println("DOM of request element can not be created from String");
      return null;
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        System.err.println("Error in closing input stream of XACML response");
      }
    }
    return doc.getDocumentElement();
  }

}
