package com.css.im.log.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Aspect
public class LogAspect {

    Logger log = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.css.im.log.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.css.im.log.LogDebug)")
    public void logDebugPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Signature sig = joinPoint.getSignature();
        if(sig instanceof MethodSignature) {
            Object target = joinPoint.getTarget();
            Method currentMethod = target.getClass().getMethod(sig.getName(), ((MethodSignature)sig).getParameterTypes());
            log.debug(String.format("方法%s用时%s：", currentMethod.getDeclaringClass()+"#"+currentMethod.getName(), System.currentTimeMillis() - currentTime.get()));
        }
        currentTime.remove();
        return result;
    }

    @Around("logDebugPointcut()")
    public Object logDebugAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Signature sig = joinPoint.getSignature();
        if(sig instanceof MethodSignature) {
            Object target = joinPoint.getTarget();
            Method currentMethod = target.getClass().getMethod(sig.getName(), ((MethodSignature)sig).getParameterTypes());
            log.debug(String.format("方法%s用时%s,响应结果：%s", currentMethod.getDeclaringClass()+"#"+currentMethod.getName(),
                    System.currentTimeMillis() - currentTime.get(),new Gson().toJson(result)));
        }
        currentTime.remove();
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            Signature sig = joinPoint.getSignature();
            if (sig instanceof MethodSignature) {
                Object target = joinPoint.getTarget();
                Method currentMethod = target.getClass().getMethod(sig.getName(), ((MethodSignature) sig).getParameterTypes());
                log.debug(String.format("方法%s用时%s,异常：%s", currentMethod.getDeclaringClass() + "#" + currentMethod.getName(),
                        System.currentTimeMillis() - currentTime.get(), e.getMessage()),e);
            }
            currentTime.remove();
        }catch (Exception e1){

        }
    }

}
