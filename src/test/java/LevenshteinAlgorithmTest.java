import com.google.gson.JsonElement;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:26 PM [14-05-2023]
 */
public class LevenshteinAlgorithmTest {
    private static final double SIMILARITY_THRESHOLD = 0.97; // Adjust the threshold as per your needs

    public static void main(String[] args) {
        List<DataObj> requests = new ArrayList<>();

        DataObj httpObject = new DataObj();
        httpObject.setBody("{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"SPxdbalgyfeuwlnpnx@yopmail.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"stage-ontsignin_froonlinebofenligneppeapp_1\",\"label\":\"FRO Online / BOF en ligne [PPE] APP\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://ok12static.oktacdn.com/assets/img/logos/default.6770228fb0dab49a1695ef440a5279bb.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"0oa4o4hntgMSsoIO25d7\",\"name\":\"FRO Online / BOF en ligne [PPE] APP\",\"uri\":\"http://www.okta.com/exk4o4hntfw81OmCo5d7\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://stage.signin.ontario.ca/login/step-up/redirect?stateToken=00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://stage.signin.ontario.ca/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}");
        requests.add(httpObject);

        httpObject = new DataObj();
        httpObject.setBody("{\"password\":\"fCMS_123!\",\"username\":\"SPxdbalgyfeuwlnpnx@yopmail.com\",\"options\":{\"warnBeforePasswordExpired\":true,\"multiOptionalFactorEnroll\":true},\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\"}");
        requests.add(httpObject);

        httpObject = new DataObj();
        httpObject.setBody("{\"nonce\":\"Kr6joNjBbhJaFV02k3V1rdc-Bj_Jl0ac\",\"expiresAt\":1680716247499}");
        requests.add(httpObject);

        httpObject = new DataObj();
        httpObject.setBody("{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\"}");
        requests.add(httpObject);

        // The lower this threshold is, the more precise value the algorithm will look for.
        int threshold = 3; // Maximum acceptable Levenshtein distance
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for(int i = 0; i < requests.size(); i++) {
            DataObj firstObj = requests.get(i);

            System.out.println("\n------------------ STARTING WITH #"+i +" ------------------");
            for(int j = i + 1; j < requests.size(); j++) {
                DataObj secondObj = requests.get(j);
                System.out.println("COMPARING TO OBJ: #" + j);

                for (Map.Entry<String, JsonElement> entry1 : firstObj.jsonObject.entrySet()) {
                    for (Map.Entry<String, JsonElement> entry2 : secondObj.jsonObject.entrySet()) {
                        if (entry1.getKey().equals(entry2.getKey())) {
                            if (entry1.getValue().isJsonPrimitive() && entry2.getValue().isJsonPrimitive()) {
                                String value1 = entry1.getValue().getAsString();
                                String value2 = entry2.getValue().getAsString();

                                int distance = levenshteinDistance.apply(value1, value2);
                                System.out.println("Key: " + entry1.getKey() + ", Levenshtein Distance: " + distance + " val1: " + value1 + " val2: " + value2);
                            }
                        }
                    }
                }
            }
        }

        /*for(int i = 0; i < requests.size(); i++) {
            DataObj firstObj = requests.get(i);

            for(int j = 1; j < requests.size() - 1; j++) {
                DataObj secondObj = requests.get(j);

                if(i == j) {
                    continue;
                }

                // Loop through all data in the first obj
                for(String[] valArray: firstObj.keyPairData.values()) {
                    for(String val1: valArray) {
                        System.out.println("VAL1: " + val1);
                        // Now here we loop through the second objects valArray and data in it
                        for(String[] valArray2: secondObj.keyPairData.values()) {
                            for(String val2: valArray2) {
                                // Now here we loop through the second objects valArray and data in it

                                int distance = levenshteinDistance.apply(val1, val2);
                                System.out.println("Distance: " + distance +  " between: " + val1 +  " and val: " + val2);
                                if (distance <= threshold) {
                                    System.out.println("Potential Correlation Candidate: " +
                                            "\n ->" + val1 + "" +
                                            "\n ->" + val2);
                                }
                            }
                        }

                    }
                }
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