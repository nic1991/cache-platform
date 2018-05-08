package com.newegg.ec.cache.plugin.humpback;

import com.google.common.collect.Lists;
import com.newegg.ec.cache.app.util.HttpUtil;
import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class HumpbackManager implements INodeOperate,INodeRequest {
    static ExecutorService executorService = Executors.newFixedThreadPool(100);
    private int userId;
    private static String humpbackImage;
    private static String humpbackApiFormat;

    public HumpbackManager(){

    }
    public HumpbackManager(int userId){
        this.userId = userId;
    }


    @Override
    public boolean pullImage(List<String> ipList, String imageUrl) {
        List<Future<Boolean>> futureList = new ArrayList<>();
        for(String ip : ipList){
            Future<Boolean> future = executorService.submit( new PullImageTask(ip, imageUrl) );
            futureList.add( future );
        }
        for(Future<Boolean> future : futureList){
            try {
                future.get();
            } catch (Exception e) {

            }
        }
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

    public String getApiAddress(String ip){
        return String.format( humpbackApiFormat,  ip);
    }

    class PullImageTask implements Callable<Boolean> {
        private String image;
        private String ip;
        public PullImageTask(String ip, String image){
            this.ip = ip;
            this.image = image;
        }

        @Override
        public Boolean call() throws Exception {
            boolean res = true;
            try {
                JSONObject reqObject = new JSONObject();
                reqObject.put("Image", this.image);
                String url = getApiAddress(ip);
                System.out.println( url + reqObject );
                HttpUtil.jsonPost(url, reqObject);
            }catch (Exception e){
                res = false;
            }
            return res;
        }
    }

    @Value("${cache.humpback.image}")
    public void setHumpbackImage(String humpbackImage) {
        HumpbackManager.humpbackImage = humpbackImage;
    }
    @Value("${cache.humpback.api.format}")
    public void setHumpbackApiFormat(String humpbackApiFormat) {
        HumpbackManager.humpbackApiFormat = humpbackApiFormat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
