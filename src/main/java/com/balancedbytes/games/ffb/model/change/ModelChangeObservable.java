/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class ModelChangeObservable {
    private transient Set<IModelChangeObserver> fObservers = Collections.synchronizedSet(new HashSet());

    public boolean addObserver(IModelChangeObserver pObserver) {
        if (pObserver == null) {
            return false;
        }
        return this.fObservers.add(pObserver);
    }

    public boolean removeObserver(IModelChangeObserver pObserver) {
        if (pObserver == null) {
            return false;
        }
        return this.fObservers.remove(pObserver);
    }

    public IModelChangeObserver[] getObservers() {
        return this.fObservers.toArray(new IModelChangeObserver[this.fObservers.size()]);
    }

    public int countObservers() {
        return this.fObservers.size();
    }

    public void clearObservers() {
        this.fObservers.clear();
    }

    public void notifyObservers(ModelChange pModelChange) {
        if (pModelChange == null || pModelChange.getChangeId() == null) {
            return;
        }
        for (IModelChangeObserver observer : this.fObservers) {
            observer.update(pModelChange);
        }
    }
}

