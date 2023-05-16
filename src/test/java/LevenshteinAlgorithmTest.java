import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phoenixx.core.snapshots.HTTPObject;
import com.phoenixx.core.snapshots.data.TreeNode;
import com.phoenixx.core.snapshots.impl.Snapshot;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:26 PM [14-05-2023]
 */
public class LevenshteinAlgorithmTest {
    private static final double SIMILARITY_THRESHOLD = 0.97; // Adjust the threshold as per your needs

    public static void main(String[] args) {
        List<TreeNode> treeNodes = new ArrayList<>();
        List<HTTPObject> requests = new ArrayList<>();

        /*for(int i = 0; i < 5; i++) {
            HTTPObject httpObject = new HTTPObject("test", "test");
            httpObject.setBody("{\"type\":\"DF_ADEOS\",\"id\":\""+i+"-14DK29M\",\"status\":\"In Progress\",\"contactId\":\"1-14D9RX8\",\"documentAdminId\":\"1-14CZNDD\",\"caseId\":\"1-14D9RWW\",\"formLastUpdatedTS\":\"04/05/2023 13:39:52\",\"signedDate\":\"\",\"signedBy\":\"\",\"signedFlag\":false,\"formData\":{\"id\":\"1-14DK29N\",\"payorSection\":{\"emailAddress\":\"JhonDoe@ontario.ca\",\"addressType\":\"Canada\",\"canadaUsaAddress\":{\"unitNumber\":\"\",\"streetNumber\":\"123\",\"streetName\":\"Drury\",\"poBox\":\"\",\"city\":\"Toronto\",\"country\":\"Canada\",\"province\":\"ON\",\"addressLine1\":null,\"addressLine2\":null,\"postalCode\":\"A1A 1A1\",\"addressInfo\":null,\"town\":\"\",\"state\":null,\"zipCode\":null},\"internationalAddress\":null,\"phoneNumType\":\"USA\",\"phoneNumber\":{\"homePhone\":\"(905) 428-5395\",\"cellPhone\":\"(123) 465-7685\",\"businessPhone\":\"\"},\"payorDtls\":{\"firstName\":\"John\",\"lastName\":\"Doe\"}},\"recipientSection\":{\"emailAddress\":\"\",\"addressType\":\"\",\"canadaUsaAddress\":null,\"internationalAddress\":null,\"phoneNumType\":\"\",\"phoneNumber\":{\"homePhone\":\"\",\"cellPhone\":\"\",\"businessPhone\":\"\"},\"recipientDtls\":{\"firstName\":\"\",\"lastName\":\"\"}},\"typeOfSupportList\":{\"supportOrderTypeSpouseFlag\":false,\"supportOrderTypeChildFlag\":false},\"spousalDtls\":null,\"childDtlsArray\":null},\"formType\":\"DF_ADEOS\",\"serviceRequestNum\":null}");
            JsonObject jsonObject = JsonParser.parseString(httpObject.getBody()).getAsJsonObject();

            treeNodes.add(Snapshot.constructTree(jsonObject));
        }*/

        HTTPObject httpObject = new HTTPObject("test", "test");
        httpObject.setBody("{\n" +
                "    \"_id\": \"6463969d293ec28993deac4c\",\n" +
                "    \"index\": 0,\n" +
                "    \"guid\": \"08c69379-b866-4074-a8f5-9e27eaec8590\",\n" +
                "    \"isActive\": true,\n" +
                "    \"balance\": \"$2,867.99\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 32,\n" +
                "    \"eyeColor\": \"blue\",\n" +
                "    \"name\": \"Byrd Estes\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"company\": \"OPTIQUE\",\n" +
                "    \"email\": \"byrdestes@optique.com\",\n" +
                "    \"phone\": \"+1 (804) 520-2682\"\n" +
                "  }");

        JsonObject jsonObject = JsonParser.parseString(httpObject.getBody()).getAsJsonObject();
        treeNodes.add(Snapshot.constructTree(jsonObject));
        requests.add(httpObject);

        httpObject = new HTTPObject("test", "test");
        httpObject.setBody("{\n" +
                "    \"_id\": \"6463969ddc0245c90c6e0513\",\n" +
                "    \"index\": 1,\n" +
                "    \"guid\": \"489b0217-ad65-4c65-88b7-1557904b2413\",\n" +
                "    \"isActive\": false,\n" +
                "    \"balance\": \"$2,069.87\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 33,\n" +
                "    \"eyeColor\": \"green\",\n" +
                "    \"name\": \"Freida Briggs\",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"company\": \"MAGNINA\",\n" +
                "    \"email\": \"freidabriggs@magnina.com\",\n" +
                "    \"phone\": \"+1 (820) 590-3556\"\n" +
                "  }");
        jsonObject = JsonParser.parseString(httpObject.getBody()).getAsJsonObject();
        treeNodes.add(Snapshot.constructTree(jsonObject));
        requests.add(httpObject);

        httpObject = new HTTPObject("test", "test");
        httpObject.setBody("{\n" +
                "    \"_id\": \"6463969d293ec28993deac4c\",\n" +
                "    \"index\": 2,\n" +
                "    \"guid\": \"8aceaf88-5d88-49bd-a01e-be7157831f1c\",\n" +
                "    \"isActive\": false,\n" +
                "    \"balance\": \"$2,251.09\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 34,\n" +
                "    \"eyeColor\": \"blue\",\n" +
                "    \"name\": \"Yesenia Bernard\",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"company\": \"ILLUMITY\",\n" +
                "    \"email\": \"yeseniabernard@illumity.com\",\n" +
                "    \"phone\": \"+1 (920) 559-2800\"\n" +
                "  }");
        jsonObject = JsonParser.parseString(httpObject.getBody()).getAsJsonObject();
        treeNodes.add(Snapshot.constructTree(jsonObject));
        requests.add(httpObject);

        // The lower this threshold is, the more precise value the algorithm will look for.
        int threshold = 3; // Maximum acceptable Levenshtein distance
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        for(int i = 0; i < requests.size(); i++) {
            HTTPObject firstObj = requests.get(i);

            for(int j = 1; j < requests.size() - 1; j++) {
                HTTPObject secondObj = requests.get(j);

                if(i == j) {
                    continue;
                }

                int distance = levenshteinDistance.apply(firstObj.getBody(), secondObj.getBody());
                System.out.println("Distance: " + distance +  " between: " + i +  " and val: " + j);
                if (distance <= threshold) {
                    System.out.println("Potential Correlation Candidate: " +
                            "\n ->" + firstObj.getBody() + "" +
                            "\n ->" + secondObj.getBody());
                }
            }
        }

       /* // Loop through all tree's
        for(int i = 0; i < treeNodes.size(); i++) {
            TreeNode tree1 = treeNodes.get(i);

            // Loop through all trees starting from the 2nd element (1st index)
            for(int j = 1; j < treeNodes.size() - 1; j++) {
                TreeNode tree2 = treeNodes.get(j);

                LevenshteinDistance distance = new LevenshteinDistance();
                int similarityScore = Snapshot.calculateSimilarity(node1, node2, distance);
                System.out.println("Similarity score: " + similarityScore);
            }
        }*/
    }

    public static void testOne() {
        String responseStateToken = "00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E";
        /*List<String> requestBodies = new ArrayList<>(); // Populate your list of request bodies
        requestBodies.add("test");
        requestBodies.add("tes");
        requestBodies.add("HELLOOOOO");
        requestBodies.add("nothing");*/

        List<String> requestBodies = Arrays.asList(
                "{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"SPxdbalgyfeuwlnpnx@yopmail.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"stage-ontsignin_froonlinebofenligneppeapp_1\",\"label\":\"FRO Online / BOF en ligne [PPE] APP\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://ok12static.oktacdn.com/assets/img/logos/default.6770228fb0dab49a1695ef440a5279bb.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"0oa4o4hntgMSsoIO25d7\",\"name\":\"FRO Online / BOF en ligne [PPE] APP\",\"uri\":\"http://www.okta.com/exk4o4hntfw81OmCo5d7\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://stage.signin.ontario.ca/login/step-up/redirect?stateToken=00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://stage.signin.ontario.ca/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}",
                "{\"stateToken\":\"1234567890\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"asd\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"test@example.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"test_app\",\"label\":\"Test App\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://example.com/logo.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"issuer_id\",\"name\":\"Test App\",\"uri\":\"http://www.example.com\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://example.com/login/step-up/redirect?stateToken=1234567890\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://example.com/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}",
                "{\"stateToken\":\"5\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"asd\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"test@example.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"test_app\",\"label\":\"Test App\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://example.com/logo.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"issuer_id\",\"name\":\"Test App\",\"uri\":\"http://www.example.com\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://example.com/login/step-up/redirect?stateToken=1234567890\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://example.com/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}",
                "{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kma\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"gasdqe\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"test@example.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"test_app\",\"label\":\"Test App\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://example.com/logo.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"issuer_id\",\"name\":\"Test App\",\"uri\":\"http://www.example.com\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://example.com/login/step-up/redirect?stateToken=1234567890\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://example.com/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}"

        );

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        List<String> similarStrings = new ArrayList<>();

        for (String requestBody : requestBodies) {
            double similarity = levenshteinDistance.apply(responseStateToken, requestBody);
            double normalizedSimilarity = similarity / Math.max(responseStateToken.length(), requestBody.length());

            System.out.println("Normalized: " + normalizedSimilarity);
            System.out.println("Sim: " + similarity);
            if (normalizedSimilarity >= SIMILARITY_THRESHOLD) {
                similarStrings.add(requestBody);
            }
        }

        // Do something with the similar strings (e.g., print, store, process further)
        System.out.println("Similar strings: ");
        for(String sim: similarStrings) {
            System.out.println(sim);
        }
    }
}