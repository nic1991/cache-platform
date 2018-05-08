package com.newegg.ec.cache.plugin.humpback;

import com.newegg.ec.cache.app.model.Cluster;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lzz on 2018/4/20.
 */
@Repository
public interface IHumpbackNodeDao {
    List<HumpbackNode> getHumbackNodeList(String userGroup);

    HumpbackNode getHumpbackNode(int id);

    int removeHumbackNode(int id );

    int addHumbackNode(HumpbackNode humpbackNode);
}
