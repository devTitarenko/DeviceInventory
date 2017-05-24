package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AbstractDeviceTest {

    Device device;

    int in0 = 0;
    int in5 = 5;
    int inm3 = -3;
    String type = "";
    String manufacturer = "";
    String model = "";
    Date productionDate = new Date();

    @Before
    public void before() throws Exception {
        device = new AbstractDevice() {};
    }

    @After
    public void after() throws Exception {
        device = null;
    }

    @Test
    public void setGetIn() throws Exception {
        device.setIn(in5);
        int result = device.getIn();

        assertEquals(in5, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn0() throws Exception {
        device.setIn(in0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInNegative() throws Exception {
        device.setIn(inm3);
    }

    @Test
    public void setGetType() throws Exception {
        String result = device.getType();

        assertEquals(device.getClass().getSimpleName(), result);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        device.setManufacturer(manufacturer);
        String result = device.getManufacturer();

        assertEquals(manufacturer, result);
    }

    @Test
    public void setGetModel() throws Exception {
        device.setModel(model);
        String result = device.getModel();

        assertEquals(model, result);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        device.setProductionDate(productionDate);
        Date result = device.getProductionDate();

        assertEquals(productionDate, result);
    }

}