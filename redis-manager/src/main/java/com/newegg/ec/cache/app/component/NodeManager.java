package com.newegg.ec.cache.app.component;

import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.PluginType;
import com.newegg.ec.cache.plugin.docker.DockerManager;
import com.newegg.ec.cache.plugin.humpback.HumpbackManager;
import com.newegg.ec.cache.plugin.machine.MachineManager;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class NodeManager {
    @Resource
    private HumpbackManager humpbackManager;
    @Resource
    private MachineManager machineManager;
    @Resource
    private DockerManager dockerManager;

    public NodeManager(){

    }

    public INodeOperate factoryOperate(PluginType pluginType){
        INodeOperate nodeOperate = null;
        switch ( pluginType ){
            case machine:
                nodeOperate = machineManager;
                break;
            case docker:
                nodeOperate = dockerManager;
                break;
            case humpback:
                nodeOperate = new HumpbackManager();
                break;
        }
        return nodeOperate;
    }

    public INodeRequest factoryRequest(PluginType pluginType){
        INodeRequest nodeRequest = null;
        switch ( pluginType ){
            case machine:
                nodeRequest = machineManager;
                break;
            case docker:
                nodeRequest = dockerManager;
                break;
            case humpback:
                nodeRequest = humpbackManager;
                break;
        }
        return nodeRequest;
    }
}
