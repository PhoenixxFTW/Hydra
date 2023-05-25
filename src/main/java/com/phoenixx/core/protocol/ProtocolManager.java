package com.phoenixx.core.protocol;

import com.phoenixx.core.protocol.impl.WebProtocol;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:58 a.m. [09-05-2023]
 */
public class ProtocolManager {

    // Map of ProtocolType to its corresponding protocol implementation class
    private final Map<ProtocolTypes, Class<? extends IProtocol>> loadedProtocols;

    public ProtocolManager() {
        this.loadedProtocols = new HashMap<>();

        this.loadProtocols();
    }

    /**
     * Registers and loads the given protocols
     */
    public void loadProtocols() {
        this.registerProtocol(ProtocolTypes.WEB, WebProtocol.class);
    }

    /**
     * Registers the given protocols
     *
     * @param type The {@link ProtocolTypes} of the {@link IProtocol}
     * @param protocolClass The {@link Class} of the {@link IProtocol}
     */
    public void registerProtocol(ProtocolTypes type, Class<? extends IProtocol> protocolClass) {
        this.getLoadedProtocols().put(type, protocolClass);
    }

    // Please dear god make this work

    /**
     * Creates a new instance of the passed in {@link IProtocol} class
     *
     * @param protocol The class of a {@link IProtocol} to instantiate
     * @return New instance of {@link IProtocol}
     */
    public IProtocol createInstanceOfProtocol(Class<? extends IProtocol> protocol) {
        try {
            Constructor<?> constructor = protocol.getConstructor();
            Object object = constructor.newInstance();
            return (IProtocol) object;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IProtocol getProtocolFromType(ProtocolTypes type) {
        //FIXME Could also try using class#newInstance();
        if(this.getLoadedProtocols().get(type) == null) {
            return null;
        }
        return this.createInstanceOfProtocol(this.getLoadedProtocols().getOrDefault(type, null));
    }

    public Map<ProtocolTypes, Class<? extends IProtocol>> getLoadedProtocols() {
        return this.loadedProtocols;
    }
}
