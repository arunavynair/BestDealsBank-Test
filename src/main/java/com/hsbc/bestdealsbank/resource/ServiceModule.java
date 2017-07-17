package com.hsbc.bestdealsbank.resource;

import com.google.inject.AbstractModule;
import com.hsbc.bestdealsbank.dao.DaoModule;
import com.hsbc.bestdealsbank.service.CalculatorService;
import com.hsbc.bestdealsbank.service.CalculatorServiceImpl;
import com.hsbc.bestdealsbank.service.calculators.CalculatorModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new CalculatorModule());
        install(new DaoModule());
        bind(CalculatorService.class).to(CalculatorServiceImpl.class);
    }
}
