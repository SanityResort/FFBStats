/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.model.change.IModelChangeObserver;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import java.util.HashSet;
import java.util.Set;

public abstract class ModelChangeObservable {
    private transient Set<IModelChangeObserver> fObservers = new HashSet<IModelChangeObserver>();

    public void addObserver(IModelChangeObserver pObserver) {
        if (pObserver == null) {
            return;
        }
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            this.fObservers.add(pObserver);
        }
    }

    public boolean removeObserver(IModelChangeObserver pObserver) {
        if (pObserver == null) {
            return false;
        }
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            return this.fObservers.remove(pObserver);
        }
    }

    public IModelChangeObserver[] getObservers() {
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            return this.fObservers.toArray(new IModelChangeObserver[this.fObservers.size()]);
        }
    }

    public int countObservers() {
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            return this.fObservers.size();
        }
    }

    public void clearObservers() {
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            this.fObservers.clear();
        }
    }

    public void notifyObservers(ModelChange pModelChange) {
        if (pModelChange == null || pModelChange.getChangeId() == null) {
            return;
        }
        Set<IModelChangeObserver> set = this.fObservers;
        synchronized (set) {
            for (IModelChangeObserver observer : this.fObservers) {
                observer.update(pModelChange);
            }
        }
    }
}

