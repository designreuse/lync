package com.lync.service;

import com.lync.domain.primary.Role;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by breeze on 2017/3/6.
 */
public interface RoleService {
    Role findById(Long id);
    List<Role> findAll(Sort sort);
}
