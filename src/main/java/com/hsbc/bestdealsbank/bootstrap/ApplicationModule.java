package com.hsbc.bestdealsbank.bootstrap;

import com.google.inject.servlet.ServletModule;
import com.hsbc.bestdealsbank.resource.ServiceModule;

public class ApplicationModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new ServiceModule());
    }
}
