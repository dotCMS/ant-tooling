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
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MergePropertiesTest {

    private static File file;

    @BeforeClass
    public static void init() {
        ClassLoader classLoader = MergePropertiesTest.class.getClassLoader();
        file = new File(classLoader.getResource("dotmarketing-config.properties").getFile());
    }

    @Test
    public void testMerge01() {
        try {
            String dotmarketing = "cache.default.chain      	    		= sampleData1\nSHOW_VELOCITYFILES=true\n";

            String name = "Test Merge 01";
            PluginFileMerger pfm = new PluginFileMerger();

            pfm.merge(
                file,
                "## BEGIN PLUGINS",
                "## END PLUGINS",
                "## BEGIN PLUGIN:" + name,
                "## END PLUGIN:" + name,
                dotmarketing, "#",
                "## OVERRIDE:" + name);

            String key = "";
            Properties prop = new Properties();
            InputStream input = null;

            try {
                input = new FileInputStream(file.getAbsoluteFile());
                prop.load(input);

                Assert.assertNotNull(prop);

                key = "cache.default.chain";
                System.out.println(String.format("Test 01. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
                assertThat(String.format("Property '%s' equals sampleData1", key), prop.get(key).equals("sampleData1"));

                key = "SHOW_VELOCITYFILES";
                System.out.println(String.format("Test 01. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
                assertThat(String.format("Property '%s' equals true", key), prop.get(key).equals("true"));
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testMerge02() {
        try {
            String dotmarketing = "cache.default.chain = sampleData2";

            String name = "Test Merge 02";
            PluginFileMerger pfm = new PluginFileMerger();

            pfm.merge(
                file,
                "## BEGIN PLUGINS",
                "## END PLUGINS",
                "## BEGIN PLUGIN:" + name,
                "## END PLUGIN:" + name,
                dotmarketing, "#",
                "## OVERRIDE:" + name);

            String key = "";
            Properties prop = new Properties();
            InputStream input = null;

            try {
                input = new FileInputStream(file.getAbsoluteFile());
                prop.load(input);

                Assert.assertNotNull(prop);

                key = "cache.default.chain";
                System.out.println(String.format("Test 02. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
                assertThat(String.format("Property '%s' equals sampleData2", key), prop.get(key).equals("sampleData2"));
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testMerge03() {
        try {
            String dotmarketing = "cache.default.chain=sampleData3";

            String name = "Test Merge 03";
            PluginFileMerger pfm = new PluginFileMerger();

            pfm.merge(
                file,
                "## BEGIN PLUGINS",
                "## END PLUGINS",
                "## BEGIN PLUGIN:" + name,
                "## END PLUGIN:" + name,
                dotmarketing, "#",
                "## OVERRIDE:" + name);

            String key = "";
            Properties prop = new Properties();
            InputStream input = null;

            try {
                input = new FileInputStream(file.getAbsoluteFile());
                prop.load(input);

                Assert.assertNotNull(prop);

                key = "cache.default.chain";
                System.out.println(String.format("Test 03. Property: '%s', Value: '%s'", key, prop.getProperty(key)));
                assertThat(String.format("Property '%s' equals sampleData3", key), prop.get(key).equals("sampleData3"));
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
