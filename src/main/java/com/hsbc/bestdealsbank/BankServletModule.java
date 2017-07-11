package com.hsbc.bestdealsbank;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.servlet.ServletModule;
import com.hsbc.bestdealsbank.bootstrap.ApplicationModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class BankServletModule extends ServletModule {

    private static final String SERVES_PATH = "/services/*";
    private static final String RESOURCE_PACKAGE = "com.hsbc.bestdealsbank.resource";

    @Override
    protected void configureServlets() {

        bind(JacksonJsonProvider.class).asEagerSingleton();
        install(new ApplicationModule());

        new PackagesResourceConfig(RESOURCE_PACKAGE).getClasses()
                        .stream()
                        .forEach(endpoint -> bind(endpoint));

        serve(SERVES_PATH).with(GuiceContainer.class);
        
    }

}
