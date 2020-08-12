package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import cn.abc.def.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取一个接口的所有实现类bean
 */
@RestController
public class GetImplListController {

    /*
     * Resource注解加在一个接口的list上, 可以把所有实现这个接口的bean获取到
     */
    @Resource
    private List<IUserService> userServices;

    @RequestMapping("/wow")
    public ResponseResult wow() {
        ResponseResult rr = new ResponseResult();
        rr.setData(userServices);
        return rr;
    }
}
