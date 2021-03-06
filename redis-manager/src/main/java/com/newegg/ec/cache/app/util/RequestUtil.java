package com.newegg.ec.cache.app.util;

import com.newegg.ec.cache.app.model.Common;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.core.logger.CommonLogger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gl49 on 2018/5/8.
 */
public class RequestUtil {
    public static final CommonLogger logger = new CommonLogger( RequestUtil.class );
    public static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    private RequestUtil(){
        // ignore
    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if( null != request ){
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        return request;
    }

    /**
     * init for restful request
     * @param
     */
    public static void setObject(String name, Object object) {
        HttpServletRequest request = getRequest();
        request.setAttribute(name, object);
    }

    public static Object getObject(String name){
        HttpServletRequest request = getRequest();
        Object object = request.getAttribute(name);
        return object;
    }

    public static void setUser(User user){
        setObject(Common.SESSION_USER_KEY, user);
    }

    public static User getUser(){
        return (User) getObject( Common.SESSION_USER_KEY );
    }
}
