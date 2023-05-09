package com.phoenixx.core.protocol;

import com.phoenixx.core.script.IFunction;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 7:17 PM [28-04-2023]
 */
public interface IProtocol {

    /**
     * Parses the given action file, and makes calls to {@link IFunction#parseFunction()} in order to load functions
     */
    void parseActionFile();

    void addFunction(IFunction function);

    /**
     * Get the name of the protocol.
     * @return String.
     */
    String getName();

    /**
     * Retrieve's the {@link ProtocolTypes} for this {@link IProtocol}
     * @return {@link ProtocolTypes}
     */
    ProtocolTypes getType();

    /**
     * The functions / methods that this protocol has in Vugen
     * @return List of {@link IFunction}'s
     */
    List<IFunction> getFunctions();
}
