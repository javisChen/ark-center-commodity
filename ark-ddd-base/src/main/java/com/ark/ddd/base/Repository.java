package com.ark.ddd.base;

public interface Repository<A, ID> {

    Long store(A aggregate);

    A findById(ID id);

    boolean update(A aggregate);

    boolean remove(ID id);

}
