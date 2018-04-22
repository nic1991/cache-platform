package com.newegg.ec.cache.controller.advice;


import com.newegg.ec.cache.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by gl49 on 2017/9/9.
 */
@Aspect
@Component
public class LogAdvice {
    private static Logger logger = LoggerFactory.getLogger(LogAdvice.class);
    private static final String LOG_SEPARATOR = ",";
    /**
     * 用于记录 access log
     * @param proceedingJoinPoint
     * @return
     */
    @Around( "execution(* com.newegg.ec.cache.controller.*.*(..))" )
    public  Object arroundExecute( ProceedingJoinPoint proceedingJoinPoint ){
        Object value = null;
        try {
            String currentDate  = DateUtil.getCurrentDate();
            /* 客户端ip */
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String clientIp = request.getHeader("x-forwarded-for");
            if ( clientIp == null ){
                clientIp = request.getRemoteHost();
            }
            String path = request.getServletPath();
            HttpSession session = request.getSession();
            String userid = "userid001";
            String groups = "group001";
            /* 请求参数 */
            Object[] params = proceedingJoinPoint.getArgs();
            String param = "";
            if( null != params && params.length != 0 ){
                param = params[0].toString();
            }
            if( param.contains("session") ){
                param = "";
            }
            /* 计算调用时间 */
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String msgType;
            try {
                value = proceedingJoinPoint.proceed();
                msgType = "INFO";
            }catch (Exception e){
                msgType = "ERROR";
                logger.error( "", e );
            }
            String msg = currentDate + LOG_SEPARATOR + msgType + LOG_SEPARATOR + clientIp + LOG_SEPARATOR + userid + LOG_SEPARATOR + groups + LOG_SEPARATOR + path + LOG_SEPARATOR + param + LOG_SEPARATOR + stopWatch.getTotalTimeMillis();
            System.out.println( msg );
            logger.info( msg );
            stopWatch.stop();
        } catch (Throwable e) {
            logger.error( "", e );
        }
        return value;
    }
}
