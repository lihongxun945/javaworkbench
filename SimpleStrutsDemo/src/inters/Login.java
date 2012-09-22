package inters;

import java.io.Console;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class Login extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		ActionContext ac = ai.getInvocationContext();
		Map<String, Object> session = ac.getSession();
		String userName = (String) session.get("userName");
		if(userName != null && userName.length() > 0){
			System.out.println(userName + " login!");
			ac.put("userName", userName);
			ai.invoke();
		}else{
			ac.put("msg", "ÇëÏÈµÇÂ¼!");
			return "error";
		}
		return null;
	}

}
