package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import cn.abc.def.util.HttpUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 一些第三方接口的调用
 * @author Administrator
 *
 */
@RestController
public class ThridPartyController {

	/**
	 * 判断手机号码运营商和归属地
	 */
	@RequestMapping("/phone_loc")
	public ResponseResult phoneLocation(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return new ResponseResult(-1, "缺少电话号码参数");
		}
		try {
			String result = HttpUtil.get("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phone);
			System.out.println(result);
			
			//判断是否为移动号码
			if (!StringUtils.isEmpty(result)) {
				return new ResponseResult(1, result.contains("移动") ? "移动号码" : "非移动号码");
			} else {
				return new ResponseResult(-1, "没查到");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseResult(-1, "网络错误");
		}
	}
}
