package com.hsbc.bestdealsbank.service.calculators;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.hsbc.bestdealsbank.service.decorator.DecoratorModule;

public class CalculatorModule extends AbstractModule {

    @Override
    public void configure() {

        install(new DecoratorModule());

        bind(InterestCalculator.class)
                        .annotatedWith(Names.named("simple")).to(CompoundInterestCalculator.class);
        bind(InterestCalculator.class)
                        .annotatedWith(Names.named("compound")).to(SimpleInterestCalculator.class);

    }

    @Provides
    public Map<String, InterestCalculator> getCalculators(CompoundInterestCalculator compound, SimpleInterestCalculator simple) {
        Map<String, InterestCalculator> calculators = new HashMap<>();

        calculators.put("simple", simple);
        calculators.put("compound", compound);

        return calculators;
    }
}
