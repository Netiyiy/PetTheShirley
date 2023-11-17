package com.kevin.pettheshirley;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Getter @Setter
    private int bank;
    @Getter @Setter
    private int wallet;

    public Player() {

    }

    public Player(int bank, int wallet) {
        this.bank = bank;
        this.wallet = wallet;
    }

    public void deposit() {
        bank += wallet;
        wallet = 0;
    }

    public int deposit(int amount) {
        int amountDeposited = amount;
        if (amount <= wallet) {
            bank += amount;
            wallet -= amount;
            return amountDeposited;
        }
        amountDeposited = wallet;
        bank += wallet;
        wallet = 0;
        return amountDeposited;
    }

    public int withdrawal(int amount) {
        int amountWithdrawn = amount;
        if (amount <= bank) {
            bank -= amount;
            wallet += amount;
            return amountWithdrawn;
        }
        amountWithdrawn = bank;
        wallet += bank;
        bank = 0;
        return amountWithdrawn;
    }

    public void withdrawal() {
        wallet += bank;
        bank = 0;
    }

    public void death() {
        wallet = 0;
    }

}
