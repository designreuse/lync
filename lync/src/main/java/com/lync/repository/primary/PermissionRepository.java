package com.lync.repository.primary;

import com.lync.domain.primary.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Administrator on 2017/4/26 0026.
 */
@Repository
public interface PermissionRepository  extends JpaRepository<Permission,Long> {
    List<Permission> findByRoleId(Long roleId);
}
