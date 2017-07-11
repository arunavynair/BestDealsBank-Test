package com.hsbc.bestdealsbank.dao;

class Currency {

    private final String currencyName;
    private final double currencyRate;

    public Currency(String currencyName, double currencyRate) {
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public double getCurrencyRate() {
        return this.currencyRate;
    }

}
