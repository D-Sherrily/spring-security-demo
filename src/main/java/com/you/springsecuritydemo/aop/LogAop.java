package com.you.springsecuritydemo.aop;

import com.baomidou.mybatisplus.annotations.TableField;
import com.you.springsecuritydemo.annotation.Log;
import com.you.springsecuritydemo.domain.dto.SysLogDetailDto;
import com.you.springsecuritydemo.domain.entity.LoginUser;
import com.you.springsecuritydemo.domain.pojo.SysLog;
import com.you.springsecuritydemo.domain.pojo.SysLogDetail;
import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName: LogAop
 * @Description: 操作日志的aop  切面日志配置
 * @author: D
 * @create: 2020-07-22 18:44
 **/
@Aspect
@Component
@Slf4j
public class LogAop {

    @Resource
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.you.springsecuritydemo.annotation.Log)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        Object proceed = null;
        try {
            proceed = point.proceed();
            MethodSignature signature = (MethodSignature)point.getSignature();
            Method method = signature.getMethod();

            SysLog sysLog = new SysLog();
            //log注解上的描述
            String userAction = method.getAnnotation(Log.class).value();

            //获取用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser)authentication.getPrincipal();

            sysLog.setOperation(userAction);
            sysLog.setUserId(loginUser.getId());
            SysLogDetail sysLogDetail = new SysLogDetail();



            //获取session
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            List<SysLogDetailDto> logDetailDtoList = (List<SysLogDetailDto>)attributes.getRequest().getSession().getAttribute("logDetailDtoList");
            log.info(logDetailDtoList.toString());
            for (SysLogDetailDto sysLogDetailDto :logDetailDtoList){
                BeanUtils.copyProperties(sysLogDetailDto,sysLogDetail);
                String column = sysLogDetailDto.getColumn();
                String fieldName = getFieldAnnotation(column, User.class);
                sysLogDetail.setColumnName(fieldName);
                sysLogDetail.setOperationAction(sysLog.getOperation());
            }

            sysLogService.insertLog(sysLog,sysLogDetail);
            //获取类名
            //String className = point.getTarget().getClass().getName();
            //log.info("className:"+className);
            //获取方法名
            //String methodName = signature.getName();
            //log.info("methodName:"+methodName);
            //请求的方法的参数值
            //String args = Arrays.toString(point.getArgs());
            //log.info("args:"+args);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    //通过属性名 获取字段的的名字
    public <T> String getFieldAnnotation(String fieldName, Class<T> tClass){
        Field field;
        String value;

        try {
            field = tClass.getDeclaredField(fieldName);
            value = field.getAnnotation(TableField.class).value();
            if (StringUtils.isBlank(value)){
                return "";
            }
            return value;
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException");
            e.printStackTrace();
        }
        return "";
    }

}
