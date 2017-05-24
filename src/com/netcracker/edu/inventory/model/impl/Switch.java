package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Switch extends Router implements Device {
    private int numberOfPorts;
    private ConnectorType portsType;
    private Connection[] portsConnections;

    public Switch(ConnectorType portsType) {
        this.portsType = portsType;
        this.portsConnections = new Connection[0];
    }

    public Switch() {
        this(ConnectorType.need_init);
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
        this.portsConnections = new Connection[numberOfPorts];
    }

    @Override
    public void fillAllFields(List<Field> fields) {
        Field numberOfPortsField = fields.get(fields.size() - 3);
        Field portsTypeField = fields.get(fields.size() - 2);
        Field portsConnectionsField = fields.get(fields.size() - 1);

        setNumberOfPorts((Integer) numberOfPortsField.getValue());
        if (portsType == ConnectorType.need_init) {
            portsType = (ConnectorType) portsTypeField.getValue();
        }
        List<Connection> connections = (List<Connection>) portsConnectionsField.getValue();
        portsConnections = connections.toArray(new Connection[connections.size()]);

        super.fillAllFields(fields.subList(0, fields.size() - 3));
    }

    @Override
    public List<Field> getAllFieldsList() {
        List<Field> fields = super.getAllFieldsList();

        fields.add(new Field(int.class, getNumberOfPorts()));
        fields.add(new Field(ConnectorType.class, getPortsType()));
        fields.add(new Field(List.class, new ArrayList<Connection>(Arrays.asList(portsConnections))));

        return fields;
    }

    public Connection getPortConnection(int portNumber) {
        return portsConnections[portNumber];
    }

    public void setPortConnection(Connection connection, int portNumber) {
        portsConnections[portNumber] = connection;
    }

    public List<Connection> getAllPortConnections() {
        return new ArrayList<Connection>(Arrays.asList(portsConnections));
    }

    public ConnectorType getPortsType() {
        return portsType;
    }
}
