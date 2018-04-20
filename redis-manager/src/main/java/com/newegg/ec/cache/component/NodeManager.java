package com.newegg.ec.cache.component;

import com.newegg.ec.cache.plugin.manager.INodeOperate;
import com.newegg.ec.cache.plugin.manager.INodeRequest;
import com.newegg.ec.cache.plugin.model.PluginType;

/**
 * Created by lzz on 2018/4/20.
 */
public class NodeManager {
    public INodeOperate factoryOperate(PluginType pluginType){
        switch ( pluginType ){
            case machine:
                break;
            case docker:
                break;
            case humback:
                break;
        }
        return null;
    }

    public INodeRequest factoryRequest(PluginType pluginType){
        switch ( pluginType ){
            case machine:
                break;
            case docker:
                break;
            case humback:
                break;
        }
        return null;
    }
}
