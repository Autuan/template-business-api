package top.autuan.templatebusinessapi.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "登录请求对象")
public record LogInReq(
        @Schema(description = "手机号",requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "手机号 不能为空")
        String mobile,

        @Schema(description = "密码",requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "密码 不能为空")
        String password,

        @Schema(description = "图形验证码",requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "图形验证码 不能为空")
        String verifyCode
) {}
