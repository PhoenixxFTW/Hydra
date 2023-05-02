package com.phoenixx;

import com.phoenixx.core.VugenScript;
import com.phoenixx.ui.HydraWindow;
import com.phoenixx.ui.HydraWindowThread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:13 PM [17-04-2023]
 */
public class HydraApp {

    public static final String BUILD_VERSION = "1.0.0";
    public static final String BUILD_DATE = "April 21 2023";

    public static final boolean DEBUG_ENV = true;

    public static String currentPath;
    public final List<VugenScript> loadedScripts = new ArrayList<>();

    private static HydraApp instance;

    public HydraApp() throws Exception {
        instance = this;

        // Create an instance of the HydraWindow which is the main display for the hydra client
        HydraWindow hydraWindow = new HydraWindow();
        // Create an instance of the HydraWindowThread which we use to launch our window in a separate thread to keep everything clean
        HydraWindowThread windowThread = new HydraWindowThread(hydraWindow);

        // Run the window thread which starts our application
        windowThread.start();

  /*      System.out.println();
        if(DEBUG_ENV) {
            System.out.println("WARNING: The Hydra application is set to run in a DEBUG ENVIRONMENT. ");
        }

        currentPath = HydraApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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



        // TODO
        /**
         * 1) Read in all the action files and connect the transactions to the snapshots IDs
         * 2) Find the snapshots under results/iterations etc and load them into an object
         * 3) Determine correlations and parameters based off of the data retrieved
         */

    }

    public VugenScript loadScript(File file) throws IOException {
        System.out.println("UPDATED PATH: " + file.getAbsolutePath());

        if(!file.isDirectory()) {
            System.out.println("Current location is not a valid Vugen file!");
            return null;
        }

        if(!DEBUG_ENV) {
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
        this.loadedScripts.add(vugenScript);

        return vugenScript;
    }

    public static HydraApp getInstance() {
        return instance;
    }
}
