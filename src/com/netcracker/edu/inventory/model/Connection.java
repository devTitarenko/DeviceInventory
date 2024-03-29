package com.netcracker.edu.inventory.model;

import java.io.Serializable;
import java.util.List;

/**
 * The interface Connection describe contract of mutable POJO,
 * witch represent connecting equipment-entity as object.
 */
public interface Connection<A extends Device, B extends Device> extends FeelableEntity, Serializable {

    String PLANED = "Planed"; // must be default
    String ON_BUILD = "On build";
    String READY = "Ready";
    String USED = "Used";
    String ON_DISASSEMBLING = "On disassembling";
    String DISASSEMBLED = "Disassembled";

    String getStatus();

    void setStatus(String status);

    interface HaveAPoint {
        ConnectorType getAPointConnectorType();
    }

    interface HaveBPoint {
        ConnectorType getBPointConnectorType();
    }

    interface OneAPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveAPoint {
        A getAPoint();
        void setAPoint(A device);
    }

    interface OneBPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveBPoint {
        B getBPoint();
        void setBPoint(B device);
    }

    interface MultipleAPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveAPoint {
        List<A> getAPoints();
        void setAPoints(List<A> devices);
        int getACapacity();
        A getAPoint(int deviceNumber);
        void setAPoint(A device, int deviceNumber);
    }

    interface MultipleBPoint<A extends Device, B extends Device> extends Connection<A, B>, HaveBPoint {
        List<B> getBPoints();
        void setBPoints(List<B> devices);
        int getBCapacity();
        B getBPoint(int deviceNumber);
        void setBPoint(B device, int deviceNumber);
    }

}
