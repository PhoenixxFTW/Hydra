import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:30 PM [28-04-2023]
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //decoder();
        //XMLTest();

        String line = "Snapshot=t14.inf";
        String snapshotVal = line.split("=")[1];
        // Remove the "t" character in front of the number
        snapshotVal = snapshotVal.substring(1);
        // Remove the file extension
        snapshotVal = snapshotVal.substring(0, snapshotVal.length() - 4);
        System.out.println("SNAPSHOT ID: " + snapshotVal);
    }
    public static void decoder() throws IOException {
        // Decodes Base64-encoded strings taken from the snapshotsxmls
/*        String base64EncodedValue = "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";
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedValue);
        String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(decodedValue);
        */
        // Convert data Base64 encoded string to image
/*        String encodedBase64Image = "gw8QAICqqqrq/3Q114OBCoSEpkdGREWkWWoGeEd4QVRlRkbAAtkN4ODgoGEmHqaZ5qoGomIR4W2u9mx+/g+fZK1Ntttb6w+EjDwSiUQiI9lP+6xFRq6srFD22bGer8N19xdkCPX+ZrYdQyM+Bg2TGhMVSdg3ouo3x4XY6ez0tNqS+VaQkGFaxiCgt9L5VJb/4cxsNvQhFNp0OOhg5U5/J0gwMcnIQQd7//KbGjGJ5IGjRNkPdL89HKbNZpjD25tNtVpnH5K40FDcFgtmty9LiassZsPiq6V8OFCsyjay3iJaLnwoCLQ6DiogKt8znUv37+GB40Ase9O4vtd24bQstax4bWnFa8igNw/NKH9V/FarWZ/VzMrEX8ZtQWUZxr6fWUsgHcf3ItB78bwfaMkcWavr3qVUEET2b4s3149UqKMnYR9eNcGRKnwqQpTCFU0MSXhsJHIRuZCNrKDW62vBGibETU+brWSoLdHUCy03qrWWrkBwJw2TE9IElWYzIOLV0u+ZuHM0DBnQ+XbVuJT8a8D6QnCqpxv+MRnFGfPvCdmeYLCOX8cdBUmmp/AqXc1fQ81HR+A1Tz9MRyvO2CzYN/2KC+mHpR8ofg110NlRIRmMG4Z+r4EDcRdTgYzRzlVwDJOdF0qgWyXr9Jl2+MPTfvcS+7L83/7OeCF2EhnZUlnKitYY7Lz2W82wGUeONasCKxhJWaow7l6IldiJAozbmOoU6ENO3pxAbwspy/DNMngJ24F9i74t5oCTGUYr2QVOw9HRGtsYqJpJzrmOzA8yXSmAbQrAYEr70pNRlbJuncjGLW19oNYoyJhAXBUm10K4kKMfjuLLIKqslK25xgicQTdR8pBpAnp0FsZpgNGu1rXwfgoeaK1BsBLv54cDHR8X3+ZQljMdrDNIomgAAwdGNUQzjKnTwaBlWsiNk6aLVhRvJzqtGFaS89YH1/f7SQdqKMtZEGDastRsnfGozVCWG3LqgGcqfqt9BnDqjUSjObPYWsSMDWArTQNkCAnuIW5iV8SuzjFYCg0n6Xb7mY5ORX90BGHvlNdRP7P2rFXT+sukXHemCdVmQ+kutmNPKmL8e7N5BiSzDOyb7taFtie2c/5I7Z2T7iY2R9eUO//hN4VIH9fLP0K7NRsdEVz2TmdZn1yeAfaW9cVfXwDHH6jnl4DDwFsXgK1l/WV+Dri1KsRAKsvN3IcPpMxsAKb0J75Kv1mciovkujyzNvTxwnHXw+H8I/nj4HVAAQxZ5TJMxN71/v90t+tr1yVm/JM4CvHt890P5UMhJ9TlQszaJEGb1RgQt2wLw5yXPSU8aFkSfDB696GN72Upha9388YVj46SpbNqWhc4miRuN8JGq9a/qVw3kczHgEJ73fm+1eJWZB/C5m2yvoiRWHacQgZhSYoA05uvGAAw0a8n1tpQljrY2QngLJmabJLUE7wF32xvnp4Xz0uz/Pn4z/Xt8gaYDWeoY2B8WqbGDdSmKmQThzYDJRkKeQYxkASkcjgE6D3wBMU2MZVl4qjD+68+kPHpkVy713A4dGbnpPvtPk4lxrxfbIVYtyYxkbkMKFonEXOaBqKmm1m7LUv99LjsQqDjINz2G8yJKS7xlJ8Q30a9LZToaOSQyEFotFJIkJiMHOBYRM+MD8MoN06ciexfkwskzMg42swxlgQEa0KGoAnGRdioSsTKnlKrHQB60/o09G7PYreJAXrTxN3ge3I+AvoiXq3/1xAnTbeZ6G+ldjOg12+2VEjqrdA5qrBvug7GUsSYigQ5yzPR/4+dzxxXq9aJuKZ7gt700bUFuCIfHbwUUOPF8/Pi+vbpYbm8vgUuy2C2FeN+ZZwI+5dRKJlXEq0ce3fcuxfq1YF3shbV38OdtzpItJJQi2vdULexuWss+4ESyWOMUvsIAReafNYO1SwUg6ttytVtslF70xw+1J+xpeQtkxub6o/L9awpIob19Bz3kTvb1CL7UCW74U4737YUFCrhkRRkX7kiMFHJ+jg3ZanRhZLFNK4xjSVpONLwT318749Hft6RfupkxQA0EpBMHoDOQg51VPcLm70NctAuP4beNz6vYezEHSfauSC+OT63lsp5hjPqW0eDYwqioCy9FqeFP48mOao6G7WMSwZMHcKU61nyku2mo5fEa6FbIJ5Rt9usutmYieWHnVrPzk2fy6ksVbrgpm91FY/dFAvjQKmMIWuBXBUn7kon4t7J/YBTzb5Z7uRyj0jgZLarhtVq+NogT6nXOIajI+BVWNvVKa/COhM6yQaH+k7z6vMa+hPTd9qDRq/WmDQDHElHrLNo5G7X/+3uNqh77c1uYoq5VZ3NZNivPyfmgWNDKf3HS6pd3w/VKi9Zpfag46CdcX0fm0qEfpG4tIhZaI+kqIsxh5s8mqe67yUZtr988hJ5aytiZK8i+KWI5o0B+Zkp44IZbaeFLzhTKgq0liKl1LTQImEH6D3pSuE19yPor2RyaJ6lzujbLEgnyQj+0sIISA21xqenTGgCd2dyaGI7VzNmQ3vcOekAg+UUNcUel/X0rDA1ye5+Q0HEnoikYKRERBqr+745k+Uxk4YljyVlOMrwjyxksu96wlDINSOEixgnXeh/G7zIJ7EV+hcRvHv0YcNCuBngPwQ2qgyq/peQxm2I1d9CM3QBHaSU7tzKYR3Ensy746BVm2KlnWNx9TFNpZAhu2YZ2oZOlLy4pBZ/7zPJSdFIn3l34w/cMXbEOWEPFelrb1rP1fYM1R2M2vTk2NpFaGRyRs6ceAPM/cPzP/c/n6zTTk8ZZdJnwMntV0o1myNoNgDCalzZkgPFuS4QbmAdMzKFlnjxwmYGqRoMfQwutE0wqNVfoaFfhs1IH6bVyt4rusZEseho7qr1YZ0BMDGpXXGqqpbVtcsesY0750Ol8gLeTQO5PwqT7HuqVEtbN/aiMM1CJgsFlR+HQC46M+8aOlQpSy2dM/Rw+N9mO2UnIu4/tmGJbOvDaSlWLbbUkxCGU+Q7AuTeBHp/umsYqhuGbxSezgFXF3eGV4Un5+fXH8SofHhL1T2urh7cbbmjmNJN+M7Zu6tDNeOifbTUjeQkZqe7Gq/f+5DtTs+uiU90aHLGL/PzMLr205f2dNvZXoBCZW6DZX1+elaT0V+p9lemeKf8xZUyn9zJyf5TYjoOsSXzO6lKGfPpj41hcjjmsadjCncvlE+9f+HAYfPC8T0RMyGjs2o++JvEdBWMS/vQ/Iiu1R6qB447n8gwpdi/kYbagMsO846x3LA2NtWaZApeaJyRjsLKJSb4zFYzot44IegoUZ/9jnSZwy5BYYpAxPted5GsV6KhYv/gcMxfP9PZVRJnT+IoOuDJfA4VaaUAb7RFHKV4d158eC22kYu4tXr8iBuiqqGCXAcNGcA0D02mt2e394S0mB2l5F7pcBAGHDNAznjx+TRMlgpTD4H6r/swm9ue0RgeUs3xtY8vrjc+hZAlEt74ThN3Lz7QdQxb/4pkHn5Zls/xJhXYJiEZvmT6ZPmvl062e31LG13NePHXF3PX0aCfe8Di8KANuYjfb1OZVBlZ66hGSGbxkoRdI/uAELzG+JBYkQqPfY0plCVFyl3Ta8QpudWMF+eXGHxpe7N4Xtz9+z+V13TXt6qgaXc/oOFzivTOYIp42Yv4VyqR0Qouz7rpn5P3VEjNeHJ5hs6XSnXLv93Hli7bbHj84O8ohJ6f6vraPev12ppRfG8W6dB3ibFkvLiw7kubxcPD8ueNyiu8f2dzkeKIZB6Xd/e/lqQ3RAopfntr8hJ5R6VInczlJZau1yF6As0fWjC7MFsEhNKWd6IWyfyhfZJ8g6w3SKaJg7hbZJ34AIqSiEXWDZFCkTINpNfZ9vISC1vKZYtkxkTMd3tPJhW7UE+Nqk/cEz5krH9z3H2H57Z7AYkhCGoGx05oy7c/Ii0Zzy5alVWFicx3kC7hUTGcpLLrN3LLtYO7Hzkj2SnX5dfngvWr20R6QAD9w7LPM2ufa2YJmRJZUidj1FlqqJ3mXa6mfNOeiqzC+j5g6dh8Roe/Z/IjADZe5i/bC3mWpXx+wOjzKSBZ1menp20C4oeIk1rPUQxfgp421dQgIVeTeomaYjubVUU9tSkh5/yaYOlFoVycQ11MIAW69tG1dJOBSCwPv0AvejaV12w0ikoJ+EnFlRanpqPmzwu2YQWhp24XfNFzDG3f1ICGnKHGTsckjmUcylKl3yZpvshb3n7XsX3rtDchqufHH+GmsVHKh29pqE68+Q4ZIGvIGuoB";

        byte[] decodedImageBytes = Base64.getDecoder().decode(encodedBase64Image);
        Files.write(Paths.get("output.jpg"), decodedImageBytes);*/
    }

    public static void XMLTest() throws Exception {
        String loc = "D:\\talpu\\Desktop\\Code\\Java\\My Projects\\Workspaces\\ProjectHydra\\TestScripts\\EnglishGrade9Art\\data\\snapshot_1.xml";
        InputStream inputStream = Files.newInputStream(Paths.get(loc));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inputStream);

        NodeList httpTaskList = document.getElementsByTagName("HTTPTask");
        for (int i = 0; i < httpTaskList.getLength(); i++) {
            Node httpTaskNode = httpTaskList.item(i);
            if (httpTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element httpTaskElement = (Element) httpTaskNode;
                String hostname = httpTaskElement.getAttribute("hostname");
                String url = httpTaskElement.getAttribute("url");
                System.out.println("Hostname: " + hostname);
                System.out.println("URL: " + url);

                NodeList httpRequestList = httpTaskElement.getElementsByTagName("HTTPRequest");
                for (int j = 0; j < httpRequestList.getLength(); j++) {
                    Node httpRequestNode = httpRequestList.item(j);
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
                                    System.out.println("HTTPHeaderEntity Name: " + name + ", ActualData: " + actualData);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
