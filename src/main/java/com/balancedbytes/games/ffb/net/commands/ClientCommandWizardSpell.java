/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandWizardSpell
extends ClientCommand {
    private SpecialEffect fWizardSpell;
    private FieldCoordinate fTargetCoordinate;

    public ClientCommandWizardSpell() {
    }

    public ClientCommandWizardSpell(SpecialEffect pWizardSpell) {
        this.fWizardSpell = pWizardSpell;
    }

    public ClientCommandWizardSpell(SpecialEffect pWizardSpell, FieldCoordinate pTargetCoordinate) {
        this(pWizardSpell);
        this.fTargetCoordinate = pTargetCoordinate;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_WIZARD_SPELL;
    }

    public SpecialEffect getWizardSpell() {
        return this.fWizardSpell;
    }

    public FieldCoordinate getTargetCoordinate() {
        return this.fTargetCoordinate;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.WIZARD_SPELL.addTo(jsonObject, this.fWizardSpell);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandWizardSpell initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fWizardSpell = (SpecialEffect)IJsonOption.WIZARD_SPELL.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

