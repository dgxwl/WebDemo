package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跨域
 */
@RestController
@RequestMapping("/cross")
public class CrossOriginController {

    /**
     * json跨域示例. 待完善: 为了安全, 可以增加一个白名单
     * @param param 业务参数
     * @param callback 回调函数名
     * @return 返回内容, 拼接成: 回调函数名(处理结果数据), 即系将返回内容作为参数, 一返回前端就调这个callback函数
     */
    @RequestMapping("/jsonpDemo")
    public String jsonpDemo(String param, String callback) {
        return callback + "("
                + JSON.toJSONString(new ResponseResult("The param is: " + param))
                + ")";
    }

    /**
     * 使用CrossOrigin注解在接口上实现允许跨域访问
     * @param param 业务参数
     * @return rr
     */
    @CrossOrigin(origins = "*")
    @RequestMapping("/crossDemo")
    public ResponseResult crossDemo(String param) {
        return new ResponseResult("The param is: " + param);
    }
}
