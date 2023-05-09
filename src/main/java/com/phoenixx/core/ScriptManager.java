package com.phoenixx.core;

import com.phoenixx.HydraApp;
import com.phoenixx.core.script.VugenScript;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:35 a.m. [09-05-2023]
 */
public class ScriptManager {

    private VugenScript loadedScript;

    public ScriptManager() {
        super();
    }

    public VugenScript loadScript(File file) throws IOException {
        System.out.println("UPDATED PATH: " + file.getAbsolutePath());

        if(!file.isDirectory()) {
            System.out.println("Current location is not a valid Vugen file!");
            return null;
        }

        if(!HydraApp.DEBUG_ENV) {
            if (!file.getName().contains(".usr")) {
                System.out.println("File does not end in .usr!");
                return null;
            }
        }

        if(Objects.requireNonNull(file.list()).length == 0) {
            System.out.println("No scripts found in folder: " + file.getName());
            return null;
        }

        VugenScript vugenScript = new VugenScript(file);
        this.setLoadedScript(vugenScript);

        return vugenScript;
    }

    public void setLoadedScript(VugenScript loadedScript) {
        this.loadedScript = loadedScript;
    }

    public VugenScript getLoadedScript() {
        return loadedScript;
    }
}
