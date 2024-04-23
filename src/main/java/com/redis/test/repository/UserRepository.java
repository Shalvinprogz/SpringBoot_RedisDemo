package com.redis.test.repository;

import com.redis.test.domain.UserDO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserDO, Integer> {
    @Query("from UserDO where id = :id")
    UserDO find(@Param("id") String id);
}
