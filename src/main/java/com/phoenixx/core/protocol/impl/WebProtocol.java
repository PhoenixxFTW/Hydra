package com.phoenixx.core.protocol.impl;

import com.phoenixx.core.protocol.AbstractProtocol;
import com.phoenixx.core.protocol.ProtocolTypes;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:11 a.m. [09-05-2023]
 */
public class WebProtocol extends AbstractProtocol {
    public WebProtocol() {
        super(ProtocolTypes.WEB);
    }

    //TODO Needs a method to parse functions
    @Override
    public void parseActionFile() {

    }
}
