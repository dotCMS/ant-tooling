package com.dotmarketing.plugin.test;

import com.dotmarketing.plugin.util.PluginFileMerger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * MergePropertiesTest class
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MergePropertiesTest {

    private static File file;

    @BeforeClass
    public static void init() throws IOException {
        ClassLoader classLoader = MergePropertiesTest.class.getClassLoader();
        URL url = classLoader.getResource("dotmarketing-config.properties");
        if (url != null) {
            file = new File(url.getFile());
        } else {
            throw new IOException("Config file 'dotmarketing-config.properties' not found");
        }
    }

    /**
     * Merges the dotmarketing-config.properties file with the new properties
     *
     * @param commentTag        The comment tag
     * @param propertiesToMerge The properties to be merged into the file
     */
    private void mergeFile(String commentTag, String propertiesToMerge) throws IOException {
        PluginFileMerger pfm = new PluginFileMerger();

        pfm.merge(
            file,
            "## BEGIN PLUGINS",
            "## END PLUGINS",
            "## BEGIN PLUGIN:" + commentTag,
            "## END PLUGIN:" + commentTag,
            propertiesToMerge, "#",
            "## OVERRIDE:" + commentTag);
    }

    /**
     * Gets the new properties after the file was merged
     *
     * @return Properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(file.getAbsoluteFile());
            properties.load(input);

            Assert.assertNotNull(properties);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    /**
     * Test with spaces at the end of the property
     */
    @Test
    public void testMerge01() {
        try {
            String commentTag = "Merge Test 01";
            String propertiesToMerge = "cache.default.chain      	    		= sampleData1\nSHOW_VELOCITYFILES=true\n";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 01. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals sampleData1", key), prop.get(key).equals("sampleData1"));

            key = "SHOW_VELOCITYFILES";
            System.out.println(String.format("Test 01. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals true", key), prop.get(key).equals("true"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test with a single space after the property
     */
    @Test
    public void testMerge02() {
        try {
            String commentTag = "Merge Test 02";
            String propertiesToMerge = "cache.default.chain = sampleData2";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 02. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals sampleData2", key), prop.get(key).equals("sampleData2"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test with no spaces between the property and the value
     */
    @Test
    public void testMerge03() {
        try {
            String commentTag = "Merge Test 03";
            String propertiesToMerge = "cache.default.chain=sampleData3";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 03. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals sampleData3", key), prop.get(key).equals("sampleData3"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test with spaces at the beginning of the property
     */
    @Test
    public void testMerge04() {
        try {
            String commentTag = "Merge Test 04";
            String propertiesToMerge = "             cache.default.chain=sampleData4";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 04. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals sampleData4", key), prop.get(key).equals("sampleData4"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test with spaces at the beginning and at the end of the property
     */
    @Test
    public void testMerge05() {
        try {
            String commentTag = "Merge Test 05";
            String propertiesToMerge = "             cache.default.chain             =sampleData5";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 04. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' equals sampleData5", key), prop.get(key).equals("sampleData5"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test with space in the property - should display an error
     */
    @Test
    public void testMerge06() {
        try {
            String commentTag = "Merge Test 06";
            String propertiesToMerge = "cache. default.chain=sampleData6";

            mergeFile(commentTag, propertiesToMerge);
            Properties prop = getProperties();

            String key = "cache.default.chain";
            System.out.println(String.format("Test 06. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
            assertThat(String.format("Property '%s' is not sampleData6", key), !prop.get(key).equals("sampleData6"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
