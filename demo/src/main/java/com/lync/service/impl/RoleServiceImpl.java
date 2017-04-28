package com.lync.service.impl;

import com.lync.domain.primary.Role;
import com.lync.repository.primary.RoleRepository;
import com.lync.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by breeze on 2017/3/6.
 */
@SuppressWarnings( "all" )
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        roleRepository.findOne(id);
        return roleRepository.findOne(id);
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return roleRepository.findAll( sort );
    }


}
