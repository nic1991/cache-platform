package com.newegg.ec.cache.plugin;

import com.newegg.ec.cache.plugin.basemodel.*;

import java.util.List;

/**
 * Created by lzz on 2018/4/20.
 */
public interface INodeOperate{
    boolean pullImage(List<String> ipList, String imageUrl);
    boolean install(InstallParam installParam);
    boolean start(StartParam startParam);
    boolean stop(StopParam stopParam);
    boolean restart(RestartParam restartParam);
    boolean remove(RemovePram removePram);
    List<String> getImageList();

    List<Node> getNodeList(int clusterId);
}
