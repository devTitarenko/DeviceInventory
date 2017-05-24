package com.netcracker.edu.inventory.model;

import java.util.Set;

public interface AllToAllConnection<T extends Device> extends Connection<T, T> {

    ConnectorType getConnectorType();
    boolean addDevice(T device);
    boolean removeDevice(T device);
    boolean containDevice(T device);
    Set<T> getAllDevices();
    int getCurSize();
    int getMaxSize();
    
}
