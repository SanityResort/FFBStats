/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.model.Player;

public class ClientData {
    private Player fSelectedPlayer;
    private FieldCoordinate fDragEndPosition;
    private FieldCoordinate fDragStartPosition;
    private String fStatusTitle;
    private String fStatusMessage;
    private StatusType fStatusType;
    private int fNrOfBlockDice;
    private int[] fBlockRoll;
    private int fBlockDiceIndex;
    private boolean fActingPlayerUpdated;
    private boolean fTurnTimerStopped;
    private boolean fEndTurnButtonHidden;
    private int fSpectators;
    private SpecialEffect fWizardSpell;

    public Player getSelectedPlayer() {
        return this.fSelectedPlayer;
    }

    public void setSelectedPlayer(Player pPlayer) {
        this.fSelectedPlayer = pPlayer;
    }

    public FieldCoordinate getDragEndPosition() {
        return this.fDragEndPosition;
    }

    public void setDragEndPosition(FieldCoordinate pEndPosition) {
        this.fDragEndPosition = pEndPosition;
    }

    public FieldCoordinate getDragStartPosition() {
        return this.fDragStartPosition;
    }

    public void setDragStartPosition(FieldCoordinate pStartPosition) {
        this.fDragStartPosition = pStartPosition;
    }

    public void setBlockDiceResult(int pNrOfBlockDice, int[] pBlockRoll, int pBlockDiceIndex) {
        this.fNrOfBlockDice = pNrOfBlockDice;
        this.fBlockRoll = pBlockRoll;
        this.fBlockDiceIndex = pBlockDiceIndex;
    }

    public void clearBlockDiceResult() {
        this.setBlockDiceResult(0, null, -1);
    }

    public int getNrOfBlockDice() {
        return this.fNrOfBlockDice;
    }

    public int[] getBlockRoll() {
        return this.fBlockRoll;
    }

    public int getBlockDiceIndex() {
        return this.fBlockDiceIndex;
    }

    public void setStatus(String pTitle, String pMessage, StatusType pType) {
        this.fStatusTitle = pTitle;
        this.fStatusMessage = pMessage;
        this.fStatusType = pType;
    }

    public void clearStatus() {
        this.setStatus(null, null, null);
    }

    public String getStatusTitle() {
        return this.fStatusTitle;
    }

    public String getStatusMessage() {
        return this.fStatusMessage;
    }

    public StatusType getStatusType() {
        return this.fStatusType;
    }

    public void setActingPlayerUpdated(boolean pActingPlayerUpdated) {
        this.fActingPlayerUpdated = pActingPlayerUpdated;
    }

    public boolean isActingPlayerUpdated() {
        return this.fActingPlayerUpdated;
    }

    public void setTurnTimerStopped(boolean pTimerStopped) {
        this.fTurnTimerStopped = pTimerStopped;
    }

    public boolean isTurnTimerStopped() {
        return this.fTurnTimerStopped;
    }

    public int getSpectators() {
        return this.fSpectators;
    }

    public void setSpectators(int pSpectators) {
        this.fSpectators = pSpectators;
    }

    public void setWizardSpell(SpecialEffect pWizardSpell) {
        this.fWizardSpell = pWizardSpell;
    }

    public SpecialEffect getWizardSpell() {
        return this.fWizardSpell;
    }

    public boolean isEndTurnButtonHidden() {
        return this.fEndTurnButtonHidden;
    }

    public void setEndTurnButtonHidden(boolean pEndTurnButtonHidden) {
        this.fEndTurnButtonHidden = pEndTurnButtonHidden;
    }

    public void clear() {
        this.setSelectedPlayer(null);
        this.setDragStartPosition(null);
        this.setDragEndPosition(null);
        this.clearBlockDiceResult();
        this.clearStatus();
        this.setActingPlayerUpdated(false);
        this.setWizardSpell(null);
        this.setEndTurnButtonHidden(false);
    }
}

