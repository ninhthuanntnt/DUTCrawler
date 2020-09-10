package com.ntnt.dut.crawlerapi.services;

import com.ntnt.dut.crawlerapi.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepo;

}
