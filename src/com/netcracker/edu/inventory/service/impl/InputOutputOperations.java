package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.location.Location;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class InputOutputOperations {
    private Validator validator = new Validator();
    private static Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());
    private List<FeelableEntity.Field> fieldList;

    /**
     * General methods for connections and devices
     */
    private String lineReader(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char ch = (char) reader.read();
        while ((ch != '\n') && (ch != '\r')) {
            stringBuilder.append(ch);
            ch = (char) reader.read();
        }
        reader.read();
        return stringBuilder.toString();
    }

    private String removeSpaces(String str) {
        return (str.equals(" ")) ? null : str.substring(1, str.length() - 1);
    }

    private void outputDeviceConnection(Device device, Connection connection, OutputStream outputStream) throws IOException {
        if (device == null && connection == null) {
            return;
        }
        validator.validate(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (connection != null) {
            dataOutputStream.writeUTF(connection.getClass().getName());
            fieldList = connection.getAllFieldsList();
        } else {
            dataOutputStream.writeUTF(device.getClass().getName());
            fieldList = device.getAllFieldsList();
        }
        writeOutputStream(dataOutputStream);
    }

    private void choiceOfMethod(StringTokenizer stringTokenizer, DataInputStream dataInputStream) throws IOException {
        if (stringTokenizer != null) {
            setValuesToFieldList(stringTokenizer);
        } else {
            setValuesToFieldList(dataInputStream);
        }
    }

    private void writeWriter(Writer writer) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            if (field.getValue() == null) {
                writer.write(field.getType() == Date.class ? " -1 |" : " |");
            } else {
                if (fieldList.indexOf(field) != 0) {
                    writer.write(" ");
                }
                Class type = field.getType();
                if (fieldList.indexOf(field) == 0 && type == int.class) {
                    writer.write("[" + field.getValue() + "]");
                    continue;
                } else if (field.getType() == Device.class) {
                    writer.write("");
                } else if (field.getType() == Date.class) {
                    writer.write(((Date) field.getValue()).getTime() + "");
                } else if (field.getType() == ConnectorType.class) {
                    writer.write(((ConnectorType) field.getValue()).name());
                } else if (field.getType() == Array.class) {
                    Device[] devicesArray = (Device[]) field.getValue();
                    writer.write(devicesArray.length + " |");
                    for (Device device : devicesArray) {
                        writer.write(" |");
                    }
                } else if (field.getType() == Set.class) {
                    Set<Device> deviceSet = (Set<Device>) field.getValue();
                    writer.write(deviceSet.size() + " |");
                    for (Device device : deviceSet) {
                        writer.write(" |");
                    }
                } else if (field.getType() == List.class) {
                    List<Connection> connectionList = (List<Connection>) field.getValue();
                    writer.write(connectionList.size() + " |");
                    for (Connection connection : connectionList) {
                        writer.write(" |");
                    }
                } else {
                    writer.write(field.getValue().toString());
                }
                writer.write(" |");
            }
        }
        writer.write(LineSeparator.Windows);
    }

    private void writeOutputStream(DataOutputStream dataOutputStream) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            if (field.getValue() == null && field.getType() != Date.class) {
                dataOutputStream.writeUTF("\n");
            } else {
                Class type = field.getType();
                if (type == int.class) {
                    dataOutputStream.writeInt((Integer) field.getValue());
                } else if (field.getType() == Device.class) {
                    dataOutputStream.writeUTF("\n");
                } else if (field.getType() == Date.class) {
                    dataOutputStream.writeLong(field.getValue() == null ? -1 :
                            ((Date) field.getValue()).getTime());
                } else if (field.getType() == ConnectorType.class) {
                    dataOutputStream.writeUTF(((ConnectorType) field.getValue()).name());
                } else if (field.getType() == TwistedPair.Type.class) {
                    dataOutputStream.writeUTF(((TwistedPair.Type) field.getValue()).getFullName());
                } else if (field.getType() == OpticFiber.Mode.class) {
                    dataOutputStream.writeUTF(((OpticFiber.Mode) field.getValue()).getFullName());
                } else if (field.getType() == Array.class) {
                    Device[] devicesArray = (Device[]) field.getValue();
                    dataOutputStream.writeInt(devicesArray.length);
                    for (Device device : devicesArray) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else if (field.getType() == Set.class) {
                    Set<Device> deviceSet = (Set<Device>) field.getValue();
                    dataOutputStream.writeInt(deviceSet.size());
                    for (Device device : deviceSet) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else if (field.getType() == List.class) {
                    List<Connection> connectionList = (List<Connection>) field.getValue();
                    dataOutputStream.writeInt(connectionList.size());
                    for (Connection connection : connectionList) {
                        dataOutputStream.writeUTF("\n");
                    }
                } else {
                    dataOutputStream.writeUTF(field.getValue().toString());
                }
            }
        }
    }

    private void setValuesToFieldList(StringTokenizer stringTokenizer) {
        for (FeelableEntity.Field field : fieldList) {
            Class type = field.getType();
            if (type == int.class) {
                if (fieldList.indexOf(field) == 0) {
                    field.setValue(Integer.parseInt(stringTokenizer.nextToken()));
                } else {
                    field.setValue(Integer.parseInt(removeSpaces(stringTokenizer.nextToken())));
                }
            } else if (type == String.class) {
                if (fieldList.indexOf(field) == 0) {
                    String status = stringTokenizer.nextToken();
                    status = status.substring(0, status.length() - 1);
                    field.setValue(status);
                } else {
                    field.setValue(removeSpaces(stringTokenizer.nextToken()));
                }
            } else if (type == Date.class) {
                long productionDate = Long.parseLong(removeSpaces(stringTokenizer.nextToken()));
                Date date = null;
                if (productionDate != -1) {
                    date = new Date(productionDate);
                }
                field.setValue(date);
            } else if (type == Device.class) {
                Device device = removeSpaces(stringTokenizer.nextToken()) == null ? null : null;
                field.setValue(device);
            } else if (type == ConnectorType.class) {
                String connectorType = removeSpaces(stringTokenizer.nextToken());
                field.setValue(ConnectorType.valueOf(connectorType));
            } else if (type == OpticFiber.Mode.class) {
                String mode = removeSpaces(stringTokenizer.nextToken());
                field.setValue(mode == null ? OpticFiber.Mode.need_init : OpticFiber.Mode.valueOf(mode));
            } else if (type == TwistedPair.Type.class) {
                String numType = removeSpaces(stringTokenizer.nextToken());
                field.setValue(numType == null ? TwistedPair.Type.need_init : TwistedPair.Type.valueOf(numType));
            } else if (type == Set.class) {
                Set<Device> deviceSet = new HashSet<Device>();
                int sizeOfSet = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                for (int i = 0; i < sizeOfSet; i++) {
                    stringTokenizer.nextToken();
                    deviceSet.add(null);
                }
                field.setValue(deviceSet);
            } else if (type == Set.class) {
                List<Connection> connectionList = new ArrayList<Connection>();
                int sizeOfList = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                for (int i = 0; i < sizeOfList; i++) {
                    stringTokenizer.nextToken();
                    connectionList.add(null);
                }
                field.setValue(connectionList);
            } else if (type == Array.class) {
                int arrayLenght = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                Device[] deviceArray = new Device[arrayLenght];
                for (int i = 0; i < deviceArray.length; i++) {
                    stringTokenizer.nextToken();
                    deviceArray[i] = null;
                }
                field.setValue(deviceArray);
            }
        }
    }

    private void setValuesToFieldList(DataInputStream dataInputStream) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            Class type = field.getType();
            if (type == int.class) {
                field.setValue(dataInputStream.readInt());
            } else if (type == String.class) {
                String str = dataInputStream.readUTF();
                field.setValue(str.equals("\n") ? null : str);
            } else if (type == Date.class) {
                long productionDate = dataInputStream.readLong();
                Date date = null;
                if (productionDate != -1) {
                    date = new Date(productionDate);
                }
                field.setValue(date);
            } else if (type == Device.class) {
                Device device = dataInputStream.readUTF().equals("\n") ? null : null;
                field.setValue(device);
            } else if (type == ConnectorType.class) {
                String connectorType = dataInputStream.readUTF();
                field.setValue(ConnectorType.valueOf(connectorType));
            } else if (type == OpticFiber.Mode.class) {
                String mode = dataInputStream.readUTF();
                field.setValue(OpticFiber.Mode.valueOf(mode));
            } else if (type == TwistedPair.Type.class) {
                String numType = dataInputStream.readUTF();
                field.setValue(TwistedPair.Type.valueOf(numType));
            } else if (type == Set.class) {
                Set<Device> deviceSet = new HashSet<Device>();
                int sizeOfSet = dataInputStream.readInt();
                for (int i = 0; i < sizeOfSet; i++) {
                    dataInputStream.readUTF();
                    deviceSet.add(null);
                }
                field.setValue(deviceSet);
            } else if (type == List.class) {
                List<Connection> connectionList = new ArrayList<Connection>();
                int sizeOfList = dataInputStream.readInt();
                for (int i = 0; i < sizeOfList; i++) {
                    dataInputStream.readUTF();
                    connectionList.add(null);
                }
                field.setValue(connectionList);
            } else if (type == Array.class) {
                int arrayLenght = dataInputStream.readInt();
                Device[] deviceArray = new Device[arrayLenght];
                for (int i = 0; i < deviceArray.length; i++) {
                    dataInputStream.readUTF();
                    deviceArray[i] = null;
                }
                field.setValue(deviceArray);
            }
        }
    }

    /**
     * Methods for work with connections
     */
    void writeConnection(Connection connection, Writer writer) throws IOException {
        if (connection == null) {
            return;
        }
        validator.validate(writer);
        if (!new Validator().isValidConnectionForWriteToStream(connection)) {
            IllegalArgumentException e = new IllegalArgumentException("Connection is not valid for write to stream");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        fieldList = connection.getAllFieldsList();
        writer.write(connection.getClass().getName() + LineSeparator.Windows);
        writeWriter(writer);
    }

    Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);

        String className = lineReader(reader);
        Class clazz;
        if (className.equals("")) {
            return null;
        } else {
            clazz = Class.forName(className);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(lineReader(reader), "|");
        return getConnection(clazz, stringTokenizer, null);
    }

    void outputConnection(Connection connection, OutputStream outputStream) throws IOException {
        outputDeviceConnection(null, connection, outputStream);
    }

    Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        validator.validate(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        return getConnection(clazz, null, dataInputStream);
    }

    private Connection getConnection(Class clazz, StringTokenizer stringTokenizer, DataInputStream dataInputStream)
            throws ClassNotFoundException, IOException {
        if (clazz.isInstance(new OpticFiber())) {
            OpticFiber opticFiber = new OpticFiber();
            fieldList = opticFiber.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            opticFiber.fillAllFields(fieldList);
            return opticFiber;
        }
        if (clazz.isInstance(new ThinCoaxial())) {
            ThinCoaxial thinCoaxial = new ThinCoaxial();
            fieldList = thinCoaxial.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            thinCoaxial.fillAllFields(fieldList);
            return thinCoaxial;
        }
        if (clazz.isInstance(new TwistedPair())) {
            TwistedPair twistedPair = new TwistedPair();
            fieldList = twistedPair.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            twistedPair.fillAllFields(fieldList);
            return twistedPair;
        }
        if (clazz.isInstance(new Wireless())) {
            Wireless wireless = new Wireless();
            fieldList = wireless.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            wireless.fillAllFields(fieldList);
            return wireless;
        }

        ClassNotFoundException e = new ClassNotFoundException("Wrong Class!");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    /**
     * Methods for work with devices
     */
    void writeDevice(Device device, Writer writer) throws IOException {
        if (device == null) {
            return;
        }
        validator.validate(writer);
        if (!new Validator().isValidDeviceForWriteToStream(device)) {
            DeviceValidationException e = new DeviceValidationException(device, "DeviceService.writeDevice");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        fieldList = device.getAllFieldsList();
        writer.write(device.getClass().getName() + LineSeparator.Windows);
        writeWriter(writer);
    }

    Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        validator.validate(reader);
        String className = lineReader(reader);
        Class clazz;
        if (className.equals("")) {
            return null;
        } else {
            clazz = Class.forName(className);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(lineReader(reader), "[]|");
        return getDevice(clazz, stringTokenizer, null);
    }

    void outputDevice(Device device, OutputStream outputStream) throws IOException {
        outputDeviceConnection(device, null, outputStream);
    }

    Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        validator.validate(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Class clazz = Class.forName(dataInputStream.readUTF());
        return getDevice(clazz, null, dataInputStream);
    }

    private Device getDevice(Class clazz, StringTokenizer stringTokenizer, DataInputStream dataInputStream)
            throws ClassNotFoundException, IOException {
        if (clazz.isInstance(new Battery())) {
            Battery battery = new Battery();
            fieldList = battery.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            battery.fillAllFields(fieldList);
            return battery;
        }
        if (clazz.isInstance(new Router())) {
            Router router = new Router();
            fieldList = router.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            router.fillAllFields(fieldList);
            return router;
        }
        if (clazz.isInstance(new Switch())) {
            Switch switcher = new Switch();
            fieldList = switcher.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            switcher.fillAllFields(fieldList);
            return switcher;
        }
        if (clazz.isInstance(new WifiRouter())) {
            WifiRouter wifiRouter = new WifiRouter();
            fieldList = wifiRouter.getAllFieldsList();
            choiceOfMethod(stringTokenizer, dataInputStream);
            wifiRouter.fillAllFields(fieldList);
            return wifiRouter;
        }

        ClassNotFoundException e = new ClassNotFoundException("Wrong Class!");
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    void serializeDevice(Device device, OutputStream outputStream) throws IOException {
        if (device == null) {
            return;
        }
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need OutputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(device);
    }

    Device deserializeDevice(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        Device device;
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need InputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectInput objectInput = new ObjectInputStream(inputStream);
        device = (Device) objectInput.readObject();
        return device;
    }

    /**
     * Methods for work with racks
     */
    void writeRack(Rack rack, Writer writer) throws IOException {
        if (rack == null) {
            return;
        }
        validator.validate(writer);

        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        locationService.writeLocation(rack.getLocation(), writer);
        writer.write(rack.getSize() + " ");
        writer.write(rack.getTypeOfDevices().getCanonicalName() + LineSeparator.Windows);
        for (int i = 0; i < rack.getSize(); i++) {
            Device device = rack.getDevAtSlot(i);
            if (device != null) {
                writeDevice(device, writer);
            } else {
                writer.write(LineSeparator.Windows);
            }
        }
    }

    Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        Rack rack;
        validator.validate(reader);

        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        Location location = locationService.readLocation(reader);
        StringTokenizer stringTokenizer = new StringTokenizer(lineReader(reader), " ");
        int in = Integer.parseInt(stringTokenizer.nextToken());
        Class clazz = Class.forName(stringTokenizer.nextToken());

        rack = new RackArrayImpl(in, clazz);
        rack.setLocation(location);
        for (int i = 0; i < rack.getSize(); i++) {
            Device device = readDevice(reader);
            if (device != null) {
                rack.insertDevToSlot(device, i);
            }
        }
        return rack;
    }

    void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }
        validator.validate(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        locationService.outputLocation(rack.getLocation(), dataOutputStream);
        dataOutputStream.writeInt(rack.getSize());
        dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());

        for (int i = 0; i < rack.getSize(); i++) {
            if (rack.getDevAtSlot(i) == null) {
                dataOutputStream.writeUTF("\n");
            } else {
                dataOutputStream.writeUTF("Device");
                outputDevice(rack.getDevAtSlot(i), dataOutputStream);
            }
        }
    }

    Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        Rack rack;
        validator.validate(inputStream);

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        com.netcracker.edu.location.impl.ServiceImpl locationService = new com.netcracker.edu.location.impl.ServiceImpl();
        Location location = locationService.inputLocation(dataInputStream);
        int size = dataInputStream.readInt();
        Class clazz = Class.forName(dataInputStream.readUTF());

        rack = new RackArrayImpl(size, clazz);
        rack.setLocation(location);
        for (int i = 0; i < rack.getSize(); i++) {
            if (dataInputStream.readUTF().equals("Device")) {
                rack.insertDevToSlot(inputDevice(dataInputStream), i);
            }
        }
        return rack;
    }

    void serializeRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack == null) {
            return;
        }
        if (outputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need OutputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(rack);
    }

    Rack deserializeRack(InputStream inputStream) throws IOException, ClassCastException, ClassNotFoundException {
        Rack rack;
        if (inputStream == null) {
            IllegalArgumentException e = new IllegalArgumentException("Need InputStream!");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ObjectInput objectInput = new ObjectInputStream(inputStream);
        rack = (Rack) objectInput.readObject();
        return rack;
    }
}