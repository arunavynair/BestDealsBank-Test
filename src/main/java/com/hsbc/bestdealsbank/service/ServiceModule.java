package com.hsbc.bestdealsbank.service;

import com.google.inject.AbstractModule;
import com.hsbc.bestdealsbank.service.CalculatorService;
import com.hsbc.bestdealsbank.service.CalculatorServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CalculatorService.class).to(CalculatorServiceImpl.class);
    }
}
