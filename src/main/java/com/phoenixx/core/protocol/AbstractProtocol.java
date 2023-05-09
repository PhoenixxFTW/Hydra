package com.phoenixx.core.protocol;

import com.phoenixx.core.script.IFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:10 a.m. [09-05-2023]
 */
public abstract class AbstractProtocol implements IProtocol {

    private final String name;
    private final ProtocolTypes type;
    private final List<IFunction> functions;

    public AbstractProtocol(ProtocolTypes type) {
        this(type.getName(), type);
    }

    public AbstractProtocol(String name, ProtocolTypes type) {
        this.name = name;
        this.type = type;
        this.functions = new ArrayList<>();
    }

    @Override
    public void addFunction(IFunction function) {
        this.functions.add(function);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProtocolTypes getType() {
        return this.type;
    }

    @Override
    public List<IFunction> getFunctions() {
        return this.functions;
    }
}
