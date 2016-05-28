/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class FieldCoordinate {
    public static final int FIELD_WIDTH = 26;
    public static final int FIELD_HEIGHT = 15;
    public static final int RSV_HOME_X = -1;
    public static final int KO_HOME_X = -2;
    public static final int BH_HOME_X = -3;
    public static final int SI_HOME_X = -4;
    public static final int RIP_HOME_X = -5;
    public static final int BAN_HOME_X = -6;
    public static final int MNG_HOME_X = -7;
    public static final int RSV_AWAY_X = 30;
    public static final int KO_AWAY_X = 31;
    public static final int BH_AWAY_X = 32;
    public static final int SI_AWAY_X = 33;
    public static final int RIP_AWAY_X = 34;
    public static final int BAN_AWAY_X = 35;
    public static final int MNG_AWAY_X = 36;
    private int fX;
    private int fY;

    public FieldCoordinate(int pX, int pY) {
        this.fX = pX;
        this.fY = pY;
    }

    public FieldCoordinate(int pNrOfSquare) {
        this(pNrOfSquare % 26, pNrOfSquare / 26);
    }

    public int getX() {
        return this.fX;
    }

    public int getY() {
        return this.fY;
    }

    public int getNrOfSquare() {
        return this.getY() * 26 + this.getX();
    }

    public int hashCode() {
        return this.getNrOfSquare();
    }

    public boolean equals(Object pObject) {
        if (pObject != null && pObject instanceof FieldCoordinate) {
            FieldCoordinate otherCoordinate = (FieldCoordinate)pObject;
            return this.getY() == otherCoordinate.getY() && this.getX() == otherCoordinate.getX();
        }
        return super.equals(pObject);
    }

    public FieldCoordinate add(int pDeltaX, int pDeltaY) {
        return new FieldCoordinate(this.getX() + pDeltaX, this.getY() + pDeltaY);
    }

    public int distanceInSteps(FieldCoordinate pOtherCoordinate) {
        int result = -1;
        if (pOtherCoordinate != null) {
            result = Math.max(Math.abs(this.getX() - pOtherCoordinate.getX()), Math.abs(this.getY() - pOtherCoordinate.getY()));
        }
        return result;
    }

    public boolean isAdjacent(FieldCoordinate pOtherCoordinate) {
        return pOtherCoordinate != null && this.distanceInSteps(pOtherCoordinate) == 1;
    }

    public FieldCoordinate transform() {
        switch (this.getX()) {
            case -1: {
                return new FieldCoordinate(30, this.getY());
            }
            case -2: {
                return new FieldCoordinate(31, this.getY());
            }
            case -3: {
                return new FieldCoordinate(32, this.getY());
            }
            case -4: {
                return new FieldCoordinate(33, this.getY());
            }
            case -5: {
                return new FieldCoordinate(34, this.getY());
            }
            case -6: {
                return new FieldCoordinate(35, this.getY());
            }
            case -7: {
                return new FieldCoordinate(36, this.getY());
            }
            case 30: {
                return new FieldCoordinate(-1, this.getY());
            }
            case 31: {
                return new FieldCoordinate(-2, this.getY());
            }
            case 32: {
                return new FieldCoordinate(-3, this.getY());
            }
            case 33: {
                return new FieldCoordinate(-4, this.getY());
            }
            case 34: {
                return new FieldCoordinate(-5, this.getY());
            }
            case 35: {
                return new FieldCoordinate(-6, this.getY());
            }
            case 36: {
                return new FieldCoordinate(-7, this.getY());
            }
        }
        return new FieldCoordinate(25 - this.getX(), this.getY());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(this.getX()).append(",").append(this.getY()).append(")");
        return sb.toString();
    }

    public int compareTo(FieldCoordinate pAnotherFc) {
        if (pAnotherFc == null) {
            return -1;
        }
        if (!(pAnotherFc instanceof FieldCoordinate)) {
            return -1;
        }
        if (pAnotherFc.getX() < this.getX()) {
            return 1;
        }
        if (pAnotherFc.getX() > this.getX()) {
            return -1;
        }
        return this.getY() - pAnotherFc.getY();
    }

    public boolean isBoxCoordinate() {
        switch (this.getX()) {
            case -7: 
            case -6: 
            case -5: 
            case -4: 
            case -3: 
            case -2: 
            case -1: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: {
                return true;
            }
        }
        return false;
    }

    public static FieldCoordinate transform(FieldCoordinate pFieldCoordinate) {
        return pFieldCoordinate != null ? pFieldCoordinate.transform() : null;
    }

    public static boolean equals(FieldCoordinate pCoordinate1, FieldCoordinate pCoordinate2) {
        if (pCoordinate1 != null) {
            return pCoordinate1.equals(pCoordinate2);
        }
        if (pCoordinate2 != null) {
            return pCoordinate2.equals(pCoordinate1);
        }
        return true;
    }
}

