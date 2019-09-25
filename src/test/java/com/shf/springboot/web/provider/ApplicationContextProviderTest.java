package com.shf.springboot.web.provider;

import com.shf.springboot.NotWebBaseTest;
import com.shf.springboot.properties.SampleProperties;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ApplicationContextProviderTest extends NotWebBaseTest {

    @Test
    public void getBean() {
        SampleProperties bean = ApplicationContextProvider.getBean(SampleProperties.class);
        assertNotNull(bean);
    }
}