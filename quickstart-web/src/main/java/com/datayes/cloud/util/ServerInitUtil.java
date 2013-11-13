package com.datayes.cloud.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ContextResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: huhongsi
 * Date: 13-9-2
 * Time: 上午9:05
 */

public class ServerInitUtil {
    public enum ServerType{
        ZIMBRA_SERVER("zimbra"),
        REMOTE_SERVER("remote");

        ServerType(String scriptKey){
            key=scriptKey;
        }

        public String getStrValue(){
            return key;
        }

        private String key;
    }

    public enum ServerFlavor{
        tiny("m1.tiny"),
        small("m1.small"),
        medium("m1.medium"),
        large("m1.large"),
        xlarge("m1.xlarge");

        private String flavor;

        ServerFlavor(String flavor){
            this.flavor = flavor;
        }

        public String getStrValue(){
            return flavor;
        }
    }

    private static HashMap<String,String> scripts;
    private static HashMap<String,String> images;

    //TODO: load from config file
    static{
        scripts = new HashMap<String, String>();
        images = new HashMap<String, String>();
        scripts.put(ServerType.ZIMBRA_SERVER.getStrValue(),"serverscript/zimbra/ubuntu12.04/installzimbra-ubuntu12.04-passwd");
        images.put(ServerType.ZIMBRA_SERVER.getStrValue(),"cirros");
    }

    public static String getImageUrl(ServerType type){
        return getImageUrl(type.getStrValue());
    }

    public static String getImageUrl(String serverType){
        if (serverType==null || serverType.isEmpty() || !images.containsKey(serverType)) return "";
        return images.get(serverType);
    }

    /**
     *
     * @param type
     * @param domainName
     * @return
     * @throws Exception
     */
    public static String getScript(ServerType type, String domainName, boolean encoded) throws Exception {
        return getScript(type.getStrValue(), domainName, encoded);
    }

    /**
     *
     * @param scriptKey
     * @return
     */
    public static String getScript(String scriptKey, String domainName, boolean encoded) throws Exception{
        if (scriptKey==null || scriptKey.isEmpty() || !scripts.containsKey(scriptKey)) return "";
        ClassPathResource resource = new ClassPathResource(scripts.get(scriptKey));
        BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
        StringBuilder sb = new StringBuilder("");
        String tempString;

        while ((tempString = reader.readLine()) != null) {
            tempString = processScript(tempString, scriptKey, domainName);
            sb.append(tempString).append("\n");
        }
        reader.close();

        String scriptStr = sb.toString();
        if (encoded) scriptStr = Base64.encodeBase64String(scriptStr.getBytes());
        return scriptStr;
    }

    private static String processScript(String line, String scriptKey, String domainName){
        if (scriptKey.equalsIgnoreCase(ServerType.ZIMBRA_SERVER.getStrValue())){
            if (line.startsWith("fullhost")){
                line="fullhost=$host'."+domainName+"'";
            }
        }
        return line;
    }

    public static void setScripts(HashMap<String, String> scriptsMap) {
        scripts = scriptsMap;
    }

    public static void setImages(HashMap<String, String> imagesMap) {
        images = imagesMap;
    }
}
