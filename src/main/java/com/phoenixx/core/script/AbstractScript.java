package com.phoenixx.core.script;

import com.phoenixx.core.protocol.IProtocol;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:22 a.m. [09-05-2023]
 */
public abstract class AbstractScript implements IScript {

    private final String name;
    private final List<IProtocol> protocols;

    public AbstractScript(String name, List<IProtocol> protocols) {
        this.name = name;
        this.protocols = protocols;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<IProtocol> getProtocols() {
        return this.protocols;
    }
}
