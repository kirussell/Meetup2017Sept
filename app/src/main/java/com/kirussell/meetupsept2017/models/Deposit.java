package com.kirussell.meetupsept2017.models;


public class Deposit {
    Card card;
    double amount;

    public Deposit(Card card, double amount) {
        this.card = card;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Card getCard() {
        return card;
    }
}
