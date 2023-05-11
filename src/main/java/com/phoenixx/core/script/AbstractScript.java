package com.phoenixx.core.script;

import com.phoenixx.core.protocol.IProtocol;
import com.phoenixx.core.snapshots.SnapshotManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:22 a.m. [09-05-2023]
 */
public abstract class AbstractScript implements IScript {

    private final String name;
    private final ScriptFile scriptFile;
    private final SnapshotManager snapshotManager;

    private final List<Action> actions;
    private final List<IProtocol> protocols;

    public AbstractScript(String name, ScriptFile scriptFile, List<IProtocol> protocols) {
        this.name = name;
        this.scriptFile = scriptFile;
        this.actions = new ArrayList<>();
        this.protocols = protocols;

        try {
            this.loadActionFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.snapshotManager = new SnapshotManager(this);
    }

    /**
     * //TODO This needs to be redone, we need to utilize the {@link com.phoenixx.core.loader.FileLoader} and {@link com.phoenixx.script.parser.IParser}
     * @throws IOException
     */
    @Deprecated
    private void loadActionFiles() throws IOException {
        for(String actionFile: this.scriptFile.getActionFiles()) {
            List<String> lines = new ArrayList<>();
            System.out.println("LOADING ACTION FILE: " + actionFile);

            String line;
            File configFolder = new File(this.scriptFile.getScriptFolder(), actionFile);
            FileReader fileReader = new FileReader(configFolder);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                //System.out.println("LINE: " + line);
            }

            fileReader.close();
            bufferedReader.close();

            this.actions.add(new Action(actionFile, lines));
        }
    }

    @Override
    public SnapshotManager getSnapshotManager() {
        return this.snapshotManager;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ScriptFile getScriptFile() {
        return this.scriptFile;
    }

    @Override
    public List<Action> getActions() {
        return this.actions;
    }

    @Override
    public List<IProtocol> getProtocols() {
        return this.protocols;
    }
}
