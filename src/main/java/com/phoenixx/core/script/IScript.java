package com.phoenixx.core.script;

import com.phoenixx.core.protocol.IProtocol;
import com.phoenixx.core.snapshots.SnapshotManager;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:55 a.m. [09-05-2023]
 */
public interface IScript {

    /**
     * Retrieves an {@link Step} object instance based on the given ID
     * This method will loop through all actions, and transactions in order to find the correct step.
     *
     * @param ID The ID of the {@link Step} to search for.
     * @return {@link Step}
     */
    Step getStepFromID(int ID);

    /**
     * Name of the script
     * @return {@link String} Script name
     */
    String getName();

    /**
     * Instance of the {@link SnapshotManager} assigned to this script. Contains all the information regarding snapshots
     * @return {@link SnapshotManager}
     */
    SnapshotManager getSnapshotManager();

    /**
     * Instance of {@link ScriptFile} which contains data about all the current scripts files (action files, configs etc)
     * @return {@link ScriptFile}
     */
    ScriptFile getScriptFile();

    /**
     * {@link List} of {@link Action}'s that were loaded from the script
     * @return {@link List} of {@link Action}'s
     */
    List<Action> getActions();

    /**
     * {@link List} of {@link IProtocol}'s used by this {@link IScript}
     * @return {@link List} of {@link IProtocol}'s
     */
    List<IProtocol> getProtocols();
}
