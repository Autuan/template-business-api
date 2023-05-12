package top.autuan.templatebusinessapi.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinesssupport.base.entity.AddressRes;
import top.autuan.templatebusinesssupport.base.service.AddressService;
import top.autuan.web.Result;

import java.util.Map;

@RequestMapping("/address")
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @RequestMapping("/all")
    @ResponseBody
    Result<AddressRes> all() {
        AddressRes res = addressService.all();
        return Result.ok(res);
    }

    @RequestMapping("/map")
    @ResponseBody
    Result<Object> map() {
        Map<String, String> obj = addressService.map();
        return Result.ok(obj);
    }

}
