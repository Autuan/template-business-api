package top.autuan.templatebusinessapi.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "示例响应对象")
public class DemoRes {
    @Schema(description = "昵称")
    private String name;
}
