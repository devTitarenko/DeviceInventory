package com.netcracker.edu.inventory.model;

public interface ManyToManyConnection<A extends Device, B extends Device> extends Connection<A, B>,
        Connection.MultipleAPoint<A, B>, Connection.MultipleBPoint<A, B> {
}
