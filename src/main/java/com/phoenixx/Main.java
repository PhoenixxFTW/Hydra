package com.phoenixx;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:13 PM [17-04-2023]
 */
public class Main {
    public static boolean DEBUG_ENV = true;
    public static String currentPath;

    public static void main(String[] args) throws Exception {
        System.out.println();
        if(DEBUG_ENV) {
            System.out.println("WARNING: The Hydra application is set to run in a DEBUG ENVIRONMENT. ");
        }

        currentPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        currentPath = URLDecoder.decode(currentPath, "UTF-8");
        currentPath = currentPath.replace("\\","/");

        if(DEBUG_ENV) {
            // We remove the last part of the string if this is running in a workspace from the build's folder (/build/classes/java/main/)
            currentPath = currentPath.substring(1, currentPath.length() - 24);
            currentPath += "TestScripts/";
        }

        System.out.println("Path of current program: " + currentPath);

        File folder = new File(currentPath);
        if(!DEBUG_ENV) {
            folder = folder.getParentFile();
        }

        System.out.println("UPDATED PATH: " + folder.getAbsolutePath());

        if(!folder.isDirectory()) {
            System.out.println("Current location is not a directory!");
            return;
        }

        if(Objects.requireNonNull(folder.list()).length == 0) {
            System.out.println("No scripts found in folder: " + folder.getName());
            return;
        }

        System.out.println("Found " + Objects.requireNonNull(folder.list()).length + " potential script files, loading now...");

        List<File> vugenScripts = new ArrayList<>();

        for(File fileFound: Objects.requireNonNull(folder.listFiles())) {
            if(fileFound.isDirectory()) {
                //System.out.println("Directory: " + fileFound.getName());
                boolean isVugenScript = false;
                for(String fileName: Objects.requireNonNull(fileFound.list())) {
                    //System.out.println("\t> " + fileName);
                    if(fileName.contains(".usr")) {
                        isVugenScript = true;
                        break;
                    }
                }
                if(isVugenScript) {
                    System.out.println("Found Vugen script: " + fileFound.getName());
                    vugenScripts.add(fileFound);
                }
            }
        }

        // TODO
        /**
         * 1) Read in all the action files and connect the transactions to the snapshots IDs
         * 2) Find the snapshots under results/iterations etc and load them into an object
         * 3) Determine correlations and parameters based off of the data retrieved
         */
    }
}
