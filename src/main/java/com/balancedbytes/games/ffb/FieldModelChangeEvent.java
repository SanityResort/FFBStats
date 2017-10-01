/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import java.util.EventObject;

public class FieldModelChangeEvent
extends EventObject {
    public static final int TYPE_BLOODSPOT = 1;
    public static final int TYPE_PUSHBACK_SQUARE = 2;
    public static final int TYPE_PLAYER_POSITION = 3;
    public static final int TYPE_PLAYER_STATE = 4;
    public static final int TYPE_BALL_MOVING = 5;
    public static final int TYPE_BALL_COORDINATE = 6;
    public static final int TYPE_TRACK_NUMBER = 7;
    public static final int TYPE_WEATHER = 8;
    public static final int TYPE_DICE_DECORATION = 9;
    public static final int TYPE_MOVE_SQUARE = 10;
    public static final int TYPE_RANGE_RULER = 11;
    public static final int TYPE_FIELD_MARKER = 12;
    public static final int TYPE_PLAYER_MARKER = 13;
    public static final int TYPE_BOMB_COORDINATE = 14;
    public static final int TYPE_BOMB_MOVING = 15;
    private int fType;
    private Object fOldValue;
    private Object fNewValue;
    private Object fProperty;

    public FieldModelChangeEvent(Object pSource, int pType, Object pProperty, Object pOldValue, Object pNewValue) {
        super(pSource);
        this.fType = pType;
        this.fProperty = pProperty;
        this.fOldValue = pOldValue;
        this.fNewValue = pNewValue;
    }

    public int getType() {
        return this.fType;
    }

    public Object getOldValue() {
        return this.fOldValue;
    }

    public Object getNewValue() {
        return this.fNewValue;
    }

    public Object getProperty() {
        return this.fProperty;
    }

    public boolean isAdded() {
        return this.fNewValue != null && this.fOldValue == null;
    }

    public boolean isRemoved() {
        return this.fOldValue != null && this.fNewValue == null;
    }

    public boolean isUpdated() {
        return this.fNewValue != null && this.fOldValue != null;
    }
}

