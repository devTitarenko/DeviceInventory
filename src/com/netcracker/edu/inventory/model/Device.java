package com.netcracker.edu.inventory.model;

import java.util.Date;

/**
 * The interface Device describe contract of mutable POJO,
 * witch represent device-entity as object.
 */
public interface Device extends FeelableEntity {
    int getIn();
    void setIn(int in);
    String getType();
    String getManufacturer();
    void setManufacturer(String manufacturer);
    String getModel();
    void setModel(String model);
    Date getProductionDate();
    void setProductionDate(Date productionDate);
}
