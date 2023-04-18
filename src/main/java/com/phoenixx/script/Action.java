package com.phoenixx.script;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:21 a.m. [18/04/2023]
 */
public class Action {

    private final String actionName;
    private List<Transaction> transactions;

    public Action(String actionName, List<String> fileLines) {
        this.actionName = actionName;
        this.readAction(fileLines);
    }

    /**
     * Parse the data in the given lines from the file, and retrieve the transactions sections
     * @param fileLines List of strings containing data from the action file
     */
    private void readAction(List<String> fileLines) {

        boolean multiComment = false;
        for(String line: fileLines) {
            // Multiline comment detection
            if (!multiComment && line.contains("/*")) {
                multiComment = true;
            }
            if (multiComment && line.contains("*/")) {
                multiComment = false;
            }

            // If the line is within a multi comment block or starts with a comment, we skip the line
            if (!multiComment || line.startsWith("//")) {
                continue;
            }

            int commentIndex = line.indexOf("//");
            String checkString = "lr_start_transaction";

            /*if (line.contains(checkString) && !(line.contains("//") && !(commentIndex > line.indexOf(checkString)))) {
                int startIndex = line.indexOf(checkString) + checkString.length();

                // Find the end of the "lr_start_transaction" function
                int endIndex = -1;
                for(int i = 0; i < 30; i++) {
                    char foundChar = line.charAt(startIndex + i);

                    if (foundChar == ')') {
                        endIndex = i;
                        break;
                    }
                }

                String patternCheck = line.substring(startIndex, endIndex);
                System.out.println("FOUND PATTERN CHECK: " + patternCheck);
            }*/
            System.out.println("LINE: " + line);

            Pattern pattern = Pattern.compile("lr_start_transaction\\(\"([^\"]+)\"\\);");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String parameterValue = matcher.group(1);
                System.out.println("FOUND THE TRANSACTION NAME: " + parameterValue);

            }
        }
    }

    public String getActionName() {
        return actionName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
