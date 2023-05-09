package com.phoenixx.core.script.impl;

import com.phoenixx.core.protocol.IProtocol;
import com.phoenixx.core.script.AbstractScript;
import com.phoenixx.core.script.ScriptFile;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:51 a.m. [09-05-2023]
 */
public class VugenScript extends AbstractScript {

    public VugenScript(String name, ScriptFile scriptFile, List<IProtocol> protocols) {
        super(name, scriptFile, protocols);
    }
}
