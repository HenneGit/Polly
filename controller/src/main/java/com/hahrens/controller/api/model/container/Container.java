package com.hahrens.controller.api.model.container;

import java.util.Collection;

public interface Container<T> {

    Collection<T> getContainedObjects();

}
