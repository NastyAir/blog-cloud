package com.nastryair.project.hotelmanager.repository;

import com.nastryair.project.hotelmanager.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QWL on 2018/4/12.
 */
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Integer>, JpaSpecificationExecutor {
    Permission findByPermissionId(String permissionId);

    @Query("SELECT p FROM Permission p , RolePermission rp ,Role r ,UserRole ur , User u  WHERE r.roleId=rp.roleId AND  ur.roleId =rp.roleId AND  u.userId = ur.userId AND p.permissionId=rp.permissionId AND u.userId=:userId ")
    List<Permission> findPermissionsByUserId(@Param("userId") String userId);

    @Query("SELECT p FROM Permission p , RolePermission rp \n" +
            "WHERE p.permissionId=rp.permissionId AND rp.roleId=:roleId ")
    List<Permission> findPermByRoleId(@Param("roleId") String roleId);

    @Query("SELECT new com.nastryair.project.hotelmanager.dto.PermissionDto(id,permissionId,name) FROM Permission ")
    List<Permission> findNameList();

    @Query("SELECT COUNT(p.id) FROM Permission p, RolePermission rp, UserRole ur  WHERE ur.roleId = rp.roleId AND p.permissionId = rp.permissionId AND ur.userId =:userId AND p.code =:code")
    long countByUserIdAndCode(@Param("userId") String userId,
                              @Param("code") String code);

    @Query(value = "SELECT * FROM permission WHERE id = :id", nativeQuery = true)
    Permission getById(@Param("id") Integer id);

}
