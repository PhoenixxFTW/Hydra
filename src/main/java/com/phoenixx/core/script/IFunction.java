package com.phoenixx.core.script;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:58 a.m. [09-05-2023]
 */
public interface IFunction {

    /**
     * Parses the function from the given data into its corresponding attributes
     */
    void parseFunction();

    /**
     * The name of the function
     * @return {@link String} Function name
     */
    String getName();
}
