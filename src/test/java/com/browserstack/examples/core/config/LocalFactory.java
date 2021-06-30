package com.browserstack.examples.core.config;

import com.browserstack.local.Local;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LocalFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFactory.class);

    private static LocalFactory instance;

    private final Local local = new Local();
    private final String localIdentifier = RandomStringUtils.randomAlphabetic(8);

    private LocalFactory(Map<String, String> args) {
        try {
            args.put("localIdentifier", localIdentifier);
            local.start(args);
            LOGGER.info("Started BrowserStack Local with identifier {}.", localIdentifier);
        } catch (Exception e) {
            LOGGER.error("Initialization BrowserStack Local with identifier {} failed.", localIdentifier);
        }
    }

    public static void createInstance(Map<String, String> args) {
        if (instance == null) {
            synchronized (LocalFactory.class) {
                if (instance == null) {
                    instance = new LocalFactory(args);
                    Runtime.getRuntime().addShutdownHook(new Closer(instance.local));
                }
            }
        }
    }

    public static LocalFactory getInstance() {
        return instance;
    }

    public String getLocalIdentifier() {
        return instance.localIdentifier;
    }

    private static class Closer extends Thread {
        private final Local LOCAL;

        public Closer(Local local) {
            this.LOCAL = local;
        }

        @Override
        public void run() {
            try {
                if (LOCAL.isRunning()) {
                    LOCAL.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
