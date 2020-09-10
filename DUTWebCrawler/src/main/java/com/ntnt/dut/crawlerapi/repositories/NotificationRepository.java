package com.ntnt.dut.crawlerapi.repositories;

import com.ntnt.dut.crawlerapi.models.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
