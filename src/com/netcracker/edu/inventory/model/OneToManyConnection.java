package com.netcracker.edu.inventory.model;

public interface OneToManyConnection<A extends Device, B extends Device> extends Connection<A, B>,
        Connection.OneAPoint<A, B>, Connection.MultipleBPoint<A, B> {
}
