/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

public enum ActionKey {
    PLAYER_MOVE_NORTH("key.player.move.north"),
    PLAYER_MOVE_NORTHEAST("key.player.move.northeast"),
    PLAYER_MOVE_EAST("key.player.move.east"),
    PLAYER_MOVE_SOUTHEAST("key.player.move.southeast"),
    PLAYER_MOVE_SOUTH("key.player.move.south"),
    PLAYER_MOVE_SOUTHWEST("key.player.move.southwest"),
    PLAYER_MOVE_WEST("key.player.move.west"),
    PLAYER_MOVE_NORTHWEST("key.player.move.northwest"),
    PLAYER_SELECT("key.player.select"),
    PLAYER_CYCLE_RIGHT("key.player.cycle.right"),
    PLAYER_CYCLE_LEFT("key.player.cycle.left"),
    PLAYER_ACTION_BLOCK("key.player.action.block"),
    PLAYER_ACTION_BLITZ("key.player.action.blitz"),
    PLAYER_ACTION_FOUL("key.player.action.foul"),
    PLAYER_ACTION_MOVE("key.player.action.move"),
    PLAYER_ACTION_STAND_UP("key.player.action.standup"),
    PLAYER_ACTION_HAND_OVER("key.player.action.handover"),
    PLAYER_ACTION_PASS("key.player.action.pass"),
    PLAYER_ACTION_LEAP("key.player.action.leap"),
    PLAYER_ACTION_END_MOVE("key.player.action.endMove"),
    PLAYER_ACTION_STAB("key.player.action.stab"),
    PLAYER_ACTION_GAZE("key.player.action.gaze"),
    PLAYER_ACTION_RANGE_GRID("key.player.action.rangeGrid"),
    PLAYER_ACTION_HAIL_MARY_PASS("key.player.action.hailMaryPass"),
    PLAYER_ACTION_MULTIPLE_BLOCK("key.player.action.multipleBlock"),
    TOOLBAR_TURN_END("key.toolbar.turn.end"),
    TOOLBAR_ILLEGAL_PROCEDURE("key.toolbar.illegal.procedure"),
    MENU_SETUP_LOAD("key.menu.setup.load"),
    MENU_SETUP_SAVE("key.menu.setup.save"),
    MENU_REPLAY("key.menu.replay");
    
    private String fPropertyName;

    private ActionKey(String pPropertyName) {
        this.fPropertyName = pPropertyName;
    }

    public String getPropertyName() {
        return this.fPropertyName;
    }
}

