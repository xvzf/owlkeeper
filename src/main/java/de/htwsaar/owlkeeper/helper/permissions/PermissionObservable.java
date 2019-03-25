package de.htwsaar.owlkeeper.helper.permissions;

import de.htwsaar.owlkeeper.service.InsufficientPermissionsException;

import java.util.ArrayList;
import java.util.List;

public abstract class PermissionObservable {
    private List<PermissionObserver> observers;
    private boolean state;

    public PermissionObservable() {
        observers = new ArrayList<>();
        state = false;
    }

    public boolean getState() {
        return state;
    }

    public void checkPermission(int action) throws InsufficientPermissionsException {
        state = !state;
        notifyObservers(action);
    }

    public void attach(PermissionObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(int action) throws InsufficientPermissionsException {
        for (PermissionObserver o : observers) {
            o.update(action);
        }
    }
}