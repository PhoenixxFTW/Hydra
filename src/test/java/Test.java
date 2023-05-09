import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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

    public static void decoder() {
        // Decodes Base64-encoded strings taken from the snapshotsxmls
        String base64EncodedValue = "eyJzdGF0ZVRva2VuIjoiMDBnOWZPU2dwSzJYVVhBb0o1a21hS0tUOHM5NEtvTDY4emxDWGlPWTdFIiwidHlwZSI6IlNFU1NJT05fU1RFUF9VUCIsImV4cGlyZXNBdCI6IjIwMjMtMDQtMDVUMTc6NDE6NTguMDAwWiIsInN0YXR1cyI6IlNVQ0NFU1MiLCJfZW1iZWRkZWQiOnsidXNlciI6eyJpZCI6IjAwdTh4cXM3eWp4ZGROZnBnNWQ3IiwicGFzc3dvcmRDaGFuZ2VkIjoiMjAyMy0wMy0zMVQxNjowOTo0My4wMDBaIiwicHJvZmlsZSI6eyJsb2dpbiI6IlNQeGRiYWxneWZldXdsbnBueEB5b3BtYWlsLmNvbSIsImZpcnN0TmFtZSI6bnVsbCwibGFzdE5hbWUiOm51bGwsImxvY2FsZSI6ImVuX1VTIiwidGltZVpvbmUiOiJBbWVyaWNhL0xvc19BbmdlbGVzIn19LCJ0YXJnZXQiOnsidHlwZSI6IkFQUCIsIm5hbWUiOiJzdGFnZS1vbnRzaWduaW5fZnJvb25saW5lYm9mZW5saWduZXBwZWFwcF8xIiwibGFiZWwiOiJGUk8gT25saW5lIC8gQk9GIGVuIGxpZ25lIFtQUEVdIEFQUCIsIl9saW5rcyI6eyJsb2dvIjp7Im5hbWUiOiJtZWRpdW0iLCJocmVmIjoiaHR0cHM6Ly9vazEyc3RhdGljLm9rdGFjZG4uY29tL2Fzc2V0cy9pbWcvbG9nb3MvZGVmYXVsdC42NzcwMjI4ZmIwZGFiNDlhMTY5NWVmNDQwYTUyNzliYi5wbmciLCJ0eXBlIjoiaW1hZ2UvcG5nIn19fSwiYXV0aGVudGljYXRpb24iOnsicHJvdG9jb2wiOiJTQU1MMi4wIiwiaXNzdWVyIjp7ImlkIjoiMG9hNG80aG50Z01Tc29JTzI1ZDciLCJuYW1lIjoiRlJPIE9ubGluZSAvIEJPRiBlbiBsaWduZSBbUFBFXSBBUFAiLCJ1cmkiOiJodHRwOi8vd3d3Lm9rdGEuY29tL2V4azRvNGhudGZ3ODFPbUNvNWQ3In19fSwiX2xpbmtzIjp7Im5leHQiOnsibmFtZSI6Im9yaWdpbmFsIiwiaHJlZiI6Imh0dHBzOi8vc3RhZ2Uuc2lnbmluLm9udGFyaW8uY2EvbG9naW4vc3RlcC11cC9yZWRpcmVjdD9zdGF0ZVRva2VuPTAwZzlmT1NncEsyWFVYQW9KNWttYUtLVDhzOTRLb0w2OHpsQ1hpT1k3RSIsImhpbnRzIjp7ImFsbG93IjpbIkdFVCJdfX0sImNhbmNlbCI6eyJocmVmIjoiaHR0cHM6Ly9zdGFnZS5zaWduaW4ub250YXJpby5jYS9hcGkvdjEvYXV0aG4vY2FuY2VsIiwiaGludHMiOnsiYWxsb3ciOlsiUE9TVCJdfX19fQ";
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedValue);
        String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(decodedValue);
    }
}
