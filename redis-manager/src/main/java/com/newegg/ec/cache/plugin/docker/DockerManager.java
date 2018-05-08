package com.newegg.ec.cache.plugin.docker;

import com.google.common.collect.Lists;
import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class DockerManager implements INodeOperate,INodeRequest {
    private int userId;
    private String dockerImage;

    public DockerManager(){
        //ignore
    }
    public DockerManager(int userId){
        this.userId = userId;
    }

    @Override
    public boolean pullImage(List<String> ipList, String imageUrl) {
        return false;
    }

    @Override
    public boolean install(InstallParam installParam) {
        return false;
    }

    @Override
    public boolean start(StartParam startParam) {
        return false;
    }

    @Override
    public boolean stop(StopParam stopParam) {
        return false;
    }

    @Override
    public boolean restart(RestartParam restartParam) {
        return false;
    }

    @Override
    public boolean remove(RemovePram removePram) {
        return false;
    }

    @Override
    public List<String> getImageList() {
        return Lists.newArrayList(dockerImage.split(","));
    }

    @Override
    public List<Node> getNodeList(int clusterId) {
        return null;
    }

    @Override
    public String showInstall() {
        return "plugin/docker/dockerCreateCluster";
    }

    @Override
    public String showManager() {
        return "plugin/docker/dockerNodeManager";
    }
}
