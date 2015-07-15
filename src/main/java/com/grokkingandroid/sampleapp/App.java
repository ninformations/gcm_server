package com.grokkingandroid.sampleapp;

import com.grokkingandroid.sampleapp.samples.gcm.ccs.server.CcsClient;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    static Properties prop = new Properties();
    public static void main( String[] args ) throws IOException, SmackException {
        System.out.println(App.class.getClassLoader().getResource("").getPath());
        prop.load(App.class.getClassLoader().getResourceAsStream("application.properties"));

        System.out.println( "Hello World!" );
        final String projectId = prop.getProperty("projectId"); //args[0];
        final String password = prop.getProperty("password"); //args[1];
        final String toRegId = prop.getProperty("toRegId"); //args[2];

        CcsClient ccsClient = CcsClient.prepareClient(projectId, password, true);

        try {
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            ccsClient.connect();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        if(toRegId != null) {
            // Send a sample hello downstream message to a device.
            String messageId = ccsClient.getRandomMessageId();
            Map<String, String> payload = new HashMap<String, String>();
            payload.put("message", "Simple sample sessage");
            String collapseKey = "sample";
            Long timeToLive = 10000L;
            Boolean delayWhileIdle = true;
            ccsClient.send(CcsClient.createJsonMessage(toRegId, messageId, payload, collapseKey,
                    timeToLive, delayWhileIdle));
        }
    }
}
