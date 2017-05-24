package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.location.Location;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl<D extends Device> implements Rack<D>, Serializable {

    private static Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());
    private Device[] devices;
    private final Class clazz;
    private Location location;

    public RackArrayImpl(int size, Class clazz) {
        if (size < 0) {
            IllegalArgumentException e =
                    new IllegalArgumentException("Rack size should not be negative");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (clazz == null) {
            IllegalArgumentException e =
                    new IllegalArgumentException("Type can't be null");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        devices = new Device[size];
        this.clazz = clazz;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int getSize() {
        return devices.length;
    }

    @Override
    public int getFreeSize() {
        int freeSize = 0;
        for (Device device : devices) {
            if (device == null) {
                freeSize++;
            }
        }
        return freeSize;
    }

    @Override
    public Class getTypeOfDevices() {
        return clazz;
    }

    @Override
    public D getDevAtSlot(int index) {
        checkIndex(index);
        return (D) devices[index];
    }

    @Override
    public boolean insertDevToSlot(D device, int index) {
        checkIndex(index);
        if (device == null || device.getIn() == 0 || device.getType() == null) {
            DeviceValidationException e =
                    new DeviceValidationException(device, "Rack.insertDevToSlot");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (getDevAtSlot(index) != null || !clazz.isInstance(device)) {
            return false;
        }
        devices[index] = device;
        return true;
    }

    @Override
    public D removeDevFromSlot(int index) {
        D device;
        checkIndex(index);
        if (devices[index] != null) {
            device = (D) devices[index];
            devices[index] = null;
            return device;
        } else {
            LOGGER.log(Level.WARNING, "Can not remove from empty slot " + index);
            return null;
        }
    }

    @Override
    public D getDevByIN(int in) {
        for (Device device : devices) {
            if (device != null && device.getIn() == in) {
                return (D) device;
            }
        }
        return null;
    }

    @Override
    public D[] getAllDeviceAsArray() {
        int newSize = getSize() - getFreeSize();
        Device[] arrayWithoutNull = new Device[newSize];
        int i = 0;
        for (Device tmp : devices) {
            if (tmp != null) {
                arrayWithoutNull[i] = tmp;
                i++;
            }
        }
        return (D[]) arrayWithoutNull;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= getSize()) {
            String message = "Index of device = " + index
                    + ", is out of range [0;" + (getSize() - 1) + "]";
            IndexOutOfBoundsException e = new IndexOutOfBoundsException(message);
            LOGGER.log(Level.SEVERE, message, e);
            throw e;
        }
    }
}
