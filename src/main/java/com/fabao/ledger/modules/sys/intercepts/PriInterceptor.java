package com.fabao.ledger.modules.sys.intercepts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fabaoframework.modules.utils.SpringUtil;

public class PriInterceptor implements HandlerInterceptor {
	
	private static final Logger log = Logger.getLogger(PriInterceptor.class);
	
	@Autowired
	private SpringUtil springUtil;
	

	/**
	 * 在DispatcherServlet完全处理完请求后被调用.
	 * 
	 * 当有拦截器抛出异常时,或返回false时; 会从当前拦截器往回执行所有拦截器的afterCompletion()方法.
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		log.debug("==============顺序执行3:afterCompletion==================");
	}

	/**
	 * 在业务处理器处理请求完成后,生成视图前执行的动作.
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		log.debug("==============顺序执行2:postHandle==================");
	}

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		String contextpath = request.getSession().getServletContext().getContextPath();
		String uri = request.getRequestURI();
		String path = uri.substring(contextpath.length());
		
		if(path.equals("/login")){
			return true;
		}
		
		Object userObj = request.getSession().getAttribute("iuser");
		if(null==userObj){
			request.getSession().invalidate();
			springUtil.renderHtml("<script type='text/javascript'>top.location='" + contextpath  + "/login" + "'</script>");
			return false;
		}
		return true;
	}

}
