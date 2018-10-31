package com.nastryair.project.hotelmanager.respository;

import com.nastryair.project.hotelmanager.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByName(String userName);
}
