package com.dotmarketing.plugin.ant;

import com.dotmarketing.plugin.util.PluginFileMerger;
import com.dotmarketing.plugin.util.PluginRoot;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.net.URL;

/**
 * @author Andres Olarte
 * @since 1.6.5c
 */
public class UndeployTask extends Task {

    private String distributionPath;
    private String dotcmsHome;
    private String plugins;

    @Override
    public void execute () throws BuildException {

        new PluginRoot( distributionPath, dotcmsHome, plugins ).undeploy();//Plugin that will allow any file to be overridden or added.
        new PluginFileMerger().undeploy( dotcmsHome, plugins );
    }

    /**
     * Set the root of the distribution
     *
     * @param distributionPath The root of the distribution directory
     */
    public synchronized void setDistributionPath ( String distributionPath ) {
        this.distributionPath = distributionPath;
    }

    /**
     * Set the root of the web app (Servlet context)
     *
     * @param dotcmsHome The root of the web app
     */
    public synchronized void setDotcmsHome ( String dotcmsHome ) {
        this.dotcmsHome = dotcmsHome;
    }

    /**
     * Set the directory where the plugins live
     *
     * @param plugins The directory where the plugins live
     */
    public synchronized void setPlugins ( String plugins ) {
        this.plugins = plugins;
    }

}