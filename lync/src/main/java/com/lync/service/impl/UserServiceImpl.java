package com.lync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lync.core.constant.Const;
import com.lync.domain.primary.User;
import com.lync.repository.primary.UserRepository;
import com.lync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breeze on 2017/2/21.
 */
@SuppressWarnings( "all" )
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Transactional
    @Override
    public boolean deleteUserById(Long id) {
         return userRepository.deleteUserById(Const.TIMESTAMP,id)>0;
    }

    @Override
    public boolean addUser(User user) {
        return userRepository.save( user )!=null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameAndStatu( username,1);
    }
    @Transactional
    @Override
    public boolean updateUserRole(Long role_id,Long user_id) {
       return userRepository.updateUserRole(role_id,user_id)>0;
    }
    @Transactional
    @Override
    public boolean addUserRole(Long user_id, Long role_id) {
        return userRepository.addUserRole(user_id,role_id)>0;
    }

    @Override
    public Page<User> findUserList(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageable.getOffset();
        Integer index=offset;
        Integer end=pageSize;
        if(offset>0){
            index=offset;
        }
        ArrayList<Object> list = new ArrayList<>();
        List<User> userList = userRepository.findUserList(index,end);
        for(User u:userList){
            JSONObject userinfo = new JSONObject();
            userinfo.put("username",u.getUsername());
            userinfo.put("statu",u.getStatu());
            userinfo.put("roles",u.getRolesName());
            userinfo.put("create_time",u.getCreateTime());
            list.add(userinfo);
        }
        int total = userRepository.findUserListTotal();
        return new PageImpl(list,pageable,total);
    }

}
