import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:39 PM [14-05-2023]
 */
public class CorrelationFinder {
    public static void main(String[] args) {
        String requestDynamicData = "abc111def222";
        String[] responseCorpus = {"abc111def222", "xyz123abc456", "pqr789xyz123"};

        int threshold = 3; // Maximum acceptable Levenshtein distance

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for (String responseValue : responseCorpus) {
            int distance = levenshteinDistance.apply(requestDynamicData, responseValue);
            if (distance <= threshold) {
                System.out.println("Potential Correlation Candidate: " + responseValue);
            }
        }
    }
}