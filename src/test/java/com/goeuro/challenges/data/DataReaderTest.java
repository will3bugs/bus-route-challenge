package com.goeuro.challenges.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DataReaderTest {

    private RoutesAtStation routesAtStation;
    private DataReader dataReader;

    @Before
    public void setUp() throws Exception {
        dataReader = new DataReader("data/example");
        routesAtStation = dataReader.read();
        //Files.delete(Paths.get("file.db"));
    }

    @After
    public void tearDown() throws Exception {
        routesAtStation.close();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturnRoutesAtAStop150() throws Exception {
        Set<Integer> routesAtStop = routesAtStation.get(150).get();
        assertThat(routesAtStop.size(), is(equalTo(4)));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldReturnRoutesAtAStop121() throws Exception {
        Set<Integer> routesAtStop = routesAtStation.get(121).get();
        assertThat(routesAtStop.size(), is(equalTo(3)));
    }

    @Test
    public void shouldReturnNoRoutesIfTheStopDoesNotExist() throws Exception {
        Optional<Set<Integer>> routesAtStation = this.routesAtStation.get(3333);
        assertThat(routesAtStation.isPresent(), is(false));
    }

    @Test(expected = IOException.class)
    public void shouldThrowAnExceptionIfThereIsAnIOException() throws Exception {
        DataReader dataReader = new DataReader("invalidLocation");
        routesAtStation = dataReader.read();
    }
}