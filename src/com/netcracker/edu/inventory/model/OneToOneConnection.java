package com.netcracker.edu.inventory.model;

public interface OneToOneConnection<A extends Device, B extends Device> extends Connection<A, B>,
        Connection.OneAPoint<A, B>, Connection.OneBPoint<A, B> {
}
