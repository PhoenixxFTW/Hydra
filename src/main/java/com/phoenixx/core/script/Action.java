package com.phoenixx.core.script;

import com.phoenixx.util.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:21 a.m. [18/04/2023]
 */
public class Action {

    private final String actionName;
    private final List<Transaction> transactions;
    private final List<String> codeLines;

    public Action(String actionName, List<String> fileLines) {
        this.actionName = actionName;
        this.transactions = new ArrayList<>();
        this.codeLines = fileLines;
        this.readAction(fileLines);
    }

    /**
     * Parse the data in the given lines from the file, and retrieve the transactions sections
     * @param fileLines List of strings containing data from the action file
     */
    private Step currentStep = null;

    private void readAction(List<String> fileLines) {
        Transaction currentTransaction = null;
        Transaction hydraOtherTransaction = new Transaction("Hydra-Other", this);

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
            if (multiComment || line.startsWith("//")) {
                continue;
            }

            // Detect the start of a transaction using regex
            if(currentTransaction == null) {
                String foundTransaction = Parser.regexCheck(line, Parser.TRANSACTION_START);
                if(foundTransaction != null) {
                    //System.out.println("FOUND THE START TRANSACTION NAME: " + foundTransaction);
                    currentTransaction = new Transaction(foundTransaction, this);
                } else {
                    // In certain cases, we'll come across a step that is not inside a transaction which can't be ignored
                    // So it gets added to the hydraOtherTransaction
                    this.parseStep(line, hydraOtherTransaction);
                }
            } else {
                // Detect the end of a transaction with regex
                String foundTransaction = Parser.regexCheck(line, Parser.TRANSACTION_END);
                if(foundTransaction != null) {
                    //System.out.println("FOUND THE END TRANSACTION NAME: " + foundTransaction);
                    // Finish off the transaction and add it to the list
                    this.transactions.add(currentTransaction);
                    currentTransaction = null;
                } else {
                    this.parseStep(line, currentTransaction);
                }
            }
        }
        this.transactions.add(hydraOtherTransaction);
    }

    private void parseStep(String line, Transaction currentTransaction) {
        //TODO Add detection for web_url, web_custom_request, web_submit_data
        /**
         *  Load the t[x].inf, t[x].json, t[x]_RequestBody.txt, t[x]_RequestHeader.txt, t[x]_ResponseHeader.txt
         */
        if(currentStep == null) {
            //TODO Might be more efficient to do a simple .contains on the string and THEN grab the Pattern maybe?
            String function = "web_url";
            String foundRequest = Parser.regexCheck(line, Parser.WEB_URL);
            if(foundRequest == null) {
                function = "web_custom_request";
                foundRequest = Parser.regexCheck(line, Parser.WEB_CUSTOM_REQUEST);
            }
            if(foundRequest == null) {
                function = "web_submit_data";
                foundRequest = Parser.regexCheck(line, Parser.WEB_SUBMIT_DATA);
            }
            if(foundRequest == null) {
                function = "web_submit_form";
                foundRequest = Parser.regexCheck(line, Parser.WEB_SUBMIT_FORM);
            }
            if(foundRequest == null) {
                function = "web_image";
                foundRequest = Parser.regexCheck(line, Parser.WEB_IMAGE);
            }

            if (foundRequest != null) {
                currentStep = new Step(function, foundRequest, currentTransaction);
            }
        } else {
            // End of the current Step
            if(line.trim().equals("LAST);")) {
                currentTransaction.getSteps().add(currentStep);
                //System.out.println("Finished step: " + currentStep.getStepName() + " with steps: \n"+currentStep.getStepData());
                currentStep = null;
            } else {
                line = line.trim();
                // Remove the whitespace and the first and last two characters (the quotes and final comma)
                currentStep.getStepData().add(line.substring(1, line.length() - 2));
            }
        }
    }

    public String getActionName() {
        return actionName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<String> getCodeLines() {
        return codeLines;
    }
}
