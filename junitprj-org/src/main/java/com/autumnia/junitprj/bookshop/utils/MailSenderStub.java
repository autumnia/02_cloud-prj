package com.autumnia.junitprj.bookshop.utils;

import org.springframework.stereotype.Component;

// 가짜
//@Component
public class MailSenderStub implements MailSender{
    @Override
    public boolean send() {
        return true;
    }
}
