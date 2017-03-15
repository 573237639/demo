package com.fabao.ledger.modules.sys.intercepts;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fabao.ledger.common.utils.ConstantUtils;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.sys.annotations.Log;
import com.fabao.ledger.modules.sys.entity.SysLog;
import com.fabao.ledger.modules.sys.service.SysLogManager;
import com.fabaoframework.modules.springextend.SpringContextHolder;
import com.fabaoframework.modules.utils.IUser;

/**
 * 记录操作日志
 * 
 * @author fangzuo
 *
 */
public class OpeartorLogInterceptor implements HandlerInterceptor {

	private SysLogManager logManager = SpringContextHolder.getBean("sysLogManager");
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Log log = method.getAnnotation(Log.class);
			if (log != null&&log.isVlidate()) {
				String param = WebUtils.geAllParamters(request);
				String contextpath = request.getSession().getServletContext().getContextPath();
				String path=request.getRequestURI().replace(contextpath, "");
				IUser sessionUser=(IUser)request.getSession().getAttribute(ConstantUtils.USER_SESSION);
				if(sessionUser==null){
					return true;
				}
				SysLog sysLog=new SysLog();
				sysLog.setReqParam(param);
				sysLog.setAdminName(sessionUser.getUsername());
				sysLog.setActName(path);
				sysLog.setGmtCreated(new Date());
				try {
					logManager.save(sysLog);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
