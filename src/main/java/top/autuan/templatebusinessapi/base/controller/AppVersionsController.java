package top.autuan.templatebusinessapi.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinessapi.base.entity.AppVersionsReq;
import top.autuan.templatebusinesssupport.base.entity.AppVersions;
import top.autuan.templatebusinesssupport.base.entity.AppVersionsRes;
import top.autuan.templatebusinesssupport.base.service.AppVersionsService;
import top.autuan.web.Result;

import java.util.List;

@RequestMapping("/app/version")
@RestController
@RequiredArgsConstructor
public class AppVersionsController {
    private final AppVersionsService appVersionsService;


    @PostMapping("/detail")
    Result<Object> detail(@RequestBody AppVersionsReq req) {
        AppVersions appVersions = appVersionsService.detail(req.getVersion(), req.getPlatform());
        return Result.ok(appVersions);
    }


//    @PostMapping("/change")
//    Result change(@RequestBody AppVersions versions) {
//        appVersionsService.change(versions);
//        return Result.ok();
//    }

//    @PostMapping("/add")
//    Result add(@RequestBody AppVersions versions){
//        appVersionsService.add(versions);
//        return Result.ok();
//    }

    @PostMapping("/check")
    Result<AppVersionsRes> check(AppVersionsReq req){
        AppVersionsRes res =   appVersionsService.check(req.getPlatform(),req.getVersion());
        return Result.ok(res);
    }

    @PostMapping("/list")
    Result<Object> versionList(){
    List<AppVersions> appVersions = appVersionsService.versionList();
    return Result.ok(appVersions);
    }
}
