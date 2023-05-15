package top.autuan.templatebusinessapi.notice.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinesssupport.notice.entity.MessageReadReq;
import top.autuan.templatebusinesssupport.notice.entity.SocketMsgRes;
import top.autuan.templatebusinesssupport.notice.entity.SocketNoticeItem;
import top.autuan.templatebusinesssupport.notice.service.impl.ManagerMessageWebSocketUsers;
import top.autuan.web.Result;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/station/message")
public class NoticeMessageController {
    @RequestMapping("/test")
    @Deprecated
    // todo remove this test method
    public Result test() {
        List<SocketNoticeItem> list = new ArrayList<>();
        list.add(new SocketNoticeItem("日程提醒", 3, "用户管理", "/system/user", 0));
        list.add(new SocketNoticeItem("费用报销审批", 26, "查询入库管理列表", "/wms/warehouse", 0));
        list.add(new SocketNoticeItem("最新消息", 14, "部门列表", "/system/dept", 0));
        SocketMsgRes socketMsgRes = new SocketMsgRes(0, list);
        ManagerMessageWebSocketUsers.sendMessage("admin", JSONUtil.toJsonStr(socketMsgRes));
        return Result.ok();
    }

    //    @Anonymous
    @RequestMapping("/read")
    public Result read(@RequestBody MessageReadReq messageReadReq) {
//        Long userId = SecurityUtils.getUserId();
//        messageReadReq.setUserId(userId);
//        messageService.read(messageReadReq);
        return Result.ok();
    }

    @RequestMapping("/add")
    @Deprecated
    // todo remove this test method
    public Result add() {
//        messageService.addNotification("all", NotificationDict.DEMO);
        return Result.ok();
    }
}
