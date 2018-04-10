package com.newegg.ec.base.service;

import com.newegg.ec.base.model.Url;
import net.sf.json.JSONArray;

/**
 * Created by jn50 on 2017/2/21.
 */
public interface IUrlService {

    JSONArray getUrlList(String searchStr);

    boolean addOrUpdateUrl(Url url);

    boolean deleteUrl(Url url);
}
