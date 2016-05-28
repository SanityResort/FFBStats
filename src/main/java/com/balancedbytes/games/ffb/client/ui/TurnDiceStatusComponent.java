/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogEndTurn;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.client.util.UtilClientGraphics;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.DateTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Date;
import javax.swing.JPanel;

public class TurnDiceStatusComponent
extends JPanel
implements MouseListener,
MouseMotionListener,
IDialogCloseListener {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 92;
    private static final String _LABEL_END_TURN = "End Turn";
    private static final String _LABEL_END_SETUP = "End Setup";
    private static final String _LABEL_CONTINUE = "Continue";
    private static final String _LABEL_KICKOFF = "Kick Off";
    private static final String _LABEL_TIMEOUT = "Timeout";
    private static final Font _BUTTON_FONT = new Font("Sans Serif", 1, 14);
    private static final Rectangle _BUTTON_AREA = new Rectangle(1, 1, 114, 31);
    private static final Font _DICE_FONT = new Font("Sans Serif", 1, 11);
    private static final Font _STATUS_TITLE_FONT = new Font("Sans Serif", 1, 12);
    private static final Font _STATUS_MESSAGE_FONT = new Font("Sans Serif", 0, 12);
    private static final int _STATUS_TEXT_WIDTH = 106;
    private SideBarComponent fSideBar;
    private BufferedImage fImage;
    private boolean fEndTurnButtonShown;
    private boolean fTimeoutButtonShown;
    private boolean fButtonSelected;
    private TurnMode fTurnMode;
    private boolean fHomePlaying;
    private boolean fTimeoutPossible;
    private boolean fTimeoutEnforced;
    private String fStatusTitle;
    private String fStatusMessage;
    private StatusType fStatusType;
    private boolean fWaitingForOpponent;
    private boolean fEndTurnButtonHidden;
    private Date fFinished;
    private int fNrOfBlockDice;
    private int[] fBlockRoll;
    private int fBlockDiceIndex;
    private boolean fRefreshNecessary;

    public TurnDiceStatusComponent(SideBarComponent pSideBar) {
        this.fSideBar = pSideBar;
        this.fImage = new BufferedImage(116, 92, 2);
        this.setLayout(null);
        Dimension size = new Dimension(116, 92);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.fRefreshNecessary = true;
    }

    public SideBarComponent getSideBar() {
        return this.fSideBar;
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
        BufferedImage background = this.getSideBar().isHomeSide() ? iconCache.getIconByProperty("sidebar.background.turn.dice.status.red") : iconCache.getIconByProperty("sidebar.background.turn.dice.status.blue");
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
    }

    private void drawEndTurnButton() {
        this.fEndTurnButtonShown = false;
        ClientData clientData = this.getSideBar().getClient().getClientData();
        if (this.fFinished == null && this.fTurnMode != null && this.fHomePlaying && !this.fWaitingForOpponent && !clientData.isEndTurnButtonHidden()) {
            switch (this.fTurnMode) {
                case START_GAME: {
                    break;
                }
                case SETUP: 
                case PERFECT_DEFENCE: 
                case QUICK_SNAP: 
                case HIGH_KICK: {
                    this.drawButton("End Setup");
                    this.fEndTurnButtonShown = true;
                    break;
                }
                case KICKOFF_RETURN: 
                case PASS_BLOCK: 
                case ILLEGAL_SUBSTITUTION: {
                    this.drawButton("Continue");
                    this.fEndTurnButtonShown = true;
                    break;
                }
                case KICKOFF: {
                    this.drawButton("Kick Off");
                    this.fEndTurnButtonShown = true;
                    break;
                }
                default: {
                    this.drawButton("End Turn");
                    this.fEndTurnButtonShown = true;
                }
            }
        }
    }

    private void drawTimeoutButton() {
        this.fTimeoutButtonShown = false;
        if (this.fFinished == null && this.fTurnMode != null && !this.fHomePlaying && this.fTimeoutPossible && !this.fTimeoutEnforced) {
            this.drawButton("Timeout");
            this.fTimeoutButtonShown = true;
        }
    }

    private void drawButton(String pButtonText) {
        if (pButtonText != null) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            BufferedImage buttonImage = iconCache.getIconByProperty(this.fButtonSelected ? "sidebar.turn.button.selected" : "sidebar.turn.button");
            g2d.drawImage(buttonImage, TurnDiceStatusComponent._BUTTON_AREA.x, TurnDiceStatusComponent._BUTTON_AREA.y, null);
            g2d.setFont(_BUTTON_FONT);
            g2d.setColor(Color.BLACK);
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D bounds = metrics.getStringBounds(pButtonText, g2d);
            int x = (116 - (int)bounds.getWidth()) / 2;
            int y = (TurnDiceStatusComponent._BUTTON_AREA.height + metrics.getHeight()) / 2 - metrics.getDescent();
            g2d.drawString(pButtonText, x, y);
            g2d.dispose();
        }
    }

    private void drawPlaying() {
        if (this.fTurnMode != null && this.fTurnMode != TurnMode.START_GAME && this.fFinished == null) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            BufferedImage playingImage = iconCache.getIconByProperty("sidebar.status.playing");
            g2d.drawImage(playingImage, TurnDiceStatusComponent._BUTTON_AREA.x, TurnDiceStatusComponent._BUTTON_AREA.y, null);
            g2d.dispose();
        }
    }

    private void drawStatus() {
        if (StringTool.isProvided(this.fStatusTitle) && StringTool.isProvided(this.fStatusMessage)) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            String imageProperty = null;
            switch (this.fStatusType) {
                case WAITING: {
                    imageProperty = "sidebar.status.waiting";
                    break;
                }
                case REF: {
                    imageProperty = "sidebar.status.ref";
                }
            }
            if (imageProperty != null) {
                BufferedImage statusImage = iconCache.getIconByProperty(imageProperty);
                g2d.drawImage(statusImage, 1, 1, null);
            }
            g2d.setColor(Color.BLACK);
            g2d.setFont(_STATUS_TITLE_FONT);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int x = 4;
            int y = fontMetrics.getHeight();
            g2d.drawString(this.fStatusTitle, x, y);
            g2d.setFont(_STATUS_MESSAGE_FONT);
            fontMetrics = g2d.getFontMetrics();
            y += fontMetrics.getHeight();
            AttributedString attStr = new AttributedString(this.fStatusMessage);
            attStr.addAttribute(TextAttribute.FONT, g2d.getFont());
            LineBreakMeasurer measurer = new LineBreakMeasurer(attStr.getIterator(), new FontRenderContext(null, false, false));
            TextLayout layoutLine = measurer.nextLayout(106.0f);
            while (layoutLine != null) {
                layoutLine.draw(g2d, x, y);
                if ((y += fontMetrics.getHeight()) <= 3 * fontMetrics.getHeight()) {
                    layoutLine = measurer.nextLayout(106.0f);
                    continue;
                }
                layoutLine = measurer.nextLayout(86.0f);
            }
            g2d.dispose();
        }
    }

    private void drawBlockDice() {
        if (ArrayTool.isProvided(this.fBlockRoll)) {
            int x = 0;
            int y = 38;
            Graphics2D g2d = this.fImage.createGraphics();
            Composite oldComposite = g2d.getComposite();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            for (int i = 0; i < this.fBlockRoll.length; ++i) {
                g2d.setComposite(oldComposite);
                BufferedImage diceIcon = iconCache.getDiceIcon(this.fBlockRoll[i]);
                if (this.fBlockDiceIndex >= 0 && this.fBlockDiceIndex != i) {
                    g2d.setComposite(AlphaComposite.getInstance(3, 0.5f));
                }
                x = this.fBlockRoll.length > 2 ? 1 + 39 * i : (this.fBlockRoll.length > 1 ? 20 + 39 * i : 39);
                g2d.drawImage(diceIcon, x, y, null);
            }
            if (this.fNrOfBlockDice < 0) {
                g2d.setFont(_DICE_FONT);
                g2d.setComposite(oldComposite);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                String opponentsChoice = "Opponent's choice";
                x = UtilClientGraphics.findCenteredX(g2d, opponentsChoice, 116);
                UtilClientGraphics.drawShadowedText(g2d, opponentsChoice, x, y += 38 + fontMetrics.getAscent());
            }
            g2d.dispose();
        }
    }

    public void init() {
        this.fStatusTitle = null;
        this.fStatusMessage = null;
        this.fTurnMode = null;
        this.fHomePlaying = false;
        this.fEndTurnButtonHidden = false;
        this.fBlockDiceIndex = -1;
        this.fNrOfBlockDice = 0;
        this.fBlockRoll = null;
        this.fWaitingForOpponent = false;
        this.fTimeoutPossible = false;
        this.fTimeoutEnforced = false;
        this.fRefreshNecessary = true;
        this.refresh();
    }

    public void refresh() {
        FantasyFootballClient client = this.getSideBar().getClient();
        Game game = client.getGame();
        ClientData clientData = client.getClientData();
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = !StringTool.isEqual(this.fStatusTitle, clientData.getStatusTitle()) || !StringTool.isEqual(this.fStatusMessage, clientData.getStatusMessage());
        }
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = this.fTurnMode == null || this.fTurnMode != game.getTurnMode() || this.fHomePlaying != game.isHomePlaying();
        }
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = this.fEndTurnButtonHidden != clientData.isEndTurnButtonHidden();
        }
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = this.fBlockDiceIndex != clientData.getBlockDiceIndex() || this.fNrOfBlockDice != clientData.getNrOfBlockDice() || !ArrayTool.isEqual(this.fBlockRoll, clientData.getBlockRoll());
        }
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = this.fWaitingForOpponent != game.isWaitingForOpponent() || this.fTimeoutPossible != game.isTimeoutPossible() || this.fTimeoutEnforced != game.isTimeoutEnforced() || !DateTool.isEqual(this.fFinished, game.getFinished());
        }
        if (this.fRefreshNecessary) {
            this.fButtonSelected = false;
            this.fTurnMode = game.getTurnMode();
            this.fHomePlaying = game.isHomePlaying();
            this.fNrOfBlockDice = clientData.getNrOfBlockDice();
            this.fBlockRoll = clientData.getBlockRoll();
            this.fBlockDiceIndex = clientData.getBlockDiceIndex();
            this.fStatusTitle = clientData.getStatusTitle();
            this.fStatusMessage = clientData.getStatusMessage();
            this.fStatusType = clientData.getStatusType();
            this.fTimeoutPossible = game.isTimeoutPossible();
            this.fTimeoutEnforced = game.isTimeoutEnforced();
            this.fWaitingForOpponent = game.isWaitingForOpponent();
            this.fFinished = game.getFinished();
            this.fEndTurnButtonHidden = clientData.isEndTurnButtonHidden();
            this.drawBackground();
            if (this.getSideBar().isHomeSide()) {
                if (ClientMode.PLAYER == client.getMode()) {
                    if (this.fHomePlaying) {
                        this.drawEndTurnButton();
                        this.drawBlockDice();
                    } else {
                        this.drawTimeoutButton();
                    }
                } else if (this.fHomePlaying) {
                    this.drawPlaying();
                    this.drawBlockDice();
                }
            } else if (StringTool.isProvided(this.fStatusTitle) && StringTool.isProvided(this.fStatusMessage)) {
                this.drawStatus();
            } else if (!this.fHomePlaying) {
                this.drawPlaying();
                this.drawBlockDice();
            }
            this.repaint();
            this.fRefreshNecessary = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
        this.mouseMoved(pMouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
        FantasyFootballClient client = this.getSideBar().getClient();
        Game game = client.getGame();
        UserInterface userInterface = client.getUserInterface();
        if ((this.fEndTurnButtonShown || this.fTimeoutButtonShown) && this.getSideBar().isHomeSide() && _BUTTON_AREA.contains(pMouseEvent.getPoint()) && userInterface.getDialogManager().isEndTurnAllowed()) {
            this.fButtonSelected = false;
            if (this.fHomePlaying) {
                if ((this.fTurnMode == TurnMode.REGULAR || this.fTurnMode == TurnMode.BLITZ) && UtilPlayer.testPlayersAbleToAct(game, game.getTeamHome())) {
                    DialogEndTurn endTurnDialog = new DialogEndTurn(this.getSideBar().getClient());
                    endTurnDialog.showDialog(this);
                } else {
                    client.getClientState().endTurn();
                }
            } else {
                client.getClientState().actionKeyPressed(ActionKey.TOOLBAR_ILLEGAL_PROCEDURE);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        if ((this.fEndTurnButtonShown || this.fTimeoutButtonShown) && !this.fButtonSelected && _BUTTON_AREA.contains(pMouseEvent.getPoint())) {
            this.fButtonSelected = true;
            if (this.fEndTurnButtonShown) {
                this.drawEndTurnButton();
            }
            if (this.fTimeoutButtonShown) {
                this.drawTimeoutButton();
            }
            this.repaint(_BUTTON_AREA);
        }
        if ((this.fEndTurnButtonShown || this.fTimeoutButtonShown) && this.fButtonSelected && !_BUTTON_AREA.contains(pMouseEvent.getPoint())) {
            this.fButtonSelected = false;
            if (this.fEndTurnButtonShown) {
                this.drawEndTurnButton();
            }
            if (this.fTimeoutButtonShown) {
                this.drawTimeoutButton();
            }
            this.repaint(_BUTTON_AREA);
        }
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
        if (DialogId.END_TURN == pDialog.getId()) {
            DialogEndTurn dialogEndTurn = (DialogEndTurn)pDialog;
            FantasyFootballClient client = this.getSideBar().getClient();
            if (dialogEndTurn.getChoice() == DialogEndTurn.YES) {
                client.getClientState().endTurn();
            }
        }
    }

}

