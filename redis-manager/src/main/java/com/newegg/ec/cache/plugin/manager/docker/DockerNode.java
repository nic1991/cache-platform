package com.newegg.ec.cache.plugin.manager.docker;

import com.newegg.ec.cache.plugin.manager.INodeOperate;
import com.newegg.ec.cache.plugin.manager.INodeRequest;
import com.newegg.ec.cache.plugin.model.*;

/**
 * Created by lzz on 2018/4/20.
 */
public class DockerNode implements INodeOperate,INodeRequest {

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
    public String showInstall() {
        return null;
    }

    @Override
    public String showManager() {
        return null;
    }
}
