package com.dotmarketing.plugin.util;

import com.dotcms.repackage.org.apache.logging.log4j.Level;
import com.dotcms.repackage.org.apache.logging.log4j.LogManager;
import com.dotcms.repackage.org.apache.logging.log4j.Logger;
import com.dotcms.repackage.org.apache.logging.log4j.core.Appender;
import com.dotcms.repackage.org.apache.logging.log4j.core.Layout;
import com.dotcms.repackage.org.apache.logging.log4j.core.LoggerContext;
import com.dotcms.repackage.org.apache.logging.log4j.core.appender.ConsoleAppender;
import com.dotcms.repackage.org.apache.logging.log4j.core.appender.FileAppender;
import com.dotcms.repackage.org.apache.logging.log4j.core.config.*;
import com.dotcms.repackage.org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import com.dotcms.repackage.org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @author Jonathan Gamba
 *         Date: 8/5/15
 */
public class Log4jUtil {

    /**
     * Creates a ConsoleAppender in order to add it to the root logger
     */
    public static void createAndAddConsoleAppender () {

        Logger logger = LogManager.getLogger(Log4jUtil.class);
        logger.info("++++++++++++++++++++++++++++++++");
        logger.info("++++++++++++++++++++++++++++++++");
        logger.info("++++++++++++++++++++++++++++++++");
        logger.info("++++++++++++++++++++++++++++++++");

        //TODO: Remove!!!
        //TODO: Remove!!!
        System.out.println("=======================");
        System.out.println("Entro - 0 !!!!");
        System.out.println("=======================");
        //TODO: Remove!!!
        //TODO: Remove!!!

        //Getting the current log4j appenders
        Logger rootLogger = LogManager.getRootLogger();
        //Getting all the appenders for this logger
        Map<String, Appender> appenderMap = ((com.dotcms.repackage.org.apache.logging.log4j.core.Logger) rootLogger).getAppenders();

        //Getting the log4j configuration
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext();
        Configuration configuration = loggerContext.getConfiguration();

        //TODO: Remove!!!
        //TODO: Remove!!!
        System.out.println("=======================");
        System.out.println("Entro - 1 !!!!");
        System.out.println("=======================");
        //TODO: Remove!!!
        //TODO: Remove!!!

        //Init log4j to see the messages in ant's output
        if ( !appenderMap.isEmpty() ) {

            //TODO: Remove!!!
            //TODO: Remove!!!
            System.out.println("=======================");
            System.out.println("Entro - 2 !!!!");
            System.out.println("=======================");
            //TODO: Remove!!!
            //TODO: Remove!!!

            //Create a simple layout for our appender
            Layout simpleLayout = PatternLayout.createLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN, configuration, null, null, true, false, null, null);

            //Create and add a console appender to the configuration
            ConsoleAppender consoleAppender = ConsoleAppender.createDefaultAppenderForLayout(simpleLayout);
            configuration.addAppender(consoleAppender);
        }

        System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy");
        LogManager.getLogger(Log4jUtil.class).error("**************************");
        LogManager.getLogger(Log4jUtil.class).info("**************************");
        LogManager.getLogger(Log4jUtil.class).warn("**************************");
        LogManager.getLogger(Log4jUtil.class).trace("**************************");
        LogManager.getLogger(Log4jUtil.class).debug("**************************");
        LogManager.getLogger(Log4jUtil.class).error("*********XXXXXXXXXX*****************");
        System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy");
    }

    public static void test () {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        Layout layout = PatternLayout.createLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN, config, null,null,false,false,null, null);
        Appender appender = FileAppender.createAppender("target/test.log", "false", "false", "File", "true",
                "false", "false", "4000", layout, null, "false", null, config);
        appender.start();
        config.addAppender(appender);
        AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
        AppenderRef[] refs = new AppenderRef[] {ref};
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.INFO, "org.apache.logging.log4j","true", refs, null, config, null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger("org.apache.logging.log4j", loggerConfig);
        ctx.updateLoggers();

        LogManager.getLogger(Log4jUtil.class).error("**************************");
        LogManager.getLogger(Log4jUtil.class).info("**************************");
        LogManager.getLogger(Log4jUtil.class).warn("**************************");
        LogManager.getLogger(Log4jUtil.class).trace("**************************");
        LogManager.getLogger(Log4jUtil.class).debug("**************************");
        LogManager.getLogger(Log4jUtil.class).error("**************************");
    }

    public static void test1 () {
        LogManager.getLogger().error("**************************");
        LogManager.getLogger(Log4jUtil.class).error("**************************");
        LogManager.getLogger(Log4jUtil.class).info("**************************");
        LogManager.getLogger().info("**************************");
        LogManager.getLogger(Log4jUtil.class).warn("**************************");
        LogManager.getLogger().warn("**************************");
        LogManager.getLogger(Log4jUtil.class).trace("**************************");
        LogManager.getLogger().trace("**************************");
        LogManager.getLogger(Log4jUtil.class).debug("**************************");
        LogManager.getLogger().debug("**************************");
        LogManager.getLogger(Log4jUtil.class).error("**************************");
        LogManager.getLogger().error("**************************");

        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(true);
        Configuration configuration = loggerContext.getConfiguration();
        System.out.println(loggerContext.getConfigLocation());
        if ( loggerContext.getConfigLocation() != null ) {
            System.out.println(loggerContext.getConfigLocation().toString());
        }
    }

    /**
     * Initialises/reconfigures log4j based on a given log4j configuration file
     */
    public static void initialize () {

//        String resource = "log4j2.xml";
        String resource = "/Users/jonathan/Dropbox/Projects/dotCMS/repository/git/ant-tooling/src/main/resources/log4j2.xml";

        try {

//            URL streamURL = Log4jUtil.class.getClassLoader().getResource(resource);
//            URI resourceURI = streamURL.toURI();
//            InputStream stream = Log4jUtil.class.getClassLoader().getResourceAsStream(resource);

            URI resourceURI = URI.create(resource);
            InputStream stream = new FileInputStream(resource);

            if ( stream == null ) {
                //TODO: Remove!!!
                //TODO: Remove!!!
                System.out.println("------------------------");
                System.out.println("Stream is Null!!!!");
                System.out.println("------------------------");
                //TODO: Remove!!!
                //TODO: Remove!!!
            }

            if ( stream != null ) {

                //TODO: Remove!!!
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Test 1");
                System.out.println("------------------------");
                //TODO: Remove!!!

                ConfigurationFactory factory = ConfigurationFactory.getInstance();
                Configuration configuration = factory.getConfiguration("Default", resourceURI, Log4jUtil.class.getClassLoader());
                configuration.start();
                if ( configuration.getConfigurationSource() != null ) {
                    System.out.println("----> " + configuration.getConfigurationSource().getURL());
                }

                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZ");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                LogManager.getLogger(Log4jUtil.class).info("**************************");
                LogManager.getLogger(Log4jUtil.class).warn("**************************");
                LogManager.getLogger(Log4jUtil.class).trace("**************************");
                LogManager.getLogger(Log4jUtil.class).debug("**************************");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZ");

                //TODO: Remove!!!
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Test 2");
                System.out.println("------------------------");
                //TODO: Remove!!!

                ConfigurationSource source = new ConfigurationSource(stream);
                XmlConfiguration xmlConfig = new XmlConfiguration(source);
                //xmlConfig.setup();
                LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
                ctx.stop();
                ctx.start(xmlConfig);
                System.out.println("----> " + ctx.getConfigLocation());

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                LogManager.getLogger(Log4jUtil.class).info("**************************");
                LogManager.getLogger(Log4jUtil.class).warn("**************************");
                LogManager.getLogger(Log4jUtil.class).trace("**************************");
                LogManager.getLogger(Log4jUtil.class).debug("**************************");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");

                //TODO: Remove!!!
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Test 3");
                System.out.println("------------------------");
                //TODO: Remove!!!

                LoggerContext loggerContext = (LoggerContext) LogManager.getContext(true);
                loggerContext.setConfigLocation(resourceURI);
                loggerContext.reconfigure();
                System.out.println("----> " + loggerContext.getConfigLocation());

                System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                LogManager.getLogger(Log4jUtil.class).info("**************************");
                LogManager.getLogger(Log4jUtil.class).warn("**************************");
                LogManager.getLogger(Log4jUtil.class).trace("**************************");
                LogManager.getLogger(Log4jUtil.class).debug("**************************");
                LogManager.getLogger(Log4jUtil.class).error("**************************");
                System.out.println("yyyyyyyyyyyyyyyyyyyyyyyy");

            } else {
                LogManager.getLogger().error("Error initializing log for " + resource + " configuration file [Not found].");
            }
        } catch ( Exception e ) {
            LogManager.getLogger().error("Error initializing log for " + resource + " configuration file.", e);
        }

    }
}