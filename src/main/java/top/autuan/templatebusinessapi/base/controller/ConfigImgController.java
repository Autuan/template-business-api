package top.autuan.templatebusinessapi.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.db.Page;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinessapi.base.entity.ConfigImgReq;
import top.autuan.templatebusinesssupport.base.entity.ConfigImg;
import top.autuan.templatebusinesssupport.base.service.ConfigImgService;
import top.autuan.web.Result;

@RestController
@RequestMapping("/configImg")
@RequiredArgsConstructor
public class ConfigImgController {

    private final ConfigImgService configImgService;


//    @SaCheckLogin
//    @PostMapping("/add")
//    Result add(@RequestBody ConfigImg configImg) {
//        configImgService.add(configImg);
//        return Result.ok();
//    }
//
//    @SaCheckLogin
//    @PostMapping("/edit")
//    Result edit(@RequestBody ConfigImg configImg) {
//        configImgService.edit(configImg);
//        return Result.ok();
//    }

    @SaCheckLogin
    @PostMapping("/list")
    Result list(@RequestBody ConfigImgReq configImg) {
        Page page = new Page(configImg.pageNo(), configImg.pageSize());
        ConfigImg img = new ConfigImg();
        BeanUtils.copyProperties(configImg, img);
        PageInfo<ConfigImg> list = configImgService.list(page, img);
        return Result.ok(list);
    }

//    @SaCheckLogin
//    @PostMapping("/abandon")
//    Result abandon(@RequestBody DetailReq req) {
//        configImgService.abandon(req.id());
//        return Result.ok();
//    }
}
