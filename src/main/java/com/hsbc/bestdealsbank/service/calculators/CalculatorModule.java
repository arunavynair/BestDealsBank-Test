package com.hsbc.bestdealsbank.service.calculators;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.hsbc.bestdealsbank.service.decorator.DecoratorModule;

public class CalculatorModule extends AbstractModule {

    @Override
    protected void configure() {
        
        install(new DecoratorModule());
        
        bind(InterestCalculator.class)
                        .annotatedWith(Names.named("simple")).to(CompoundInterestCalculator.class);
        bind(InterestCalculator.class)
                        .annotatedWith(Names.named("compound")).to(SimpleInterestCalculator.class);

    }

}
