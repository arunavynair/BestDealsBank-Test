package com.hsbc.bestdealsbank.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.hsbc.bestdealsbank.BankServletModule;

/**
 * Guice provided Servlet context initializer
 *  
 * @author home
 */
public class GuiceServletContextListenerImpl extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new BankServletModule());
    }
}
