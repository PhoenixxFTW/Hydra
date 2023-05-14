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
    private static final double SIMILARITY_THRESHOLD = 0.3; // Adjust the threshold as per your needs

    public static void main(String[] args) {
        String responseStateToken = "00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E";
        /*List<String> requestBodies = new ArrayList<>(); // Populate your list of request bodies
        requestBodies.add("test");
        requestBodies.add("tes");
        requestBodies.add("HELLOOOOO");
        requestBodies.add("nothing");*/

        List<String> requestBodies = Arrays.asList(
                "{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"SPxdbalgyfeuwlnpnx@yopmail.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"stage-ontsignin_froonlinebofenligneppeapp_1\",\"label\":\"FRO Online / BOF en ligne [PPE] APP\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://ok12static.oktacdn.com/assets/img/logos/default.6770228fb0dab49a1695ef440a5279bb.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"0oa4o4hntgMSsoIO25d7\",\"name\":\"FRO Online / BOF en ligne [PPE] APP\",\"uri\":\"http://www.okta.com/exk4o4hntfw81OmCo5d7\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://stage.signin.ontario.ca/login/step-up/redirect?stateToken=00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://stage.signin.ontario.ca/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}",
                "{\"stateToken\":\"1234567890\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"test@example.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"test_app\",\"label\":\"Test App\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://example.com/logo.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"issuer_id\",\"name\":\"Test App\",\"uri\":\"http://www.example.com\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://example.com/login/step-up/redirect?stateToken=1234567890\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://example.com/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}"
        );

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        List<String> similarStrings = new ArrayList<>();

        for (String requestBody : requestBodies) {
            double similarity = levenshteinDistance.apply(responseStateToken, requestBody);
            double normalizedSimilarity = similarity / Math.max(responseStateToken.length(), requestBody.length());

            if (normalizedSimilarity >= SIMILARITY_THRESHOLD) {
                similarStrings.add(requestBody);
            }
        }

        // Do something with the similar strings (e.g., print, store, process further)
        System.out.println("Similar strings: " + similarStrings);
    }
}