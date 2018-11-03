package com.nastryair.project.hotelmanager.respository;

import com.nastryair.project.hotelmanager.entity.RolePermission;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by QWL on 2018/4/13.
 */
public interface RolePermissionRepository extends PagingAndSortingRepository<RolePermission, Integer> {
    void deleteByRoleId(String roleId);

    List<RolePermission> findAllByRoleId(String roleId);
}
