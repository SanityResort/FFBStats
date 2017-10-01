/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.util.StringTool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionKeyBindings {
    private FantasyFootballClient fClient;
    private Map<ActionKeyGroup, List<ActionKeyAction>> fActionsByGroup;

    public ActionKeyBindings(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fActionsByGroup = new HashMap<ActionKeyGroup, List<ActionKeyAction>>();
        this.init();
    }

    private void init() {
        String moveNorthwest;
        String moveNortheast;
        String illegalProcedure;
        String actionFoul;
        String cycleLeft;
        String moveSoutheast;
        String moveSouthwest;
        String moveWest;
        String actionMove;
        String actionPass;
        String actionStandUp;
        String actionLeap;
        String actionHandOver;
        String cycleRight;
        String actionMultipleBlock;
        String moveEast;
        String actionRangeGrid;
        String actionEndMove;
        String moveSouth;
        String actionBlitz;
        String actionGaze;
        String actionHailMaryPass;
        String actionStab;
        this.fActionsByGroup.clear();
        ArrayList<ActionKeyAction> playerMoves = new ArrayList<ActionKeyAction>();
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(104, 0), ActionKey.PLAYER_MOVE_NORTH));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(105, 0), ActionKey.PLAYER_MOVE_NORTHEAST));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(102, 0), ActionKey.PLAYER_MOVE_EAST));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(99, 0), ActionKey.PLAYER_MOVE_SOUTHEAST));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(98, 0), ActionKey.PLAYER_MOVE_SOUTH));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(97, 0), ActionKey.PLAYER_MOVE_SOUTHWEST));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(100, 0), ActionKey.PLAYER_MOVE_WEST));
        playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(103, 0), ActionKey.PLAYER_MOVE_NORTHWEST));
        String moveNorth = this.getClient().getProperty("key.player.move.north");
        if (StringTool.isProvided(moveNorth)) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveNorth), ActionKey.PLAYER_MOVE_NORTH));
        }
        if (StringTool.isProvided(moveNortheast = this.getClient().getProperty("key.player.move.northeast"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveNortheast), ActionKey.PLAYER_MOVE_NORTHEAST));
        }
        if (StringTool.isProvided(moveEast = this.getClient().getProperty("key.player.move.east"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveEast), ActionKey.PLAYER_MOVE_EAST));
        }
        if (StringTool.isProvided(moveSoutheast = this.getClient().getProperty("key.player.move.southeast"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveSoutheast), ActionKey.PLAYER_MOVE_SOUTHEAST));
        }
        if (StringTool.isProvided(moveSouth = this.getClient().getProperty("key.player.move.south"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveSouth), ActionKey.PLAYER_MOVE_SOUTH));
        }
        if (StringTool.isProvided(moveSouthwest = this.getClient().getProperty("key.player.move.southwest"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveSouthwest), ActionKey.PLAYER_MOVE_SOUTHWEST));
        }
        if (StringTool.isProvided(moveWest = this.getClient().getProperty("key.player.move.west"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveWest), ActionKey.PLAYER_MOVE_WEST));
        }
        if (StringTool.isProvided(moveNorthwest = this.getClient().getProperty("key.player.move.northwest"))) {
            playerMoves.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(moveNorthwest), ActionKey.PLAYER_MOVE_NORTHWEST));
        }
        this.fActionsByGroup.put(ActionKeyGroup.PLAYER_MOVES, playerMoves);
        ArrayList<ActionKeyAction> playerSelection = new ArrayList<ActionKeyAction>();
        String selectPlayer = this.getClient().getProperty("key.player.select");
        if (StringTool.isProvided(selectPlayer)) {
            playerSelection.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(selectPlayer), ActionKey.PLAYER_SELECT));
        }
        if (StringTool.isProvided(cycleRight = this.getClient().getProperty("key.player.cycle.right"))) {
            playerSelection.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(cycleRight), ActionKey.PLAYER_CYCLE_RIGHT));
        }
        if (StringTool.isProvided(cycleLeft = this.getClient().getProperty("key.player.cycle.left"))) {
            playerSelection.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(cycleLeft), ActionKey.PLAYER_CYCLE_LEFT));
        }
        this.fActionsByGroup.put(ActionKeyGroup.PLAYER_SELECTION, playerSelection);
        ArrayList<ActionKeyAction> playerActions = new ArrayList<ActionKeyAction>();
        String actionBlock = this.getClient().getProperty("key.player.action.block");
        if (StringTool.isProvided(actionBlock)) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionBlock), ActionKey.PLAYER_ACTION_BLOCK));
        }
        if (StringTool.isProvided(actionBlitz = this.getClient().getProperty("key.player.action.blitz"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionBlitz), ActionKey.PLAYER_ACTION_BLITZ));
        }
        if (StringTool.isProvided(actionFoul = this.getClient().getProperty("key.player.action.foul"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionFoul), ActionKey.PLAYER_ACTION_FOUL));
        }
        if (StringTool.isProvided(actionMove = this.getClient().getProperty("key.player.action.move"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionMove), ActionKey.PLAYER_ACTION_MOVE));
        }
        if (StringTool.isProvided(actionStandUp = this.getClient().getProperty("key.player.action.standup"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionStandUp), ActionKey.PLAYER_ACTION_STAND_UP));
        }
        if (StringTool.isProvided(actionHandOver = this.getClient().getProperty("key.player.action.handover"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionHandOver), ActionKey.PLAYER_ACTION_HAND_OVER));
        }
        if (StringTool.isProvided(actionPass = this.getClient().getProperty("key.player.action.pass"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionPass), ActionKey.PLAYER_ACTION_PASS));
        }
        if (StringTool.isProvided(actionLeap = this.getClient().getProperty("key.player.action.leap"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionLeap), ActionKey.PLAYER_ACTION_LEAP));
        }
        if (StringTool.isProvided(actionEndMove = this.getClient().getProperty("key.player.action.endMove"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionEndMove), ActionKey.PLAYER_ACTION_END_MOVE));
        }
        if (StringTool.isProvided(actionStab = this.getClient().getProperty("key.player.action.stab"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionStab), ActionKey.PLAYER_ACTION_STAB));
        }
        if (StringTool.isProvided(actionGaze = this.getClient().getProperty("key.player.action.gaze"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionGaze), ActionKey.PLAYER_ACTION_GAZE));
        }
        if (StringTool.isProvided(actionRangeGrid = this.getClient().getProperty("key.player.action.rangeGrid"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionRangeGrid), ActionKey.PLAYER_ACTION_RANGE_GRID));
        }
        if (StringTool.isProvided(actionHailMaryPass = this.getClient().getProperty("key.player.action.hailMaryPass"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionHailMaryPass), ActionKey.PLAYER_ACTION_HAIL_MARY_PASS));
        }
        if (StringTool.isProvided(actionMultipleBlock = this.getClient().getProperty("key.player.action.multipleBlock"))) {
            playerActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(actionMultipleBlock), ActionKey.PLAYER_ACTION_MULTIPLE_BLOCK));
        }
        this.fActionsByGroup.put(ActionKeyGroup.PLAYER_ACTIONS, playerActions);
        ArrayList<ActionKeyAction> turnActions = new ArrayList<ActionKeyAction>();
        String turnEnd = this.getClient().getProperty("key.toolbar.turn.end");
        if (StringTool.isProvided(turnEnd)) {
            turnActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(turnEnd), ActionKey.TOOLBAR_TURN_END));
        }
        if (StringTool.isProvided(illegalProcedure = this.getClient().getProperty("key.toolbar.illegal.procedure"))) {
            turnActions.add(new ActionKeyAction(this.getClient(), KeyStroke.getKeyStroke(illegalProcedure), ActionKey.TOOLBAR_ILLEGAL_PROCEDURE));
        }
        this.fActionsByGroup.put(ActionKeyGroup.TURN_ACTIONS, turnActions);
    }

    public void addKeyBindings(JComponent pComponent, ActionKeyGroup pActionKeyGroup) {
        if (pActionKeyGroup == ActionKeyGroup.ALL) {
            this.addKeyBindings(pComponent, ActionKeyGroup.PLAYER_MOVES);
            this.addKeyBindings(pComponent, ActionKeyGroup.PLAYER_SELECTION);
            this.addKeyBindings(pComponent, ActionKeyGroup.PLAYER_ACTIONS);
            this.addKeyBindings(pComponent, ActionKeyGroup.TURN_ACTIONS);
        } else {
            List<ActionKeyAction> actions = this.fActionsByGroup.get((Object)pActionKeyGroup);
            if (actions != null) {
                InputMap inputMap = pComponent.getInputMap(1);
                for (ActionKeyAction action : actions) {
                    Object actionMapKey = inputMap.get(action.getKeyStroke());
                    if (actionMapKey != null && actionMapKey instanceof ActionKey) {
                        Action currentAction = pComponent.getActionMap().get((Object)action.getActionKey());
                        if (currentAction == null) continue;
                        if (currentAction instanceof ActionKeyAction) {
                            ActionKeyAction actionKeyAction = (ActionKeyAction)currentAction;
                            ActionKeyMultiAction multiAction = new ActionKeyMultiAction(actionKeyAction.getActionKey());
                            multiAction.add(actionKeyAction);
                            multiAction.add(action);
                            pComponent.getActionMap().put((Object)action.getActionKey(), multiAction);
                            continue;
                        }
                        ActionKeyMultiAction multiAction = (ActionKeyMultiAction)currentAction;
                        multiAction.add(action);
                        continue;
                    }
                    inputMap.put(action.getKeyStroke(), (Object)action.getActionKey());
                    pComponent.getActionMap().put((Object)action.getActionKey(), action);
                }
            }
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }
}

