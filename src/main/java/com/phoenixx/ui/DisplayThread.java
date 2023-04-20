package com.phoenixx.ui;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:19 AM [19-04-2023]
 */
public class DisplayThread extends Thread {
    public HydraWindow luckHydraWindow;

    public DisplayThread(HydraWindow luckHydraWindow){
        this.luckHydraWindow = luckHydraWindow;
    }

    public void run(){
        javafx.application.Application.launch(HydraWindow.class);
    }
}
