package top.autuan.templatebusinessapi.demo.entity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "示例请求对象")
public class DemoReq {
    @Schema(description = "昵称",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "name 不能为空")
    private String name;
}
