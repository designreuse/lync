package com.lync.service;

import com.lync.domain.primary.Permission;


import java.util.List;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public interface PermissionService {

    List<Permission> findPermissionsByRoleId(Long id);
}
