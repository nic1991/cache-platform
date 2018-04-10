package com.newegg.ec.base.service.impl;

import com.newegg.ec.base.dao.mysql.IUrlDao;
import com.newegg.ec.base.model.Url;
import com.newegg.ec.base.service.IUrlService;
import com.newegg.ec.base.util.UUIDUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jn50 on 2017/2/21.
 */
@Service
@Transactional
public class UrlService implements IUrlService{


    @Autowired
    private IUrlDao urlDao;

    @Override
    public JSONArray getUrlList(String searchStr) {
        if(searchStr!=null && !"".equals(searchStr)){
            return JSONArray.fromObject(urlDao.getFilterUrlList(searchStr));
        }else {
            return JSONArray.fromObject(urlDao.getUrlList());
        }
    }

    @Override
    public boolean addOrUpdateUrl(Url url) {
        if(url!=null && url.getId()!=null && !"".equals(url.getId())){
            return urlDao.updateUrl(url);
        }else{
            url.setId(UUIDUtil.getUUID());
            return urlDao.addUrl(url);
        }
    }

    @Override
    public boolean deleteUrl(Url url) {
        if(url!=null && url.getId()!=null) {
            return urlDao.deleteUrl(url);
        }else{
            return false;
        }
    }
}
