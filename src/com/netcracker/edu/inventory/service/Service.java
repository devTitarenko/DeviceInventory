package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Device;

/**
 * The interface Service describe list of services of Inventory component
 */
public interface Service {

    /**
     * Return DeviceService implementation
     *
     * @return implementation of DeviceService interface
     */
    DeviceService getDeviceService();

    /**
     * Return RackService implementation
     *
     * @return implementation of RackService interface
     */
    RackService getRackService();

    /**
     * Sort array of Device-s by identification number.
     *
     * @param devices - array of Device-s, that need to be sorted
     */
    void sortByIN(Device[] devices);

    /**
     * Filtrate array of Device-s by type
     *
     * @param devices - array of Device-s, that need to be filtrated
     * @param type - type of Devices, that will remain in the array after filtering
     */
    void filtrateByType(Device[] devices, String type);

}