/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.option.GameOptionBoolean;
import com.balancedbytes.games.ffb.option.GameOptionFactory;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;

public class GameOptions
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "options";
    private Map<GameOptionId, IGameOption> fOptionById;
    private transient Game fGame;
    private transient GameOptionFactory fGameOptionFactory;

    public GameOptions(Game pGame) {
        this.fGame = pGame;
        this.fOptionById = new HashMap<GameOptionId, IGameOption>();
        this.fGameOptionFactory = new GameOptionFactory();
    }

    public Game getGame() {
        return this.fGame;
    }

    public void addOption(IGameOption pOption) {
        if (pOption != null) {
            this.addOptionInternal(pOption);
            switch (pOption.getId()) {
                case PILING_ON_ARMOR_ONLY: {
                    GameOptionBoolean pilingOnInjuryOnly;
                    if (!((GameOptionBoolean)pOption).isEnabled() || !(pilingOnInjuryOnly = (GameOptionBoolean)this.getOptionWithDefault(GameOptionId.PILING_ON_INJURY_ONLY)).isEnabled()) break;
                    this.addOptionInternal(pilingOnInjuryOnly.setValue(false));
                    break;
                }
                case PILING_ON_INJURY_ONLY: {
                    GameOptionBoolean pilingOnArmorOnly;
                    if (!((GameOptionBoolean)pOption).isEnabled() || !(pilingOnArmorOnly = (GameOptionBoolean)this.getOptionWithDefault(GameOptionId.PILING_ON_ARMOR_ONLY)).isEnabled()) break;
                    this.addOptionInternal(pilingOnArmorOnly.setValue(false));
                    break;
                }
                case FOUL_BONUS: {
                    GameOptionBoolean foulBonusOutsideTacklezone;
                    if (!((GameOptionBoolean)pOption).isEnabled() || !(foulBonusOutsideTacklezone = (GameOptionBoolean)this.getOptionWithDefault(GameOptionId.FOUL_BONUS_OUTSIDE_TACKLEZONE)).isEnabled()) break;
                    this.addOptionInternal(foulBonusOutsideTacklezone.setValue(false));
                    break;
                }
                case FOUL_BONUS_OUTSIDE_TACKLEZONE: {
                    if (!((GameOptionBoolean)pOption).isEnabled()) break;
                    GameOptionBoolean foulBonus = (GameOptionBoolean)this.getOptionWithDefault(GameOptionId.FOUL_BONUS);
                    if (foulBonus.isEnabled()) {
                        this.addOptionInternal(foulBonus.setValue(false));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void addOptionInternal(IGameOption pOption) {
        this.fOptionById.put(pOption.getId(), pOption);
        this.notifyObservers(ModelChangeId.GAME_OPTIONS_ADD_OPTION, pOption);
    }

    public IGameOption getOption(GameOptionId pOptionId) {
        return this.fOptionById.get(pOptionId);
    }

    public IGameOption getOptionWithDefault(GameOptionId pOptionId) {
        IGameOption option = this.getOption(pOptionId);
        if (option == null) {
            option = this.fGameOptionFactory.createGameOption(pOptionId);
        }
        return option;
    }

    public IGameOption[] getOptions() {
        return this.fOptionById.values().toArray(new IGameOption[this.fOptionById.size()]);
    }

    public void init(GameOptions pOtherOptions) {
        if (pOtherOptions == null) {
            return;
        }
        for (IGameOption otherOption : pOtherOptions.getOptions()) {
            IGameOption myOption = this.fGameOptionFactory.createGameOption(otherOption.getId());
            myOption.setValue(otherOption.getValueAsString());
            this.addOption(myOption);
        }
    }

    private void notifyObservers(ModelChangeId pChangeId, Object pValue) {
        if (this.getGame() == null || pChangeId == null) {
            return;
        }
        this.getGame().notifyObservers(new ModelChange(pChangeId, null, pValue));
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        UtilXml.startElement(pHandler, "options");
        for (IGameOption option : this.getOptions()) {
            option.addToXml(pHandler);
        }
        UtilXml.endElement(pHandler, "options");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlSerializable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        if ("option".equals(pXmlTag)) {
            this.addOption(new GameOptionFactory().fromXmlElement(pXmlTag, pXmlAttributes));
        }
        return this;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "options".equals(pXmlTag);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        JsonArray optionArray = new JsonArray();
        for (IGameOption option : this.getOptions()) {
            optionArray.add(option.toJsonValue());
        }
        IJsonOption.GAME_OPTION_ARRAY.addTo(jsonObject, optionArray);
        return jsonObject;
    }

    @Override
    public GameOptions initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        JsonArray optionArray = IJsonOption.GAME_OPTION_ARRAY.getFrom(jsonObject);
        int nrOfOptions = optionArray.size();
        GameOptionFactory optionFactory = new GameOptionFactory();
        for (int i = 0; i < nrOfOptions; ++i) {
            IGameOption gameOption = optionFactory.fromJsonValue(optionArray.get(i));
            this.addOption(gameOption);
        }
        return this;
    }

}

