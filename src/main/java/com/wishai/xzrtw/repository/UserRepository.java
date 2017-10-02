package com.wishai.xzrtw.repository;


import com.wishai.xzrtw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.TypedQuery;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    /*
    @Query("select u.id from User u where u.name=?1 and u.password=?2")
    public Integer findIdByNameAndPassword(String name, String password);
    */

    @Query("select u.name from User u where u.id=?1")
    public String findNameById(Integer id);

    @Query("select u.id, u.name, u.permission from User u where u.name=?1 and u.password=?2")
    public Object[] findIdAndNameAndPermissionByNameAndPassword(String name, String password);
}
