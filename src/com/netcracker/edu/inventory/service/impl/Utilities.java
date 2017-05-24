package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;

import java.util.Arrays;
import java.util.Comparator;

class Utilities {
    void sortByIN(Device[] devices) {
        Arrays.sort(devices, new Comparator<Device>() {
            @Override
            public int compare(Device d1, Device d2) {
                if (d1 == null && d2 == null) {
                    return 0;
                }
                if (d1 == null) {
                    return 1;
                }
                if (d2 == null) {
                    return -1;
                }
                if (d1.getIn() == 0 && d2.getIn() != 0) {
                    return 1;
                }
                if (d2.getIn() == 0) {
                    return -1;
                }
                return d1.getIn() - d2.getIn();
            }
        });
    }

    void filtrateByType(Device[] devices, String type) {
        if (devices == null) {
            return;
        }
        for (int i = 0; i < devices.length; i++) {
            if (type == null) {
                if (devices[i] != null && (devices[i].getType() != null)) {
                    devices[i] = null;
                }
            } else {
                if (devices[i] != null && !type.equals(devices[i].getType())) {
                    devices[i] = null;
                }
            }
        }
    }
}
