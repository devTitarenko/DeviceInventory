package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;

class DeviceServiceImpl implements DeviceService {
    private Validator validator = new Validator();
    private InputOutputOperations operations = new InputOutputOperations();

    @Override
    public boolean isCastableTo(Device device, Class clazz) {
        if (device == null || clazz == null) {
            return false;
        }
        return clazz.isInstance(device);
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        return validator.isValidDeviceForInsertToRack(device);
    }

    @Override
    public boolean isValidDeviceForWriteToStream(Device device) {
        return validator.isValidDeviceForWriteToStream(device);
    }

    @Override
    public void writeDevice(Device device, Writer writer) throws IOException {
        operations.writeDevice(device, writer);
    }

    @Override
    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return operations.readDevice(reader);
    }

    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        operations.outputDevice(device, outputStream);
    }

    @Override
    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        return operations.inputDevice(inputStream);
    }

    @Override
    public void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        operations.serializeDevice(device, outputStream);
    }

    @Override
    public Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        return operations.deserializeDevice(inputStream);
    }
}
