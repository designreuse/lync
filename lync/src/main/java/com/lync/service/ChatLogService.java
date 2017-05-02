package com.lync.service;


import com.lync.domain.primary.Chatlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by breeze on 2017/3/6.
 */
public interface ChatLogService {
    //会话展示列表,按照会话开始时间倒叙排列,每页展示30条
    Page<Chatlog> findAll(Pageable pageable);
    //会话详情
    Page<Chatlog> findChatLogForUser(String username,Pageable pageable);
    //会话查询
}
