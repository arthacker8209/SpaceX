package com.deepak.spacex.common.base;

/**
 * Created by Deepak Kumawat on 17/07/24.
 * Maps one entity of one layer to another entity of different layer
 */
public interface Mapper<E, T> {
    T mapFrom(E from);
}


