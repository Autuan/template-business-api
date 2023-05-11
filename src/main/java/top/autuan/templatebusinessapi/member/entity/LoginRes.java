package top.autuan.templatebusinessapi.member.entity;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.Builder;
import top.autuan.templatebusinesssupport.member.entity.Member;

@Builder
public record LoginRes(
        SaTokenInfo saToken,
        Long memberId,
        Member member
) {
}
