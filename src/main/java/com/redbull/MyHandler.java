package com.redbull;

import org.apache.solr.core.PluginInfo;
import org.apache.solr.handler.component.SearchHandler;

/**
 * @author Thomas Kurz (thomas.kurz@redlink.co)
 * @since 09.03.17.
 */
public class MyHandler extends SearchHandler {

    @Override
    public String getVersion() {
        return "1.0";
    }
}
