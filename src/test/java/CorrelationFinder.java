import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:39 PM [14-05-2023]
 */
public class CorrelationFinder {
    public static void main(String[] args) {
        String requestDynamicData = "abc111def222";
        String[] responseCorpus = {"abc111def222", "xyz123abc456", "pqr789xyz123", "c111d"};

        // The lower this threshold is, the more precise value the algorithm will look for.
        int threshold = 3; // Maximum acceptable Levenshtein distance

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for (String responseValue : responseCorpus) {
            int distance = levenshteinDistance.apply(requestDynamicData, responseValue);
            System.out.println("Distance: " + distance +  " between: " + requestDynamicData +  " and val: " + responseValue);
            if (distance <= threshold) {
                System.out.println("Potential Correlation Candidate: " + responseValue);
            }
        }
    }
}

class TreeSimilarity {
    private static class TreeNode {
        private String value;
        private List<TreeNode> children;

        public TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    private static TreeNode constructTree(JsonObject jsonObject){
        TreeNode root = new TreeNode(null);
        buildTree(root, jsonObject);
        return root;
    }

    private static void buildTree(TreeNode node, JsonObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            TreeNode child = new TreeNode(value.toString());
            node.children.add(child);
            if (value instanceof JsonObject) {
                buildTree(child, (JsonObject) value);
            }
        }
    }

    private static int calculateSimilarity(TreeNode tree1, TreeNode tree2, LevenshteinDistance distance) {
        if (tree1.value == null || tree2.value == null) {
            return 0; // Not comparable
        }

        int similarity = 0;
        if (distance.apply(tree1.value, tree2.value) < 3) { // Adjust the threshold as needed
            similarity += 1;
        }

        for (int i = 0; i < tree1.children.size(); i++) {
            TreeNode child1 = tree1.children.get(i);
            TreeNode child2 = tree2.children.get(i);
            similarity += calculateSimilarity(child1, child2, distance);
        }

        return similarity;
    }

    public static void main(String[] args) {
        try {
            // Read JSON file
            String jsonString = "{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"SPxdbalgyfeuwlnpnx@yopmail.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"stage-ontsignin_froonlinebofenligneppeapp_1\",\"label\":\"FRO Online / BOF en ligne [PPE] APP\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://ok12static.oktacdn.com/assets/img/logos/default.6770228fb0dab49a1695ef440a5279bb.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"0oa4o4hntgMSsoIO25d7\",\"name\":\"FRO Online / BOF en ligne [PPE] APP\",\"uri\":\"http://www.okta.com/exk4o4hntfw81OmCo5d7\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://stage.signin.ontario.ca/login/step-up/redirect?stateToken=00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://stage.signin.ontario.ca/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}"; // JSON content as a string
            //JsonObject jsonObject = new JsonObject(jsonString);
            Gson gson = new Gson();
            JsonObject jsonObject1 = gson.fromJson(jsonString, JsonObject.class);
            jsonString = "{\"stateToken\":\"00g9fOSgpK2XUXAoJ5kmaKKT8s94KoL68zlCXiOY7E\",\"type\":\"SESSION_STEP_UP\",\"expiresAt\":\"2023-04-05T17:41:58.000Z\",\"status\":\"SUCCESS\",\"_embedded\":{\"user\":{\"id\":\"00u8xqs7yjxddNfpg5d7\",\"passwordChanged\":\"2023-03-31T16:09:43.000Z\",\"profile\":{\"login\":\"test@example.com\",\"firstName\":null,\"lastName\":null,\"locale\":\"en_US\",\"timeZone\":\"America/Los_Angeles\"}},\"target\":{\"type\":\"APP\",\"name\":\"test_app\",\"label\":\"Test App\",\"_links\":{\"logo\":{\"name\":\"medium\",\"href\":\"https://example.com/logo.png\",\"type\":\"image/png\"}}},\"authentication\":{\"protocol\":\"SAML2.0\",\"issuer\":{\"id\":\"issuer_id\",\"name\":\"Test App\",\"uri\":\"http://www.example.com\"}}},\"_links\":{\"next\":{\"name\":\"original\",\"href\":\"https://example.com/login/step-up/redirect?stateToken=1234567890\",\"hints\":{\"allow\":[\"GET\"]}},\"cancel\":{\"href\":\"https://example.com/api/v1/authn/cancel\",\"hints\":{\"allow\":[\"POST\"]}}}}";
            JsonObject jsonObject2 = gson.fromJson(jsonString, JsonObject.class);

            // Construct tree from JSON data
            TreeNode tree = constructTree(jsonObject1);

            // Compare multiple trees
            TreeNode tree1 = constructTree(jsonObject1);
            TreeNode tree2 = constructTree(jsonObject2);

            LevenshteinDistance distance = new LevenshteinDistance();
            int similarityScore = calculateSimilarity(tree1, tree2, distance);
            System.out.println("Similarity score: " + similarityScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}