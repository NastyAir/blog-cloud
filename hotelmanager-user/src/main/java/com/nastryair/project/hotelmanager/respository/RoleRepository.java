package com.nastryair.project.hotelmanager.respository;

import com.nastryair.project.hotelmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QWL on 2018/4/12.
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor {
    Role findByRoleId(String roleId);

    void deleteByRoleId(String role);

    @Modifying
    @Query(value = "UPDATE role r SET r.name=:name,r.description=:description,r.update_time=:updateTime WHERE r.role_id=:roleId", nativeQuery = true)
    void updateByRoleId(
            @Param("name") String name,
            @Param("description") String description,
            @Param("updateTime") String updateTime,
            @Param("roleId") String roleId
    );

    @Query("SELECT new com.nastryair.project.hotelmanager.dto.RoleDto(id,roleId,name) FROM Role ")
    List<Role> findNameList();

    @Query("SELECT r FROM UserRole ur,Role r WHERE r.roleId=ur.roleId AND ur.userId=:userId")
    List<Role> findByUserId(@Param("userId") String userId);

    Role findByName(String name);

    @Query(value = "SELECT * FROM role WHERE id = :id", nativeQuery = true)
    Role getById(@Param("id") Integer id);
}
