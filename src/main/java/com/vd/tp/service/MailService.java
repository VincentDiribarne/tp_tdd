package com.vd.tp.service;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendMail(String to) {
        System.out.println("Mail sent to: " + to);
    }
}
