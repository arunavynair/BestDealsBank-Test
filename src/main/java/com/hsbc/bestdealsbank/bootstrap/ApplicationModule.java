package com.hsbc.bestdealsbank.bootstrap;

import com.google.inject.servlet.ServletModule;
import com.hsbc.bestdealsbank.dao.DaoModule;
import com.hsbc.bestdealsbank.service.ServiceModule;
import com.hsbc.bestdealsbank.service.calculators.CalculatorModule;

public class ApplicationModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new ServiceModule());
        
        install(new CalculatorModule());
        
        install(new DaoModule());
    }
}
