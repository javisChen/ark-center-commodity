package com.ark.ddd.base;

import java.util.List;

public interface Repository<A, ID> {

    Long store(A aggregate);

    A findById(ID id);

    boolean update(A aggregate);

    boolean remove(ID id);

    default List<A> queryByIds(List<ID> ids) {
        return null;
    }
}
