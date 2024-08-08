package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solacesystems.jcsmp.*;
import java.io.File;
import java.io.IOException;

public class SolaceClientTool {

    public static void main(String[] args)  {
        System.out.println("======================== Starting SolaceClientTool =======================");
        if (args.length != 1) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Config config = null;
        try {
            config = objectMapper.readValue(new File(args[0]), Config.class);
        } catch (IOException e) {
            System.out.printf("Cannot parse configuration file, test failed");
            return;
        }

        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, config.getHost());
        properties.setProperty(JCSMPProperties.VPN_NAME, config.getVpnName());
        properties.setProperty(JCSMPProperties.USERNAME, config.getUsername());
        properties.setProperty(JCSMPProperties.PASSWORD, config.getPassword());
        try {
            final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);
            session.connect();

            sendMessage(session, config.getQueueName(), config.getRequestedMessage());
            receiveMessage(session, config.getQueueName(), config.getTimeout(), config.getExpectedResponse());

            session.closeSession();
        } catch (JCSMPException e) {
            System.out.printf("Cannot create session, test failed");
        }
    }

    private static void sendMessage(JCSMPSession session, String queueName, String message) {
        System.out.println("Message sent to queue: " + queueName);
    }

    private static void receiveMessage(JCSMPSession session, String queueName, int timeout, String expectedResponse) {
        // logic to test response
        if (expectedResponse == null) {
            System.out.printf("Response not expected, test failed");
            return;
        }
        System.out.printf("Test passed");

    }

}