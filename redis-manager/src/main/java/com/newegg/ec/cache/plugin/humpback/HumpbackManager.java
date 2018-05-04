package com.newegg.ec.cache.plugin.humpback;

import com.google.common.collect.Lists;
import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class HumpbackManager implements INodeOperate,INodeRequest {
    @Value("${cache.humpback.image}")
    private String humpbackImage;

    @Override
    public boolean pullImage(String imageUrl) {
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
        return Lists.newArrayList(humpbackImage.split(","));
    }

    @Override
    public String showInstall() {
        return "plugin/humpback/humpbackCreateCluster";
    }

    @Override
    public String showManager() {
        return "plugin/humpback/humpbackNodeManager";
    }
}
