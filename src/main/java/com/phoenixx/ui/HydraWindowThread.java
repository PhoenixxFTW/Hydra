package com.phoenixx.ui;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:19 AM [19-04-2023]
 */
public class HydraWindowThread extends Thread {
    public HydraWindow hydraWindow;

    public HydraWindowThread(HydraWindow hydraWindow){
        this.hydraWindow = hydraWindow;
    }

    public void run(){
        javafx.application.Application.launch(HydraWindow.class);
    }
}
