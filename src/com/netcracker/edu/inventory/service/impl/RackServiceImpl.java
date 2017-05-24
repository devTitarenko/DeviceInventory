package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;

import java.io.*;

class RackServiceImpl implements RackService {
    private InputOutputOperations operations = new InputOutputOperations();

    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {
        operations.writeRack(rack, writer);
    }

    @Override
    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        return operations.readRack(reader);
    }

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        operations.outputRack(rack, outputStream);
    }

    @Override
    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        return operations.inputRack(inputStream);
    }

    @Override
    public void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        operations.serializeRack(rack, outputStream);
    }

    @Override
    public Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        return operations.deserializeRack(inputStream);
    }
}
