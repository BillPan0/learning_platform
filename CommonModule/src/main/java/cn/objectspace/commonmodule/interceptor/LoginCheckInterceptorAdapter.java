package cn.objectspace.commonmodule.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author bali
 * @Date 2023/07/02
 * 登录状态检查
 * 1.配置到拦截器要拦截哪些请求
 * 2.把这些配置放在容器中
 * 实现HandlerInterceptor接口
 */
@Slf4j
@Component
public class LoginCheckInterceptorAdapter implements HandlerInterceptor {

    /**
     * 目标方法执行前
     * @param request 请求报文相关信息
     * @param response 应答报文相关信息，返回false时Servlet假设response已由当前拦截器处理完善
     * @param handler 对经过反射获取到的目标方法的再封装
     * @return 拦截处理结果，返回false时直接进入response返回环节
     * @throws Exception 错误信息
     */
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, @NotNull javax.servlet.http.HttpServletResponse response, @NotNull Object handler) throws Exception {
        //获取进过拦截器的路径
        String requestURI = request.getRequestURI();
        log.info("执行报文拦截，请求路径：" + requestURI);

        //登录检查逻辑
        String token = request.getHeader("token");
        if(token != null)
            return true;
        else{
            response.sendRedirect("/api/error");
            return false;
        }
    }

    /**
     * 目标方法执行后
     * @param request 请求报文相关信息
     * @param response 应答报文相关信息，返回false时Servlet假设response已由当前拦截器处理完善
     * @param handler 对经过反射获取到的目标方法的再封装
     * @param ex 目标方法抛出的异常对象
     * @throws Exception 抛给上层的异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 页面渲染后
     * @param request 请求报文相关信息
     * @param response 应答报文相关信息，返回false时Servlet假设response已由当前拦截器处理完善
     * @param handler 对经过反射获取到的目标方法的再封装
     * @param modelAndView 封装了SpringMVC进行页面跳转的相关数据，已弃用
     * @throws Exception 抛给上层的异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
