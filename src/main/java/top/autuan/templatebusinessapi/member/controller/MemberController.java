package top.autuan.templatebusinessapi.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.captcha.CaptchaComponent;
import top.autuan.templatebusinessapi.aop.OperLog;
import top.autuan.templatebusinessapi.member.entity.LogInReq;
import top.autuan.templatebusinessapi.member.entity.LoginRes;
import top.autuan.templatebusinesssupport.member.entity.Member;
import top.autuan.templatebusinesssupport.member.service.MemberService;
import top.autuan.web.Result;
import top.autuan.web.exception.BusinessException;

@Tag(name = "会员模块")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final CaptchaComponent captchaComponent;

    private final RedissonClient redissonClient;

//    private final ConfigImgService configImgService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    @OperLog(title = "登录")
    Result<LoginRes> login(@RequestBody @Validated LogInReq req) {
        Member member = memberService.login(req.mobile(), req.password(), req.verifyCode());

        StpUtil.login(member.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        LoginRes res = LoginRes.builder()
                .member(member)
                .memberId(member.getId())
                .saToken(tokenInfo)
                .build();
        return Result.ok(res);
    }

    @Operation(summary = "注销")
    @PostMapping("/logout")
    @SaCheckLogin
    @OperLog(title = "注销")
    Result<Object> logout() {
        StpUtil.logout();
        return Result.ok();
    }


//    @SaCheckLogin
//    @PostMapping("/avatar")
//            @OperLog(title = "修改头像")
//        // todo doc
//    Result<Object> avatar(@RequestBody @Validated EditAvatarReq req) {
//        long loginId = StpUtil.getLoginIdAsLong();
//        memberService.avatar(loginId, req.avatarPath());
//        return Result.ok();
//    }

//    @SaCheckLogin
//    @PostMapping("/nickname")
//            @OperLog(title = "修改昵称")
//        // todo doc
//    Result<Object> nickname(@RequestBody @Validated EditNicknameReq req) {
//        long loginId = StpUtil.getLoginIdAsLong();
//        memberService.nickname(loginId, req.nickname());
//        return Result.ok();
//    }

//    @PostMapping("/register")
//    @OperLog(title = "注册")
//    Result register(@RequestBody @Validated RegisterReq req) {
//
//        String mobile = req.getMobile();
//        // 验证码验证
//        Boolean pass = captchaComponent.verifyCaptcha(mobile, req.getVerifyCode());
//        if (!pass) {
//            throw new BusinessException("验证码错误");
//        }
//
//        Member member = new Member();
//        BeanUtil.copyProperties(req, member);
//        Member bean = memberService.register(member);
//
//        Long memberId = bean.getId();
//        StpUtil.login(memberId);
//        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
//
//        LoginRes res = LoginRes.builder()
//                .member(member)
//                .memberId(member.getId())
//                .saToken(tokenInfo)
//                .build();
//        return Result.ok(res);
//    }

    // todo 封装
    private boolean checkCode(String mobile, String code) {
        String key = StrUtil.format("pws:sms:{}", mobile);
        RBucket<String> bucket = redissonClient.getBucket(key);
        boolean exists = bucket.isExists();
        if (!exists) {
            return false;
        }
        String originCode = bucket.get();
        return code.equals(originCode);
    }

    @PostMapping("/destory")
    Result destory() {
        memberService.destory();
        return Result.ok();
    }
}
