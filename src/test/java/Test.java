import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:30 PM [28-04-2023]
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //decoder();
        String str = "snapshot_123.xml";
        String regex = "snapshot_\\d+\\.xml";

        if (str.matches(regex)) {
            System.out.println("String matches pattern.");
        } else {
            System.out.println("String does not match pattern.");
        }
    }

    public static void decoder() {
        // Decodes Base64-encoded strings taken from the snapshotsxmls
        String base64EncodedValue = "eyJwYXNzd29yZCI6ImZDTVNfMTIzISIsInVzZXJuYW1lIjoiU1B4ZGJhbGd5ZmV1d2xucG54QHlvcG1haWwuY29tIiwib3B0aW9ucyI6eyJ3YXJuQmVmb3JlUGFzc3dvcmRFeHBpcmVkIjp0cnVlLCJtdWx0aU9wdGlvbmFsRmFjdG9yRW5yb2xsIjp0cnVlfSwic3RhdGVUb2tlbiI6IjAwZzlmT1NncEsyWFVYQW9KNWttYUtLVDhzOTRLb0w2OHpsQ1hpT1k3RSJ9";
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedValue);
        String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(decodedValue);
    }

    public static void XMLTest() throws Exception {
        String loc = "C:\\Users\\TalpurJu\\OneDrive - Government of Ontario\\Desktop\\Work\\Java Workspaces\\ProjectHydra\\TestScripts\\FroOnline-ADEOS - Original Copy\\ScriptUploadMetadata.xml";
        InputStream inputStream = Files.newInputStream(Paths.get(loc));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inputStream);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

        System.out.println("DOCUMENT: " + output);

        Element rootElement = document.getDocumentElement();
        String scriptName = document.getElementsByTagName("ScriptName").item(0).getTextContent();
        System.out.println("SCRIPT NAME: ");

        /*NodeList nodeList = document.getElementsByTagName("VugenScriptMetadata");
        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("Name").getNodeValue());
        }*/
    }
}
