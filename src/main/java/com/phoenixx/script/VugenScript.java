package com.phoenixx.script;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project VugenThinker
 * @since 11:53 AM [17-02-2023]
 */
public class VugenScript {
    // The folder containing all the scripts files
    private final File scriptFolder;
    // The main script file ending in .usr
    private File scriptFile;

    private String globalHFileName = "globals.h";
    private final List<String> actionFiles;
    private final List<Action> actions;

    private VugenScript(File scriptFolder) {
        this.scriptFolder = scriptFolder;
        this.actionFiles = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public static VugenScript buildScript(File scriptFolder) throws IOException {
        VugenScript vugenScript = new VugenScript(scriptFolder);

        // All files inside the main script folder
        for(File scriptFile: Objects.requireNonNull(scriptFolder.listFiles())) {

            // Check if the given file ends with the .usr extension to confirm it's a script file
            if (scriptFile.getName().contains(".usr")) {
                vugenScript.setScriptFile(scriptFile);

                BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
                String line = reader.readLine();

                // Read in all the lines from the script file
                boolean startReadingActions = false;
                while (line != null) {
                    // Load config file name
                   /*if (line.startsWith("Default Profile=")) {
                        vugenScript.setConfigName(line.replace("Default Profile=", ""));
                    }*/
                    if (!startReadingActions && line.equalsIgnoreCase("[Actions]")) {
                        startReadingActions = true;
                    } else if (startReadingActions) {
                        if (line.contains(".c")) {
                            vugenScript.addActionFile(line.split("=")[1]);
                        } else {
                            startReadingActions = false;
                        }
                    }

                    //System.out.println(line);
                    // read next line
                    line = reader.readLine();
                }
            }
        }
        return vugenScript;
    }

    public void loadActionFiles() throws IOException {
        for(String actionFile: this.getActionFiles()) {
            List<String> lines = new ArrayList<>();
            System.out.println("LOADING ACTION FILE: " + actionFile);

            String line;
            File configFolder = new File(scriptFolder, actionFile);
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
    public void setScriptFile(File scriptFile) {
        this.scriptFile = scriptFile;
    }

    public void addActionFile(String actionFile) {
        this.actionFiles.add(actionFile);
    }

    public List<String> getActionFiles() {
        return actionFiles;
    }

    public File getScriptFile() {
        return scriptFile;
    }

    public File getScriptFolder() {
        return scriptFolder;
    }
}