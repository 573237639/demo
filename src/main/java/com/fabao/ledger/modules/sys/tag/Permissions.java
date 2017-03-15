package com.fabao.ledger.modules.sys.tag;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.fabao.ledger.common.utils.ConstantUtils;


/**
 * @Description: 权限验证标签  功能菜单等....
 * 				--tld   	 /ledger/src/main/webapp/WEB-INF/tlds/permission.tld
 * 				--use case   <permission:hasPermission action="权限">是否展示的主体</permission:hasPermission>
 * @author FB - LISI
 * @date 2016年9月29日 上午11:11:46
 */
public class Permissions extends TagSupport{

	private static final long serialVersionUID = 2081889592874469928L;
	
	private String action;
	
	public void setAction(String action) {
		this.action = action;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		if(StringUtils.isNotBlank(action)){
			Object obj = this.pageContext.getSession().getAttribute(ConstantUtils.ACT_SESSION);
			if(obj == null)
				return SKIP_BODY;
			Set<String> actList = (Set<String>) obj;
			if(actList.contains(action))
				return EVAL_BODY_INCLUDE;
			else
				return SKIP_BODY;
		}
		return super.doStartTag();
	}

}
