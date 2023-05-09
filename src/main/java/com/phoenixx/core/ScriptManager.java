package com.phoenixx.core;

import com.phoenixx.HydraApp;
import com.phoenixx.core.loader.FileLoader;
import com.phoenixx.core.loader.parser.impl.XMLParser;
import com.phoenixx.core.script.VugenScript;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:35 a.m. [09-05-2023]
 */
public class ScriptManager {

    private VugenScript loadedScript;

    public ScriptManager() {
        super();
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
         *      -> FileLoader will get the corresponding parser (i.e XMLParser) and load the file depending on that
         */

        FileLoader<XMLParser> loader = new FileLoader<>(new XMLParser(), 1);
        loader.loadFiles(new String[]{file.getAbsolutePath()+"/ScriptUploadMetadata.xml"});

        Document document = loader.getParser().getDataObject();

        String scriptName;
        List<String> actionFiles = new ArrayList<>();

        scriptName = document.getElementsByTagName("ScriptName").item(0).getTextContent();

        System.out.println("SCRIPT NAME: " + scriptName);

        NodeList nodes = document.getElementsByTagName("ActionFiles").item(0).getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName().equals("FileEntry")) {
                Element fileEntry = (Element) nodes.item(i);
                actionFiles.add(fileEntry.getAttribute("Name"));
            }
        }

        System.out.println("Action files: " + actionFiles);

        return null;


        /*VugenScript vugenScript = new VugenScript(file);
        vugenScript.getActions().addAll(actionFiles);
        this.setLoadedScript(vugenScript);

        return vugenScript;*/
    }

    public void setLoadedScript(VugenScript loadedScript) {
        this.loadedScript = loadedScript;
    }

    public VugenScript getLoadedScript() {
        return loadedScript;
    }
}
