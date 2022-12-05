package com.example.springboot_server.Controller;

import com.alibaba.fastjson.JSON;
import com.example.springboot_server.DTO.payload;
import com.example.springboot_server.Service.DataService;
import com.example.springboot_server.Utils.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/18 22:36
 * @description TODO
 **/
@ServerEndpoint("/webSocket")      //你要用 @ServerEndpoint实现ws，就注定不能用@Autowired注入bean，
@Component
public class websocketController {
    //private static DataService dataService

    //@Autowired
    //public static void setDataService(DataService dataService){
    //    websocketController.dataService=dataService;
    //}
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     *  concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    private String UserName;
    /**
     * 发送消息
     */
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                System.out.println("发送数据："+ UserName+ message);
                session.getBasicRemote().sendText(message);
            }
        }
        System.out.println("----");
    }
    /**
     * 建立连接成功调用
     * @param session
     * @param
     */
    @OnOpen
    public void onOpen(Session session){
        UserName=UUID.randomUUID().toString();
        sessionPools.put(UserName, session);
        addOnlineCount();
        System.out.println(UserName + "加入webSocket！当前人数为" + onlineNum);
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(){
        sessionPools.remove(UserName);
        subOnlineCount();
        System.out.println(UserName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 收到客户端信息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("收到了客户端的消息");
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectReader reader = objectMapper.reader();

        payload payload = reader.readValue(message, payload.class);
        if("getData".equals(payload.getAction())){
            //dataService.pathResolve(payload);
            DataService dataService= SpringUtil.getBean(DataService.class);
            dataService.pathResolve(payload);
            Session session = sessionPools.get(UserName);
            System.out.println(UserName);
            sendMessage(session,JSON.toJSONString(payload));
        }else{
            for (Session session: sessionPools.values()) {
                try {
                    sendMessage(session, JSON.toJSONString(payload));
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 错误时调用
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("webSocket连接出错牌");
        throwable.printStackTrace();
    }

    /**
     * 当前连接数+1
     */
    public static void addOnlineCount(){ onlineNum.incrementAndGet(); }

    /**
     * 当前连接数-1
     */
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
