/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum InducementType implements INamedObject
{
    BLOODWEISER_BABES("bloodweiserBabes", "Bloodweiser Babes", "Bloodweiser Babe", "Bloodweiser Babes"),
    BRIBES("bribes", "Bribes", "Bribe", "Bribes"),
    EXTRA_TEAM_TRAINING("extraTeamTraining", "Extra Training", "Extra Team Training", "Extra Team Trainings"),
    MASTER_CHEF("halflingMasterChef", "Halfling Master Chef", "Halfling Master Chef", "Halfling Master Chefs"),
    IGOR("igor", "Igor", "Igor", "Igors"),
    WANDERING_APOTHECARIES("wanderingApothecaries", "Wandering Apo.", "Wandering Apothecary", "Wandering Apothecaries"),
    WIZARD("wizard", "Wizard", "Wizard", "Wizards"),
    STAR_PLAYERS("starPlayers", "Star Players", "Star Player", "Star Players"),
    MERCENARIES("mercenaries", "Mercenaries", "Mercenary", "Mercenaries"),
    CARD("card", null, null, null);
    
    private String fName;
    private String fDescription;
    private String fSingular;
    private String fPlural;

    private InducementType(String pName, String pDescription, String pSingular, String pPlural) {
        this.fName = pName;
        this.fDescription = pDescription;
        this.fSingular = pSingular;
        this.fPlural = pPlural;
    }

    public String getDescription() {
        return this.fDescription;
    }

    public String getSingular() {
        return this.fSingular;
    }

    public String getPlural() {
        return this.fPlural;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

