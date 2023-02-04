package top.autuan.templatebusinessapi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinesssupport.demo.service.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/str")
    Object demoStr(){
        return demoService.queryStr();
    }
}
