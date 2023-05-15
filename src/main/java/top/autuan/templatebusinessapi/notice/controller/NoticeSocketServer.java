package top.autuan.templatebusinessapi.notice.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.autuan.templatebusinesssupport.notice.service.impl.ManagerMessageWebSocketUsers;

@Component
@Slf4j
//@ServerEndpoint(value = "/websocket/notice/{username}")
public class NoticeSocketServer {

    /**
     * 连接建立成功调用的方法
     * todo 在线用户数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws Exception {
//        ManagerMessageWebSocketUsers.put(username, session);
//        log.info("NoticeSocket -> connect -> username -> {} size -> {}",username,ManagerMessageWebSocketUsers.getUsers().size());

//        NoticeManagerMessageService bean = SpringUtils.getBean(NoticeManagerMessageService.class);
//        bean.obtainAndSendUnreadMsg(username);
    }

    /**
     * 连接关闭时处理
     */
    @OnClose
    public void onClose(Session session) {
        log.info("NoticeSocketServer -> close -> sessionId -> {}",session.getId());
        // 移除用户
        ManagerMessageWebSocketUsers.remove(session.getId());
    }

    /**
     * 抛出异常时处理
     */
    @OnError
    public void onError(Session session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            // 关闭连接
            session.close();
        }
        String sessionId = session.getId();
        log.warn("socket -> exception -> sessionId -> {} exception -> {}",sessionId,exception);

        // 移出用户
        ManagerMessageWebSocketUsers.remove(sessionId);
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.warn("ignore message from client -> sessionId -> {} message -> {}",session.getId(),message);
    }
}
