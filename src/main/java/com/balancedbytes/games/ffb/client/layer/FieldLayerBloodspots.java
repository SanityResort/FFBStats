/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.BloodSpot;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class FieldLayerBloodspots
extends FieldLayer {
    public FieldLayerBloodspots(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawBloodspot(BloodSpot pBloodspot) {
        BufferedImage icon = this.getClient().getUserInterface().getIconCache().getIcon(pBloodspot);
        this.draw(icon, pBloodspot.getCoordinate(), 1.0f);
    }

    @Override
    public void init() {
        this.clear(true);
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (fieldModel != null) {
            BloodSpot[] bloodspots = fieldModel.getBloodSpots();
            for (int i = 0; i < bloodspots.length; ++i) {
                this.drawBloodspot(bloodspots[i]);
            }
        }
    }
}

