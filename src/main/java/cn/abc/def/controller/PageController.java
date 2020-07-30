package cn.abc.def.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/crossPage")
    public String crossPage() {
        return "cross";
    }
}
