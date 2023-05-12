package top.autuan.templatebusinessapi.base.entity;


public record ConfigImgReq(
        Long id,

        Long storeId,


        String des,


        Integer linkType,


        Integer status,

        Integer seq,

        Integer type,
        Integer pageNo,
        Integer pageSize,
        Byte delFlag
) {
}
