package com.shf.springboot.web.provider;

import com.shf.springboot.NotWebBaseTest;

import org.junit.Assert;
import org.junit.Test;

public class EnvironmentProviderTest extends NotWebBaseTest {
    private static final String EMPTY_STRING = "";

    @Test
    public void existProperty() {
        final String encode = EnvironmentProvider.getProperty("spring.messages.encoding", EMPTY_STRING);
        Assert.assertEquals("Get property failure.", "UTF-8", encode);
    }

    @Test
    public void notExistProperty() {
        final String value = EnvironmentProvider.getProperty("not.exist.property", EMPTY_STRING);
        Assert.assertEquals("Get property failure.", EMPTY_STRING, value);
    }
}