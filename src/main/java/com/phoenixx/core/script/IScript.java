package com.phoenixx.core.script;

import com.phoenixx.core.protocol.IProtocol;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:55 a.m. [09-05-2023]
 */
public interface IScript {

    String getName();

    List<IProtocol> getProtocols();
}
