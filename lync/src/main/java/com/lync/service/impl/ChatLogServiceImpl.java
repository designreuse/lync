package com.lync.service.impl;

import com.lync.domain.primary.Chatlog;
import com.lync.domain.primary.Role;
import com.lync.repository.primary.ChatLogRepository;
import com.lync.repository.primary.RoleRepository;
import com.lync.service.ChatLogService;
import com.lync.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by breeze on 2017/3/6.
 */
@SuppressWarnings( "all" )
@Service
public class ChatLogServiceImpl implements ChatLogService{
    @Autowired
    ChatLogRepository chatLogRepository;

    @Override
    public Page<Chatlog> findAll(Pageable pageable) {
        return chatLogRepository.findAll( pageable );
    }

    public Page<Chatlog> findChatLogForUser(String username,Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageable.getOffset();
        Integer index=offset;
        Integer end=pageSize;

        System.out.println(pageable.getOffset());    //
        System.out.println(pageable.getPageNumber());//第几页
        System.out.println(pageable.getPageSize());//一页多少条
        if(offset>0){
            index=offset;
        }
        List<Chatlog> chatlogList = chatLogRepository.findChatLogList(username,index,end);
            int total = chatLogRepository.findChatLogListTotal(username);
            return new PageImpl(chatlogList,pageable,total);
    }

}
