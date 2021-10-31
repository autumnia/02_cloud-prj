package com.autumnia.aaservice.sample.domain;

public class Reservation {
    private Customer customer;
    private Screening screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening Screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.screening = Screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
