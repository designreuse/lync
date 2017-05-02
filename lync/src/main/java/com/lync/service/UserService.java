package com.lync.service;

import com.lync.domain.primary.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by breeze on 2017/2/21.
 */

public interface UserService {
    boolean deleteUserById(Long id);
    boolean addUser(User user);
    User findByUsername(String username);
    boolean updateUserRole(Long role_id,Long user_id);
    boolean addUserRole(Long user_id,Long role_id);
    List<User> findByUsernameAndPassword(String username,String password);
    List<User> findAll(Sort sort);
 }
