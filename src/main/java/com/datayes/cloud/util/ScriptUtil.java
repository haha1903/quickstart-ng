package com.datayes.cloud.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: huhongsi
 * Date: 13-9-2
 * Time: 上午9:05
 * To change this template use File | Settings | File Templates.
 */

public class ScriptUtil {
    public enum Script{
        ZIMBRA_SERVER("ZIMBRA_SERVER"),
        REMOTE_SERVER("REMOTE_SERVER");

        Script(String scriptKey){
            key=scriptKey;
        }

        public String getStrValue(){
            return key;
        }

        private String key;
    }

    private HashMap<String,String> scripts;

    /**
     *
     * @param script
     * @return
     */
    public String getScript(Script script) throws Exception {
        return getScript(script.getStrValue());
    }

    /**
     *
     * @param scriptKey
     * @return
     */
    public String getScript(String scriptKey) throws Exception{
        if (!scripts.containsKey(scriptKey)) return "";

        BufferedReader reader = new BufferedReader(new FileReader(scripts.get(scriptKey)));
        StringBuilder sb = new StringBuilder("");
        String tempString;

        while ((tempString = reader.readLine()) != null) {
            sb.append(tempString).append("\n");
        }
        reader.close();

        return sb.toString();
    }

    public void setScripts(HashMap<String, String> scripts) {
        this.scripts = scripts;
    }
}
