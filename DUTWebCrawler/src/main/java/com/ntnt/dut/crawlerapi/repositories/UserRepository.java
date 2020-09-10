package com.ntnt.dut.crawlerapi.repositories;

import com.ntnt.dut.crawlerapi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT u.id from UserEntity u" +
                    " WHERE u.username = ?1")
    public Integer getIdByUsername(String username);

    public Optional<UserEntity> findByUsername(String username);
}
