package com.phoenixx;

import com.phoenixx.core.script.ScriptManager;
import com.phoenixx.ui.HydraWindow;
import com.phoenixx.ui.HydraWindowThread;

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

    private final ScriptManager scriptManager;

    private static HydraApp instance;

    public HydraApp() throws Exception {
        instance = this;
        this.scriptManager = new ScriptManager();

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

   */
        // TODO
        /**
         * 1) Read in all the action files and connect the transactions to the snapshots IDs
         * 2) Find the snapshots under results/iterations etc and load them into an object
         * 3) Determine correlations and parameters based off of the data retrieved
         */

    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public static HydraApp getInstance() {
        return instance;
    }
}
