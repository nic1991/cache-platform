package com.newegg.ec.cache.plugin.manager;

import com.newegg.ec.cache.plugin.model.*;

/**
 * Created by lzz on 2018/4/20.
 */
public interface INodeOperate {
    boolean pullImage(String imageUrl);
    boolean install(InstallParam installParam);
    boolean start(StartParam startParam);
    boolean stop(StopParam stopParam);
    boolean restart(RestartParam restartParam);
    boolean remove(RemovePram removePram);
}
