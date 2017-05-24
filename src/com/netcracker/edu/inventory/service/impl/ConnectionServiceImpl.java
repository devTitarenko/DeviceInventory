package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.service.ConnectionService;

import java.io.*;

class ConnectionServiceImpl implements ConnectionService {
    private Validator validator = new Validator();
    private InputOutputOperations operations = new InputOutputOperations();

    @Override
    public boolean isValidConnectionForWriteToStream(Connection connection) {
        return validator.isValidConnectionForWriteToStream(connection);
    }

    @Override
    public void writeConnection(Connection connection, Writer writer) throws IOException {
        operations.writeConnection(connection, writer);
    }

    @Override
    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        return operations.readConnection(reader);
    }

    @Override
    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        operations.outputConnection(connection, outputStream);
    }

    @Override
    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        return operations.inputConnection(inputStream);
    }
}