package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跨域
 */
@RestController
@RequestMapping("/cross")
public class CrossOriginController {

    /**
     * json跨域示例
     * @param param 业务参数
     * @param callback 回调函数名
     * @return 返回内容, 拼接成: 回调函数名(处理结果数据), 即系将返回内容作为参数, 一返回前端就调这个callback函数
     */
    @RequestMapping("/jsonpDemo")
    public String crossDemo(String param, String callback) {
        return callback + "("
                + JSON.toJSONString(new ResponseResult("The param is: " + param))
                + ")";
    }
}
