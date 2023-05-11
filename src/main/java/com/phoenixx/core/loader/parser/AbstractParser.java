package com.phoenixx.core.loader.parser;

import java.io.InputStream;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:42 a.m. [09-05-2023]
 */
public abstract class AbstractParser<T> {
    // The generic passed in is the object that the file will be parsed into

    public AbstractParser() {
        super();
    }

    public abstract AbstractParser<T> parse(String fileName, InputStream stream);

    public abstract T getDataObject();

    public abstract String getFileName();
}
