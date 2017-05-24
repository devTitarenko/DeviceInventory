package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractDevice implements Device, Serializable {

    private static Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    private int in;
    private final String type = getClass().getSimpleName();
    private String model;
    private String manufacturer;
    private Date productionDate;
    private List<Field> fields;

    @Override
    public int getIn() {
        return in;
    }

    @Override
    public void setIn(int in) {
        if (in < 1) {
            IllegalArgumentException e = new IllegalArgumentException("in > 0 !");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        this.in = in;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public Date getProductionDate() {
        return productionDate;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        if (fields == null) {
            return;
        }
        if (fields.get(0).getType().equals(int.class) && ((Integer) fields.get(0).getValue()) > 1) {
            in = (Integer) fields.get(0).getValue();
        }
        if (fields.get(1) != null && fields.get(1).getType().equals(String.class)) {
            model = (String) fields.get(1).getValue();
        }
        if (fields.get(2) != null && fields.get(2).getType().equals(String.class)) {
            manufacturer = (String) fields.get(2).getValue();
        }
        if (fields.get(3) != null && fields.get(3).getType().equals(Date.class)) {
            productionDate = (Date) fields.get(3).getValue();
        }
    }

    @Override
    public List<Field> getAllFieldsList() {
        fields = new ArrayList<Field>();
        fields.add(new Field(int.class, in));
        fields.add(new Field(String.class, model));
        fields.add(new Field(String.class, manufacturer));
        fields.add(new Field(Date.class, productionDate));
        return fields;
    }

    @Deprecated
    @Override
    public void feelAllFields(Field[] fields) {
        fillAllFields(new ArrayList<Field>(Arrays.asList(fields)));
    }

    @Deprecated
    @Override
    public Field[] getAllFields() {
        List<Field> fieldList = getAllFieldsList();
        Field[] fields = fieldList.toArray(new Field[fieldList.size()]);
        return fields;
    }
}