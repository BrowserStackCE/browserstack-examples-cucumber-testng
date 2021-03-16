package browserstack.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Utility {

    private Utility(){}

    public static Map<String,String> getLocalOptions(JSONObject config) {
        Map<String, String> localOptions = new HashMap<String, String>();
        JSONObject localOptionsJson = (JSONObject) ((JSONObject) ((JSONObject) config.get("tests")).get("local")).get("local_binding_caps");
        for (Object o : localOptionsJson.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (localOptions.get(pair.getKey().toString()) == null) {
                localOptions.put(pair.getKey().toString(), pair.getValue().toString());
            }
        }
        return localOptions;
    }

    public static void setSessionStatus(WebDriver webDriver, String status, String reason) {
        JavascriptExecutor jse = (JavascriptExecutor)webDriver;
        jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}",status,reason));
    }

    public static JSONObject getCombinedCapability(Map<String, String> envCapabilities, JSONObject config, JSONObject caps) {
        JSONObject capabilities = new JSONObject();
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.put(pair.getKey().toString(), pair.getValue().toString());
        }
        Map<String, String> commonCapabilities = (Map<String, String>) caps.get("common_caps");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(capabilities.get(pair.getKey().toString()) == null){
                capabilities.put(pair.getKey().toString(), pair.getValue().toString());
            }
        }
        JSONObject singleConfig = new JSONObject();
        singleConfig.put("user",config.get("user"));
        singleConfig.put("key",config.get("key"));
        singleConfig.put("capabilities",capabilities);
        if(caps.containsKey("application_endpoint")) {
            singleConfig.put("application_endpoint",caps.get("application_endpoint"));
        } else {
            singleConfig.put("application_endpoint",config.get("application_endpoint"));
        }
        return singleConfig;
    }

}
