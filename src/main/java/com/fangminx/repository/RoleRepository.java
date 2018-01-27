package com.fangminx.repository;

import com.fangminx.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 角色数据DAO
 */
public interface RoleRepository extends CrudRepository<Role,Long>{
    List<Role> findRoleByUserId(Long userId);
}
