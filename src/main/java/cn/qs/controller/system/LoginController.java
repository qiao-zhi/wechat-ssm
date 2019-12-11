package cn.qs.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.qs.bean.user.User;
import cn.qs.service.user.UserService;
import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.system.SystemUtils;

/**
 * 登陆
 * 
 * @author Administrator
 *
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	/**
	 * 跳转到登陆界面
	 * 
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		request.setAttribute("productName", SystemUtils.getProductName());
		return "login";
	}

	/**
	 * 处理登陆请求
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("doLogin")
	@ResponseBody
	public JSONResultUtil doLogin(String username, String password, HttpSession session) {
		User loginUser = userService.getUserByUserNameAndPassword(username, password);

		if (loginUser == null) {
			return JSONResultUtil.error("账号或者密码错误");
		}

		session.setAttribute("user", loginUser);
		return JSONResultUtil.ok();
	}

	/**
	 * 处理登陆请求(JSON数据)
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("doLoginJSON")
	@ResponseBody
	public JSONResultUtil<Object> doLoginJSON(@RequestBody User user, HttpSession session) {
		User loginUser = userService.getUserByUserNameAndPassword(user.getUsername(), user.getPassword());

		if (loginUser == null) {
			return JSONResultUtil.error("账号或者密码错误");
		}

		session.setAttribute("user", loginUser);

		String useStr = JSONObject.toJSONString(loginUser);
		return new JSONResultUtil<Object>(true, useStr, "登陆成功");
	}

}
