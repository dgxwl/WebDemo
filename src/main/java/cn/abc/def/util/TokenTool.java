package cn.abc.def.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * Token机制工具类
 * 参考https://www.cnblogs.com/fengli9998/p/9251631.html
 */
public class TokenTool {

    public static final String TOKEN_SUFFIX = "tk_";
	
	private TokenTool() {}

	/**
	 * 生成token
	 * @return
	 */
	public static String makeToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(token.getBytes());
			Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(md5).replaceAll("[=+/]", "");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成token放入redis
	 * @param username
	 */
	public static String createToken(String username) {
		String token = makeToken();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 30);  //token有效时间30min

		RedisUtil.set(TOKEN_SUFFIX + token, username);
		RedisUtil.expireAt(TOKEN_SUFFIX + token, calendar.getTime());

		return token;
	}

	/**
	 * 移除token
	 * @param request
	 */
	public static void removeToken(HttpServletRequest request) {
        String token = request.getParameter("token");
		RedisUtil.delete(TOKEN_SUFFIX + token);
	}

	/**
	 * 判断请求参数中的token是否和session中一致
	 * @param request
	 * @return
	 */
	public static boolean checkToken(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		String username = RedisUtil.get(TOKEN_SUFFIX + token);
		if (StringUtils.isEmpty(username)) {
			return false;
		}

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 30);  //token有效时间延长30min
		RedisUtil.expireAt(TOKEN_SUFFIX + token, calendar.getTime());

		return true;
	}

	public static boolean checkToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		String username = RedisUtil.get(TOKEN_SUFFIX + token);
		if (StringUtils.isEmpty(username)) {
			return false;
		}

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 30);  //token有效时间延长30min
		RedisUtil.expireAt(TOKEN_SUFFIX + token, calendar.getTime());

		return true;
	}
}
