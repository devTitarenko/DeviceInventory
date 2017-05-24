package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.model.impl.Wireless;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

class Validator {

    private static Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    boolean isValidDeviceForInsertToRack(Device device) {
        return !(device == null || device.getIn() == 0 || device.getType() == null);
    }

    boolean isValidDeviceForWriteToStream(Device device) {
        if (device == null || device.getType().contains("|")) {
            return false;
        }
        if ((device.getModel() != null) && (device.getModel().contains("|"))) {
            return false;
        }
        if ((device.getManufacturer() != null) && (device.getManufacturer().contains("|"))) {
            return false;
        }
        if (WifiRouter.class.isInstance(device)) {
            WifiRouter wifi = (WifiRouter) device;
            if ((wifi.getSecurityProtocol() != null) && (wifi.getSecurityProtocol().contains("|"))) {
                return false;
            }
        }
        return true;
    }

    boolean isValidConnectionForWriteToStream(Connection connection){
        if (connection == null) {
            return false;
        }

        if(connection instanceof Wireless) {
            Wireless wireless = (Wireless) connection;

            if(wireless.getTechnology() != null && wireless.getTechnology().contains("|")) {
                return false;
            }

            if(wireless.getProtocol() != null && wireless.getProtocol().contains("|")) {
                return false;
            }
        }

        return true;
    }

    void validate(OutputStream outputStream) {
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need OutputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    void validate(InputStream inputStream) {
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need InputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    void validate(Reader reader) {
        if (reader == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need InputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    void validate(Writer writer) {
        if (writer == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need Writer!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }
}