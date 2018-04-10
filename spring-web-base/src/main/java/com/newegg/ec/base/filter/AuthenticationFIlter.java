package com.newegg.ec.base.filter;

import com.newegg.ec.base.util.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jn50 on 2016/4/13.
 */
public class AuthenticationFIlter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----filter init----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 缓存设置
         */
        ((HttpServletResponse)servletResponse).setHeader("Pragma", "no-cache");
        ((HttpServletResponse)servletResponse).setDateHeader("Expires", 0);
        ((HttpServletResponse)servletResponse).addHeader("Cache-Control", "no-cache");//浏览器和缓存服务器都不应该缓存页面信息
        ((HttpServletResponse)servletResponse).addHeader("Cache-Control", "no-store");//请求和响应的信息都不应该被存储在对方的磁盘系统中；
        ((HttpServletResponse)servletResponse).addHeader("Cache-Control", "must-revalidate");
        /**
         * 登陆认证
         */
        String url = ((HttpServletRequest) servletRequest).getRequestURI();
        if(url.endsWith(".html")) {
            ((HttpServletResponse) servletResponse).sendRedirect(Constant.BASEPATH + Constant.PAGEPATH + "/session_timeout");
        }else if(url.startsWith(Constant.BASEPATH+Constant.RESTPATH+"/user/login") || url.startsWith(Constant.BASEPATH+Constant.RESTPATH+"/user/logout") ||  url.startsWith(Constant.BASEPATH+Constant.RESTPATH+"/user/loginAlert") || url.equals(Constant.BASEPATH+Constant.PAGEPATH+"/index") || url.startsWith(Constant.BASEPATH + Constant.RESTPATH + "/user/neweggLogin") || url.equals(Constant.BASEPATH+Constant.PAGEPATH+"/session_timeout") || url.equals(Constant.BASEPATH+Constant.PAGEPATH+"/about")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
                String name = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("name");
                String password = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("password");
                if (name != null && password != null) {
                    Map<String,Boolean> myPath = (Map<String,Boolean>) ((HttpServletRequest) servletRequest).getSession().getAttribute("myPath");
                    if(checkPermission(url,myPath)){
                        filterChain.doFilter(servletRequest, servletResponse);
                    }else{
                        ((HttpServletResponse) servletResponse).sendRedirect(Constant.BASEPATH+Constant.RESTPATH+"/user/permissionAlert");
                    }
                } else {
                    ((HttpServletResponse) servletResponse).sendRedirect(Constant.BASEPATH+Constant.RESTPATH+"/user/logout");
                }
        }
    }

    public boolean checkPermission(String url, Map<String,Boolean> myPath){
        for(Map.Entry<String, Boolean> entry:myPath.entrySet()){
            if(url.contains(entry.getKey())){
                if(entry.getValue()==false){
                   return false;
                }
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        System.out.println("----filter destroy----");
    }
}
