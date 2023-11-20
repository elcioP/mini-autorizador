package com.miniautorizador.miniautorizador.util;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class TestContainer {

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() { closeable = MockitoAnnotations.openMocks(this); }

    @AfterEach
    public void closeMocks() throws Exception { closeable.close(); }

    @Test
    public void init() {  assertThat("Init test container", equalTo("Init test container")); }
}