package com.hsbc.bestdealsbank.bootstrap;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class GuiceServletContextListenerImplTest {

    @Test
    public void shouldGetInjector(GuiceServletContextListenerImpl underTest) throws Exception {
        
        assertNotNull(underTest.getInjector());
    }

}
