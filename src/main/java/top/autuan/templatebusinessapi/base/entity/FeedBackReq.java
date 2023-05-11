package top.autuan.templatebusinessapi.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackReq {
    private Integer id;
    private Long memberId;
    private Integer feedBackType;
    private Integer status;
    private String reply;
    Integer pageNo;
    Integer pageSize;
}
