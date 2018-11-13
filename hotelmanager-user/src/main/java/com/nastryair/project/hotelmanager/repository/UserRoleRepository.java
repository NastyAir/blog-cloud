package com.nastryair.project.hotelmanager.repository;

import com.nastryair.project.hotelmanager.entity.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by QWL on 2018/4/10.
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Integer>, JpaSpecificationExecutor {
    List<UserRole> findByUserId(String userId);

    void deleteByUserId(String userId);

}
