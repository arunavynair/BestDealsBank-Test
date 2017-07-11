package com.hsbc.bestdealsbank.dao;

import com.google.inject.AbstractModule;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dao.class).toInstance(new InMemoryDaoImpl());
    }

}
