package com.lync.service;

import com.lync.domain.primary.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<User> findUserList(Pageable pageable);
 }
