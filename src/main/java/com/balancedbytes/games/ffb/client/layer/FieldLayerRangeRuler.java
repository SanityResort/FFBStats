/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilPassing;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FieldLayerRangeRuler
extends FieldLayer {
    private static final Map<PassingDistance, Color> _COLOR_BY_PASSING_DISTANCE = new HashMap<PassingDistance, Color>();
    public static final Color COLOR_INTERCEPTION;
    public static final Color COLOR_THROWABLE_PLAYER;
    public static final Color COLOR_SELECT_SQUARE;
    private Polygon fPolygonComplete;
    private FieldCoordinate fSelectSquareCoordinate;
    private FieldCoordinate[] fMarkedCoordinates;

    public FieldLayerRangeRuler(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawRangeRuler(RangeRuler pRangeRuler) {
        this.removeRangeRuler();
        if (pRangeRuler != null && FieldCoordinateBounds.FIELD.isInBounds(pRangeRuler.getTargetCoordinate()) && StringTool.isProvided(pRangeRuler.getThrowerId())) {
            Game game = this.getClient().getGame();
            Player thrower = game.getPlayerById(pRangeRuler.getThrowerId());
            FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(thrower);
            PassingDistance passingDistance = UtilPassing.findPassingDistance(game, throwerCoordinate, pRangeRuler.getTargetCoordinate(), false);
            if (passingDistance != null) {
                Point startCenter = new Point(throwerCoordinate.getX() * 30 + 15, throwerCoordinate.getY() * 30 + 15);
                Point endCenter = new Point(pRangeRuler.getTargetCoordinate().getX() * 30 + 15, pRangeRuler.getTargetCoordinate().getY() * 30 + 15);
                int lengthY = startCenter.y - endCenter.y;
                int lengthX = endCenter.x - startCenter.x;
                double length = Math.sqrt(lengthY * lengthY + lengthX * lengthX);
                double sinPhi = (double)lengthY / length;
                double cosPhi = (double)lengthX / length;
                this.fPolygonComplete = this.findPolygon(startCenter, 0, (int)length, sinPhi, cosPhi);
                if (this.fPolygonComplete != null) {
                    Graphics2D g2d = this.getImage().createGraphics();
                    g2d.setPaint(_COLOR_BY_PASSING_DISTANCE.get(passingDistance));
                    g2d.fillPolygon(this.fPolygonComplete);
                    if (pRangeRuler.getMinimumRoll() > 0) {
                        g2d.transform(new AffineTransform(cosPhi, - sinPhi, sinPhi, cosPhi, (double)startCenter.x, (double)startCenter.y));
                        g2d.setFont(new Font("Sans Serif", 1, 32));
                        g2d.setComposite(AlphaComposite.getInstance(1, 0.0f));
                        this.drawRulerModifier(g2d, (int)length, 0, pRangeRuler.getMinimumRoll());
                    }
                    g2d.dispose();
                    Rectangle bounds = this.fPolygonComplete.getBounds();
                    this.addUpdatedArea(bounds);
                }
                Color selectSquareColor = null;
                Team otherTeam = game.isHomePlaying() ? game.getTeamAway() : game.getTeamHome();
                Player catcher = game.getFieldModel().getPlayer(pRangeRuler.getTargetCoordinate());
                selectSquareColor = catcher == null || otherTeam.hasPlayer(catcher) ? COLOR_SELECT_SQUARE : _COLOR_BY_PASSING_DISTANCE.get(passingDistance);
                if (selectSquareColor != null) {
                    this.fSelectSquareCoordinate = pRangeRuler.getTargetCoordinate();
                    this.clear(this.fSelectSquareCoordinate, true);
                    this.drawSelectSquare(this.fSelectSquareCoordinate, selectSquareColor);
                }
                if (!pRangeRuler.isThrowTeamMate()) {
                    this.markPlayers(UtilPassing.findInterceptors(game, thrower, this.fSelectSquareCoordinate), COLOR_INTERCEPTION);
                }
            }
        }
    }

    public void removeRangeRuler() {
        if (this.fPolygonComplete != null) {
            Rectangle oldBounds = this.fPolygonComplete.getBounds();
            this.clear(oldBounds.x, oldBounds.y, oldBounds.width, oldBounds.height, true);
        }
        this.fPolygonComplete = null;
        if (this.fMarkedCoordinates != null) {
            for (FieldCoordinate playerCoordinate : this.fMarkedCoordinates) {
                this.clear(playerCoordinate, true);
            }
            this.fMarkedCoordinates = null;
        }
        if (this.fSelectSquareCoordinate != null) {
            this.clear(this.fSelectSquareCoordinate, true);
        }
        this.fSelectSquareCoordinate = null;
    }

    public boolean isRulerShown() {
        return this.fPolygonComplete != null;
    }

    public boolean testCoordinateInsideRangeRuler(FieldCoordinate pCoordinate) {
        if (pCoordinate != null) {
            int x = pCoordinate.getX() * 30;
            int y = pCoordinate.getY() * 30;
            Rectangle playerSquare = new Rectangle(x, y, 30, 30);
            if (this.fPolygonComplete != null && this.fPolygonComplete.intersects(playerSquare)) {
                return true;
            }
        }
        return false;
    }

    private Polygon findPolygon(Point pStartCenter, int pMinLength, int pMaxLength, double pSinPhi, double pCosPhi) {
        if (pMaxLength > pMinLength) {
            int halfRulerWidth = (int)(30.0 * UtilPassing.RULER_WIDTH / 2.0);
            Point point1 = new Point(pStartCenter.x + pMinLength, pStartCenter.y - halfRulerWidth);
            point1 = this.rotate(point1, pStartCenter, pSinPhi, pCosPhi);
            Point point2 = new Point(pStartCenter.x + pMinLength, pStartCenter.y + halfRulerWidth);
            point2 = this.rotate(point2, pStartCenter, pSinPhi, pCosPhi);
            Point point3 = new Point(pStartCenter.x + pMaxLength, pStartCenter.y + halfRulerWidth);
            point3 = this.rotate(point3, pStartCenter, pSinPhi, pCosPhi);
            Point point4 = new Point(pStartCenter.x + pMaxLength, pStartCenter.y - halfRulerWidth);
            point4 = this.rotate(point4, pStartCenter, pSinPhi, pCosPhi);
            return new Polygon(new int[]{point1.x, point2.x, point3.x, point4.x}, new int[]{point1.y, point2.y, point3.y, point4.y}, 4);
        }
        return null;
    }

    private Point rotate(Point pPoint, Point pCenter, double pSinPhi, double pCosPhi) {
        int x = pPoint.x - pCenter.x;
        int y = pPoint.y - pCenter.y;
        return new Point((int)(pCosPhi * (double)x + pSinPhi * (double)y + pCenter.getX()), (int)((- pSinPhi) * (double)x + pCosPhi * (double)y + pCenter.getY()));
    }

    private void drawSelectSquare(FieldCoordinate pCoordinate, Color pColor) {
        if (pCoordinate != null && FieldCoordinateBounds.FIELD.isInBounds(pCoordinate)) {
            int x = pCoordinate.getX() * 30;
            int y = pCoordinate.getY() * 30;
            Rectangle bounds = new Rectangle(x, y, 30, 30);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setPaint(pColor);
            g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.dispose();
            this.addUpdatedArea(bounds);
        }
    }

    public void markCoordinates(FieldCoordinate[] pMarkedCoordinates, Color pColor) {
        this.fMarkedCoordinates = pMarkedCoordinates;
        if (ArrayTool.isProvided(pMarkedCoordinates) && pColor != null) {
            for (int i = 0; i < pMarkedCoordinates.length; ++i) {
                this.clear(this.fMarkedCoordinates[i], true);
                this.drawSelectSquare(this.fMarkedCoordinates[i], pColor);
            }
        }
    }

    public void markPlayers(Player[] pMarkedPlayers, Color pColor) {
        Game game = this.getClient().getGame();
        if (ArrayTool.isProvided(pMarkedPlayers) && pColor != null) {
            this.fMarkedCoordinates = new FieldCoordinate[pMarkedPlayers.length];
            for (int i = 0; i < pMarkedPlayers.length; ++i) {
                this.fMarkedCoordinates[i] = game.getFieldModel().getPlayerCoordinate(pMarkedPlayers[i]);
                this.clear(this.fMarkedCoordinates[i], true);
                this.drawSelectSquare(this.fMarkedCoordinates[i], pColor);
            }
        }
    }

    public void clearMarkedCoordinates() {
        if (ArrayTool.isProvided(this.fMarkedCoordinates)) {
            for (int i = 0; i < this.fMarkedCoordinates.length; ++i) {
                this.clear(this.fMarkedCoordinates[i], true);
            }
        }
    }

    private void drawRulerModifier(Graphics2D pG2d, int pTotalLength, int pPreviousSegmentLength, int pMinimumRoll) {
        String numberString = pMinimumRoll < 6 ? "" + pMinimumRoll + "+" : "6";
        FontMetrics metrics = pG2d.getFontMetrics();
        Rectangle2D numberBounds = metrics.getStringBounds(numberString, pG2d);
        int segmentLength = pTotalLength - pPreviousSegmentLength;
        if (numberBounds.getWidth() < (double)segmentLength) {
            int baselineX = pPreviousSegmentLength + (segmentLength - (int)numberBounds.getWidth()) / 2;
            int baselineY = (int)(numberBounds.getHeight() / 4.0) + 2;
            pG2d.drawString(numberString, baselineX, baselineY);
        }
    }

    @Override
    public void init() {
        this.clear(true);
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (fieldModel != null) {
            this.drawRangeRuler(fieldModel.getRangeRuler());
        }
    }

    public static Color getColorForPassingDistance(PassingDistance pPassingDistance) {
        return _COLOR_BY_PASSING_DISTANCE.get(pPassingDistance);
    }

    static {
        _COLOR_BY_PASSING_DISTANCE.put(PassingDistance.QUICK_PASS, new Color(0.0f, 1.0f, 0.0f, 0.3f));
        _COLOR_BY_PASSING_DISTANCE.put(PassingDistance.SHORT_PASS, new Color(1.0f, 1.0f, 0.0f, 0.3f));
        _COLOR_BY_PASSING_DISTANCE.put(PassingDistance.LONG_PASS, new Color(1.0f, 0.0f, 0.0f, 0.3f));
        _COLOR_BY_PASSING_DISTANCE.put(PassingDistance.LONG_BOMB, new Color(0.0f, 0.0f, 0.0f, 0.3f));
        COLOR_INTERCEPTION = new Color(1.0f, 1.0f, 1.0f, 0.3f);
        COLOR_THROWABLE_PLAYER = new Color(1.0f, 1.0f, 1.0f, 0.3f);
        COLOR_SELECT_SQUARE = new Color(0.0f, 0.0f, 1.0f, 0.2f);
    }
}

