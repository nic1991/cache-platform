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

    public INodeOperate factoryOperate(PluginType pluginType, int userId){
        INodeOperate nodeOperate = null;
        switch ( pluginType ){
            case machine:
                nodeOperate = new MachineManager( userId );
                break;
            case docker:
                nodeOperate = new DockerManager( userId );
                break;
            case humpback:
                nodeOperate = new HumpbackManager( userId );
                break;
        }
        return nodeOperate;
    }

    public INodeRequest factoryRequest(PluginType pluginType, int userId){
        INodeRequest nodeRequest = null;
        switch ( pluginType ){
            case machine:
                nodeRequest = new MachineManager( userId );
                break;
            case docker:
                nodeRequest = new DockerManager( userId );
                break;
            case humpback:
                nodeRequest = new HumpbackManager( userId );
                break;
        }
        return nodeRequest;
    }
}
