package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.Device;

public class DeviceValidationException extends RuntimeException {
    private Device nonValidDevice;

    public DeviceValidationException(Device device, String message) {
        super("Device is not valid for operation. " + message);
        nonValidDevice = device;
    }

    public Device getNotValidDevice() {
        return nonValidDevice;
    }

    public void setNotValidDevice(Device device) {
        nonValidDevice = device;
    }
}