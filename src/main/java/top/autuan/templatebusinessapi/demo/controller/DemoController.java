package top.autuan.templatebusinessapi.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinessapi.demo.entity.DemoReq;
import top.autuan.templatebusinessapi.demo.entity.DemoRes;
import top.autuan.templatebusinesssupport.demo.service.DemoService;
import top.autuan.web.Result;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@Tag(name = "示例控制器")
public class DemoController {
    private final DemoService demoService;

    @Operation(summary = "示例接口")
    @PostMapping("/str")
    Result<DemoRes> demoStr(@RequestBody @Validated DemoReq req) {
        String str = demoService.queryStr();
        DemoRes res = new DemoRes(str);
        return Result.ok(res);
    }
}
