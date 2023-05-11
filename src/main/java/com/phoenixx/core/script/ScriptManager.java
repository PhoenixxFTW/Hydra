package com.phoenixx.core.script;

import com.phoenixx.HydraApp;
import com.phoenixx.core.loader.FileLoader;
import com.phoenixx.core.loader.parser.impl.XMLParser;
import com.phoenixx.core.protocol.IProtocol;
import com.phoenixx.core.protocol.ProtocolManager;
import com.phoenixx.core.protocol.ProtocolTypes;
import com.phoenixx.core.script.impl.VugenScript;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:35 a.m. [09-05-2023]
 */
public class ScriptManager {

    private VugenScript loadedScript;

    private final ProtocolManager protocolManager;

    public ScriptManager() {
        this.protocolManager = new ProtocolManager();
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

        //TODO Load "ScriptUploadMetadata.xml" which will be used to load the rest of the files

        /**
         * ======= THEORY =======
         * 1) Pass the "ScriptUploadMetadata.xml" into the FileLoader (
         *      -> FileLoader will get the corresponding parser (i.e. XMLParser) and load the file depending on that
         */

        FileLoader<XMLParser> loader = new FileLoader<>(new XMLParser(), 1);
        loader.loadFiles(new String[]{file.getAbsolutePath()+"/ScriptUploadMetadata.xml"});

        Document document = loader.getParser().getDataObject();

        String scriptName;
        String[] protocolNames;
        List<IProtocol> protocols = new ArrayList<>();
        List<String> actionFiles = new ArrayList<>();

        scriptName = document.getElementsByTagName("ScriptName").item(0).getTextContent();
        protocolNames = document.getElementsByTagName("Protocol").item(0).getTextContent().split(", ");

        for (String protocolName: protocolNames) {
            protocols.add(this.protocolManager.getProtocolFromType(ProtocolTypes.getFromName(protocolName)));
        }

        System.out.println("SCRIPT NAME: " + scriptName);
        System.out.println("Protocol NAME: " + Arrays.toString(protocolNames));

        NodeList nodes = document.getElementsByTagName("ActionFiles").item(0).getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName().equals("FileEntry")) {
                Element fileEntry = (Element) nodes.item(i);
                actionFiles.add(fileEntry.getAttribute("Name"));
            }
        }

        ScriptFile scriptFile = new ScriptFile(file, actionFiles);

        System.out.println("Action files: " + actionFiles);

        VugenScript vugenScript = new VugenScript(scriptName, scriptFile, protocols);
        this.setLoadedScript(vugenScript);

        return vugenScript;
    }

    public void setLoadedScript(VugenScript loadedScript) {
        this.loadedScript = loadedScript;
    }

    public VugenScript getLoadedScript() {
        return this.loadedScript;
    }
}
