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
        boolean activeTransaction = false;
        Transaction currentTransaction = null;
        for(String line: fileLines) {
            // Multiline comment detection
            if (!multiComment && line.contains("/*")) {
                multiComment = true;
            }
            if (multiComment && line.contains("*/")) {
                multiComment = false;
            }

            // If the line is within a multi comment block or starts with a comment, we skip the line
            if (multiComment || line.startsWith("//")) {
                continue;
            }

            Pattern pattern;
            Matcher matcher;
            if(currentTransaction == null) {
                pattern = Pattern.compile("lr_start_transaction\\(\"([^\"]+)\"\\);");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String parameterValue = matcher.group(1);
                    System.out.println("FOUND THE START TRANSACTION NAME: " + parameterValue);
                    currentTransaction = new Transaction(parameterValue);
                }
            } else {
                //TODO Figure out a better way to do this, because it will only match with the "LR_AUTO" parameter
                pattern = Pattern.compile("lr_end_transaction\\(\"([^\"]+)\",\\s*LR_AUTO\\);");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String parameterValue = matcher.group(1);
                    System.out.println("FOUND THE END TRANSACTION NAME: " + parameterValue);
                    // Finish off the transaction and add it to the list
                    this.transactions.add(currentTransaction);
                    currentTransaction = null;
                }

                /**
                 *  Load the t[x].inf, t[x].json, t[x]_RequestBody.txt, t[x]_RequestHeader.txt, t[x]_ResponseHeader.txt
                 */
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
