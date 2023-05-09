package com.phoenixx.core.script;

import java.io.File;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:46 a.m. [09-05-2023]
 */
public class ScriptFile {
    // The folder containing all the scripts files
    private final File scriptFolder;
    // Names of all the action files
    private final List<String> actionFiles;

    public ScriptFile(File scriptFolder, List<String> actionFiles) {
        this.scriptFolder = scriptFolder;
        this.actionFiles = actionFiles;
    }

    public String getPathToScript() {
        return this.scriptFolder.getAbsolutePath();
    }

    public File getScriptFolder() {
        return this.scriptFolder;
    }

    public List<String> getActionFiles() {
        return this.actionFiles;
    }
}
