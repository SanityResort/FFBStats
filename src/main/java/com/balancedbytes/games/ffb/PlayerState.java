/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class PlayerState {
    public static final int UNKNOWN = 0;
    public static final int STANDING = 1;
    public static final int MOVING = 2;
    public static final int PRONE = 3;
    public static final int STUNNED = 4;
    public static final int KNOCKED_OUT = 5;
    public static final int BADLY_HURT = 6;
    public static final int SERIOUS_INJURY = 7;
    public static final int RIP = 8;
    public static final int RESERVE = 9;
    public static final int MISSING = 10;
    public static final int FALLING = 11;
    public static final int BLOCKED = 12;
    public static final int BANNED = 13;
    public static final int EXHAUSTED = 14;
    public static final int BEING_DRAGGED = 15;
    public static final int PICKED_UP = 16;
    public static final int HIT_BY_FIREBALL = 17;
    public static final int HIT_BY_LIGHTNING = 18;
    public static final int HIT_BY_BOMB = 19;
    private static final int _BIT_ACTIVE = 256;
    private static final int _BIT_CONFUSED = 512;
    private static final int _BIT_ROOTED = 1024;
    private static final int _BIT_HYPNOTIZED = 2048;
    private static final int _BIT_BLOODLUST = 4096;
    private static final int _BIT_USED_PRO = 8192;
    private static int[] _BASE_MASK = new int[]{0, 65280, 65280, 65280, 65280, 0, 0, 0, 0, 0, 0, 65280, 65280, 0, 65280, 65280, 65280};
    private int fId;

    public PlayerState(int pId) {
        this.fId = pId;
    }

    public int getId() {
        return this.fId;
    }

    public int getBase() {
        return this.getId() & 255;
    }

    public PlayerState changeBase(int pBase) {
        int baseMask = pBase > 0 && pBase < _BASE_MASK.length ? _BASE_MASK[pBase] : 0;
        return new PlayerState(this.getId() & baseMask | pBase);
    }

    public boolean isActive() {
        return this.hasBit(256);
    }

    public PlayerState changeActive(boolean pActive) {
        return this.changeBit(256, pActive);
    }

    public boolean isConfused() {
        return this.hasBit(512);
    }

    public PlayerState changeConfused(boolean pConfused) {
        return this.changeBit(512, pConfused);
    }

    public boolean isRooted() {
        return this.hasBit(1024);
    }

    public PlayerState changeRooted(boolean pRooted) {
        return this.changeBit(1024, pRooted);
    }

    public boolean isHypnotized() {
        return this.hasBit(2048);
    }

    public PlayerState changeHypnotized(boolean pHypnotized) {
        return this.changeBit(2048, pHypnotized);
    }

    public boolean hasBloodlust() {
        return this.hasBit(4096);
    }

    public PlayerState changeBloodlust(boolean pBloodlust) {
        return this.changeBit(4096, pBloodlust);
    }

    public boolean hasUsedPro() {
        return this.hasBit(8192);
    }

    public PlayerState changeUsedPro(boolean pUsedPro) {
        return this.changeBit(8192, pUsedPro);
    }

    public boolean isCasualty() {
        return 6 == this.getBase() || 7 == this.getBase() || this.isKilled();
    }

    public boolean isKilled() {
        return 8 == this.getBase();
    }

    public boolean canBeSetUp() {
        return 1 == this.getBase() || 2 == this.getBase() || 3 == this.getBase() || 4 == this.getBase() || 9 == this.getBase() || 11 == this.getBase() || 12 == this.getBase();
    }

    public boolean hasTacklezones() {
        return (1 == this.getBase() || 2 == this.getBase() || 12 == this.getBase()) && !this.isConfused() && !this.isHypnotized();
    }

    public boolean isProne() {
        return 3 == this.getBase() || 4 == this.getBase();
    }

    public boolean isStunned() {
        return 4 == this.getBase();
    }

    public boolean isAbleToMove() {
        return (1 == this.getBase() || 2 == this.getBase() || 3 == this.getBase()) && this.isActive() && !this.isRooted();
    }

    public boolean canBeBlocked() {
        return 1 == this.getBase() || 2 == this.getBase();
    }

    public boolean canBeFouled() {
        return 3 == this.getBase() || 4 == this.getBase();
    }

    private PlayerState changeBit(int pMask, boolean pBit) {
        if (pBit) {
            return new PlayerState(this.getId() | pMask);
        }
        return new PlayerState(this.getId() & (65535 ^ pMask));
    }

    private boolean hasBit(int pMask) {
        return (this.getId() & pMask) > 0;
    }

    public String getDescription() {
        switch (this.getBase()) {
            case 0: {
                return "is unknown";
            }
            case 1: {
                return "is standing";
            }
            case 2: {
                return "is moving";
            }
            case 3: {
                return "is prone";
            }
            case 4: {
                return "has been stunned";
            }
            case 5: {
                return "has been knocked out";
            }
            case 6: {
                return "has been badly hurt";
            }
            case 7: {
                return "has been seriously injured";
            }
            case 8: {
                return "has been killed";
            }
            case 9: {
                return "is in reserve";
            }
            case 10: {
                return "is missing the game";
            }
            case 11: {
                return "is about to fall down";
            }
            case 12: {
                return "is being blocked";
            }
            case 13: {
                return "is banned from the game";
            }
            case 14: {
                return "is exhausted";
            }
            case 15: {
                return "is being dragged";
            }
            case 16: {
                return "has been picked up";
            }
        }
        return null;
    }

    public String getButtonText() {
        switch (this.getBase()) {
            case 0: {
                return "Unknown";
            }
            case 1: {
                return "Standing";
            }
            case 2: {
                return "Moving";
            }
            case 3: {
                return "Prone";
            }
            case 4: {
                return "has been stunned";
            }
            case 5: {
                return "Knocked Out";
            }
            case 6: {
                return "Badly Hurt";
            }
            case 7: {
                return "Serious Injury";
            }
            case 8: {
                return "Killed";
            }
            case 9: {
                return "Reserve";
            }
            case 10: {
                return "Missing";
            }
            case 11: {
                return "Falling Down";
            }
            case 12: {
                return "Blocked";
            }
            case 13: {
                return "Banned";
            }
            case 14: {
                return "Exhausted";
            }
            case 15: {
                return "Being Dragged";
            }
            case 16: {
                return "Picked Up";
            }
        }
        return null;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.fId;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PlayerState other = (PlayerState)obj;
        if (this.fId != other.fId) {
            return false;
        }
        return true;
    }
}

