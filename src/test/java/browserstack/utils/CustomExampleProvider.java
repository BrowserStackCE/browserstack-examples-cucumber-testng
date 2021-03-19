package browserstack.utils;

import java.util.Map;

import org.testng.annotations.DataProvider;

import com.google.common.collect.Maps;

public class CustomExampleProvider{

    @DataProvider(name="dynamic-examples")
    public static Object[][] dataProviderForBDD(){
        //generate and return data. 
        //This is just example with hard-coded values and you can generate and return data as per need.
        Map<Object, Object> ex1 = Maps.newHashMap();
        ex1.put("username", "fav_user");
        ex1.put("password", "testingisfun99");

        Map<Object, Object> ex2 = Maps.newHashMap();
        ex1.put("username", "'image_not_loading_user' ");
        ex1.put("password", "testingisfun99");


        return new Object[][] {{ex1},{ex2}} ;
    }
}