package com.lync.repository.primary;

import com.lync.domain.primary.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by breeze on 2017/3/6.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
