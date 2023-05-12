package top.autuan.templatebusinessapi.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppVersionsReq {
    private Integer platform;
    private String version;
    private Integer id;
}
