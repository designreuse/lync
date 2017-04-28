package com.lync.service.impl;


import com.lync.domain.primary.Permission;
import com.lync.repository.primary.PermissionRepository;
import com.lync.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by breeze on 2017/3/6.
 */
@SuppressWarnings( "all" )
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findPermissionsByRoleId(Long id) {
        return  permissionRepository.findByRoleId(id);
    }
}
