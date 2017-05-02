package com.lync.service.impl;

import com.lync.core.constant.Const;
import com.lync.domain.primary.User;
import com.lync.repository.primary.UserRepository;
import com.lync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
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
    public List<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword( username,password );
    }

}
