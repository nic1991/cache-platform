package com.newegg.ec.base.dao.mysql;

import com.newegg.ec.base.model.Url;
import java.util.List;

/**
 * Created by jn50 on 2017/2/21.
 */
public interface IUrlDao {

    List<Url> getUrlList();

    List<Url> getUserUrls(String userId);

    List<Url> getFilterUrlList(String searchStr);

    boolean addUrl(Url url);

    boolean updateUrl(Url url);

    boolean deleteUrl(Url url);
}
