/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.Skill;

public enum PlayerChoiceMode implements INamedObject
{
    TENTACLES("tentacles"),
    SHADOWING("shadowing"),
    DIVING_TACKLE("divingTackle"),
    FEED("feed"),
    DIVING_CATCH("divingCatch"),
    CARD("card"),
    BLOCK("block"),
    MVP("mvp");
    
    private String fName;

    private PlayerChoiceMode(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getDialogHeader(int nrOfPlayers) {
        StringBuilder header = new StringBuilder();
        switch (this) {
            case DIVING_TACKLE: {
                header.append("Select a player to use ").append(Skill.DIVING_TACKLE.getName());
                break;
            }
            case SHADOWING: {
                header.append("Select a player to use ").append(Skill.SHADOWING.getName());
                break;
            }
            case TENTACLES: {
                header.append("Select a player to use ").append(Skill.TENTACLES.getName());
                break;
            }
            case FEED: {
                header.append("Select a player to feed on");
                break;
            }
            case DIVING_CATCH: {
                header.append("Select a player to use ").append(Skill.DIVING_CATCH.getName());
                break;
            }
            case CARD: {
                header.append("Select a player to play this card on");
                break;
            }
            case BLOCK: {
                header.append("Select a player to block");
                break;
            }
            case MVP: {
                header.append("Nominate " + nrOfPlayers + " for the MVP");
                break;
            }
        }
        return header.toString();
    }

    public String getStatusTitle() {
        StringBuilder title = new StringBuilder();
        switch (this) {
            case DIVING_TACKLE: {
                title.append(Skill.DIVING_TACKLE.getName());
                break;
            }
            case SHADOWING: {
                title.append(Skill.SHADOWING.getName());
                break;
            }
            case TENTACLES: {
                title.append(Skill.TENTACLES.getName());
                break;
            }
            case FEED: {
                title.append("Feed on player");
                break;
            }
            case DIVING_CATCH: {
                title.append(Skill.DIVING_CATCH.getName());
                break;
            }
            case CARD: {
                title.append("Play Card");
                break;
            }
            case BLOCK: {
                title.append("Block");
                break;
            }
            case MVP: {
                title.append("MVP");
                break;
            }
        }
        return title.toString();
    }

    public String getStatusMessage() {
        StringBuilder message = new StringBuilder();
        switch (this) {
            case DIVING_TACKLE: {
                message.append("Waiting for coach to use ").append(Skill.DIVING_TACKLE.getName()).append(".");
                break;
            }
            case SHADOWING: {
                message.append("Waiting for coach to use ").append(Skill.SHADOWING.getName()).append(".");
                break;
            }
            case TENTACLES: {
                message.append("Waiting for coach to use ").append(Skill.TENTACLES.getName()).append(".");
                break;
            }
            case FEED: {
                message.append("Waiting for coach to choose player to feed on.");
                break;
            }
            case DIVING_CATCH: {
                message.append("Waiting for coach to use ").append(Skill.DIVING_CATCH.getName()).append(".");
                break;
            }
            case CARD: {
                message.append("Waiting for coach to play card on player.");
                break;
            }
            case BLOCK: {
                message.append("Waiting for coach to choose player to block.");
                break;
            }
            case MVP: {
                message.append("Waiting for coach to nominate players for the MVP.");
                break;
            }
        }
        return message.toString();
    }

}

