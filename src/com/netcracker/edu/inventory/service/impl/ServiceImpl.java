package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.ConnectionService;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

public class ServiceImpl implements Service {
    private Utilities utilities = new Utilities();

    public ConnectionService getConnectionService() {
        return new ConnectionServiceImpl();
    }

    @Override
    public DeviceService getDeviceService() {
        return new DeviceServiceImpl();
    }

    @Override
    public RackService getRackService() {
        return new RackServiceImpl();
    }

    @Override
    public void sortByIN(Device[] devices) {
        utilities.sortByIN(devices);
    }

    @Override
    public void filtrateByType(Device[] devices, String type) {
        utilities.filtrateByType(devices, type);
    }
}
