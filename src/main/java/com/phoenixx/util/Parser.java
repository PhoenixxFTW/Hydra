package com.phoenixx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 2:02 p.m. [18/04/2023]
 */
public class Parser {

    // All the used Regex patterns here so that we're not constantly wasting resources recreating them
    public static final Pattern TRANSACTION_START = Pattern.compile("lr_start_transaction\\(\"([^\"]+)\"\\);");
    //TODO Figure out a better way to do this, because it will only match with the "LR_AUTO" parameter
    public static final Pattern TRANSACTION_END = Pattern.compile("lr_end_transaction\\(\"([^\"]+)\",\\s*LR_AUTO\\);");

    public static final Pattern WEB_CUSTOM_REQUEST = Pattern.compile("web_custom_request\\(\"([^\"]+)\"");
    public static final Pattern WEB_SUBMIT_DATA = Pattern.compile("web_submit_data\\(\"([^\"]+)\"");
    public static final Pattern WEB_SUBMIT_FORM = Pattern.compile("web_submit_form\\(\"([^\"]+)\"");
    public static final Pattern WEB_IMAGE = Pattern.compile("web_image\\(\"([^\"]+)\"");
    public static final Pattern WEB_URL = Pattern.compile("web_url\\(\"([^\"]+)\"");

    /**
     * Returns the regex value searched for if it exists, otherwise returns Null
     * @param input The given input string to search
     * @param regex The regex query
     * @return The string retrieved if found, or Null
     */
    public static String regexCheck(String input, Object regex) {
        Pattern pattern;
        if(regex instanceof Pattern) {
            pattern = (Pattern) regex;
        } else {
            pattern = Pattern.compile((String) regex);
        }
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
