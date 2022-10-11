package com.autumnia.junitprj.bookshop.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailSenderAdapter implements MailSender {
    private final Mail mail;

    @Override
    public boolean send() {
//        return true;
        return mail.sendMail();
    }
}
