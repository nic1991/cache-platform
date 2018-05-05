package com.newegg.ec.cache.app.component;

import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.PluginType;
import com.newegg.ec.cache.plugin.docker.DockerManager;
import com.newegg.ec.cache.plugin.humpback.HumpbackManager;
import com.newegg.ec.cache.plugin.machine.MachineManager;
import org.springframework.stereotype.Component;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class NodeManager {
    public NodeManager(){

    }

    public INodeOperate factoryOperate(PluginType pluginType){
        switch ( pluginType ){
            case machine:
                break;
            case docker:
                break;
            case humpback:
                break;
        }
        return null;
    }

    public INodeRequest factoryRequest(PluginType pluginType){
        INodeRequest nodeRequest = null;
        switch ( pluginType ){
            case machine:
                nodeRequest = new MachineManager();
                break;
            case docker:
                nodeRequest = new DockerManager();
                break;
            case humpback:
                nodeRequest = new HumpbackManager();
                break;
        }
        return nodeRequest;
    }
}
