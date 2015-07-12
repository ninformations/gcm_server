package com.grokkingandroid.sampleapp;

import com.grokkingandroid.sampleapp.samples.gcm.ccs.server.CcsClient;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, SmackException {

        System.out.println( "Hello World!" );
        final String projectId = args[0];
        final String password = args[1];
        final String toRegId = args[2];

        CcsClient ccsClient = CcsClient.prepareClient(projectId, password, true);

        try {
            ccsClient.connect();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

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
