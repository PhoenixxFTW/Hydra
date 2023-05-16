package com.phoenixx.core.snapshots;

import com.phoenixx.util.Parser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 2:58 p.m. [09-05-2023]
 */
public class HTTPObject {

    private Type requestType;

    private String host;
    private String path;

    private String body;

    public String contentType;

    private final Map<String, QueryObj> headers;
    private final Map<String, QueryObj> cookies;

    public HTTPObject(String host, String path) {
        this(host, path, new HashMap<>(), new HashMap<>());
    }

    public HTTPObject(String host, String path, Map<String, QueryObj> headers, Map<String, QueryObj> cookies) {
        this.host = host;
        this.path = path;
        this.headers = headers;
        this.cookies = cookies;
    }

    /**
     * Builds a new {@link HTTPObject} from an XML {@link Element}
     *
     * @param nodeList A {@link NodeList} parsed from a HTTPRequest/HTTPResponse {@link Element} from a snapshot XML file
     * @return New instance of {@link HTTPObject}
     */
    public static HTTPObject buildObject(String host, String url, NodeList nodeList) {
        HTTPObject httpObject = new HTTPObject(host, url);
        /*System.out.println("Hostname: " + host);
        System.out.println("URL: " + url);*/

        if(nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            // This is only in the HTTPRequest elements, so we add this check to not get an error
            if (element.hasAttribute("method")) {
                httpObject.requestType = Type.valueOf(element.getAttribute("method"));
            }

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node httpRequestNode = nodeList.item(j);
                if (httpRequestNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element httpRequestElement = (Element) httpRequestNode;

                    NodeList httpHeaderEntityList = httpRequestElement.getElementsByTagName("HTTPHeaderEntity");
                    for (int k = 0; k < httpHeaderEntityList.getLength(); k++) {
                        Node httpHeaderEntityNode = httpHeaderEntityList.item(k);
                        if (httpHeaderEntityNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element httpHeaderEntityElement = (Element) httpHeaderEntityNode;
                            String name = httpHeaderEntityElement.getAttribute("name");

                            NodeList actualDataList = httpHeaderEntityElement.getElementsByTagName("ActualData");
                            if (actualDataList.getLength() > 0) {
                                String actualData = actualDataList.item(0).getTextContent();
                                //System.out.println("HTTPHeaderEntity Name: " + name + ", ActualData: " + actualData);
                                if (!httpHeaderEntityNode.getParentNode().getNodeName().equalsIgnoreCase("HTTPCookies")) {
                                    httpObject.headers.put(name, new QueryObj(name, Parser.decodeBase64Val(actualData)));
                                } else {
                                    httpObject.cookies.put(name, new QueryObj(name, Parser.decodeBase64Val(actualData)));
                                }
                            }
                        }
                    }

                    NodeList bodyNodeList = httpRequestElement.getElementsByTagName("HTTPBody");
                    if (bodyNodeList.getLength() != 0) {
                        Element bodyElement = (Element) bodyNodeList.item(0);

                        NodeList actualDataList = bodyElement.getElementsByTagName("ActualData");
                        Element dataElement = (Element) actualDataList.item(0);

                        String actualData = actualDataList.item(0).getTextContent();
                        byte[] decodedBytes = Base64.getDecoder().decode(actualData);
                        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
                        //System.out.println("HTTPBody ActualData: " + decodedString);

                        httpObject.setBody(Parser.decodeBase64Val(actualData));
                    }
                }
            }
            QueryObj queryObj = httpObject.getHeaders().get("Content-Type");
            if(queryObj != null) {
                httpObject.contentType = queryObj.getVal();
                System.out.println("CONTENT-TYPE: " + httpObject.contentType);

                if(httpObject.contentType.equalsIgnoreCase("application/json")) {
                    System.out.println("BODY: " + httpObject.body);
                }
            }

            //System.out.println("BODY: " + httpObject.body);
        }
        return httpObject;
    }

    public HTTPObject setBody(String body) {
        this.body = body;
        return this;
    }

    public Type getRequestType() {
        return requestType;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, QueryObj> getHeaders() {
        return this.headers;
    }

    public Map<String, QueryObj> getCookies() {
        return this.cookies;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Hostname: ").append(this.getHost()).append("\n");
        builder.append("URL: ").append(this.getPath()).append("\n");
        builder.append("Headers: \n");
        this.getHeaders().forEach((name, val) -> {
            builder.append("\t - ").append(name).append(" : ").append(val).append(" \n");
        });
        builder.append("Cookies: \n");
        this.getCookies().forEach((name, val) -> {
            builder.append("\t - ").append(name).append(" : ").append(val).append(" \n");
        });
        builder.append("Body: ").append(this.getBody());
        return builder.toString();
    }
    public enum Type {
        GET, PATCH, POST, PUT, DELETE
    }
}
