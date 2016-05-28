/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.client.ActionKeyBindings;
import com.balancedbytes.games.ffb.client.ActionKeyGroup;
import com.balancedbytes.games.ffb.client.ClientReplayer;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.ChatLogDocument;
import com.balancedbytes.games.ffb.client.ui.ChatLogScrollPane;
import com.balancedbytes.games.ffb.client.ui.ChatLogTextPane;
import com.balancedbytes.games.ffb.client.ui.CommandHighlightArea;
import com.balancedbytes.games.ffb.client.ui.CommandHighlighter;
import com.balancedbytes.games.ffb.client.ui.IReplayMouseListener;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Position;

public class LogComponent
extends JPanel
implements MouseMotionListener,
IReplayMouseListener {
    public static final int WIDTH = 389;
    public static final int HEIGHT = 226;
    private ChatLogScrollPane fLogScrollPane;
    private ChatLogTextPane fLogTextPane;
    private Map<Integer, CommandHighlightArea> fCommandHighlightAreaByCommandNr;
    private CommandHighlightArea fCurrentCommandHighlight;
    private int fMinimumCommandNr;
    private FantasyFootballClient fClient;

    public LogComponent(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fLogTextPane = new ChatLogTextPane();
        this.fLogScrollPane = new ChatLogScrollPane(this.fLogTextPane);
        this.getClient().getActionKeyBindings().addKeyBindings(this.fLogScrollPane, ActionKeyGroup.ALL);
        this.setLayout(new BorderLayout());
        this.add((Component)this.fLogScrollPane, "Center");
        Dimension size = new Dimension(389, 226);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fLogTextPane.setHighlighter(new CommandHighlighter());
        this.fLogTextPane.addMouseMotionListener(this);
        this.fLogScrollPane.addMouseMotionListener(this);
        this.fCommandHighlightAreaByCommandNr = new HashMap<Integer, CommandHighlightArea>();
    }

    public void append(ParagraphStyle pTextIndent, TextStyle pStyle, String pText) {
        this.fLogTextPane.append(pTextIndent, pStyle, pText);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public void markCommandBegin(int pCommandNr) {
        this.fCurrentCommandHighlight = this.fCommandHighlightAreaByCommandNr.get(pCommandNr);
        if (this.fCurrentCommandHighlight == null) {
            this.fCurrentCommandHighlight = new CommandHighlightArea(pCommandNr);
        }
        int logOffset = this.fLogTextPane.getChatLogDocument().getEndPosition().getOffset() - 1;
        this.fCurrentCommandHighlight.setStartPosition(logOffset);
    }

    public void markCommandEnd(int pCommandNr) {
        if (this.fCurrentCommandHighlight.getCommandNr() == pCommandNr) {
            if (this.fMinimumCommandNr > pCommandNr) {
                this.fMinimumCommandNr = pCommandNr;
            }
            int logOffset = this.fLogTextPane.getChatLogDocument().getEndPosition().getOffset() - 1;
            this.fCurrentCommandHighlight.setEndPosition(logOffset);
            this.fCommandHighlightAreaByCommandNr.put(this.fCurrentCommandHighlight.getCommandNr(), this.fCurrentCommandHighlight);
        }
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    public void detachLogDocument() {
        this.fLogTextPane.detachDocument();
        this.fCommandHighlightAreaByCommandNr.clear();
    }

    public void attachLogDocument() {
        this.fLogTextPane.attachDocument();
    }

    public boolean hasCommandHighlight(int pCommandNr) {
        CommandHighlightArea highlightArea = this.fCommandHighlightAreaByCommandNr.get(pCommandNr);
        return highlightArea != null && highlightArea.getEndPosition() - highlightArea.getStartPosition() > 0;
    }

    public boolean highlightCommand(int pCommandNr, boolean pShowEnd) {
        boolean highlightShown;
        CommandHighlightArea highlightArea = this.fCommandHighlightAreaByCommandNr.get(pCommandNr);
        boolean bl = highlightShown = highlightArea != null && highlightArea.getEndPosition() - highlightArea.getStartPosition() > 0;
        if (highlightShown) {
            try {
                ((CommandHighlighter)this.fLogTextPane.getHighlighter()).changeHighlight(highlightArea.getStartPosition(), highlightArea.getEndPosition());
                if (pShowEnd) {
                    this.fLogTextPane.setCaretPosition(highlightArea.getEndPosition());
                } else {
                    this.fLogTextPane.setCaretPosition(Math.max(highlightArea.getStartPosition() - 1, 0));
                }
            }
            catch (BadLocationException var5_5) {
                // empty catch block
            }
        }
        return highlightShown;
    }

    public int findCommandNr(int pPosition) {
        int commandNr = -1;
        CommandHighlightArea[] highlights = this.fCommandHighlightAreaByCommandNr.values().toArray(new CommandHighlightArea[this.fCommandHighlightAreaByCommandNr.size()]);
        for (int i = 0; i < highlights.length; ++i) {
            if (pPosition < highlights[i].getStartPosition() || pPosition > highlights[i].getEndPosition()) continue;
            commandNr = highlights[i].getCommandNr();
            break;
        }
        return commandNr;
    }

    public void hideHighlight() {
        try {
            ((CommandHighlighter)this.fLogTextPane.getHighlighter()).changeHighlight(0, 0);
        }
        catch (BadLocationException var1_1) {
            // empty catch block
        }
    }

    public int getMinimumCommandNr() {
        return this.fMinimumCommandNr;
    }

    @Override
    public void mousePressedForReplay(int pPosition) {
        ClientReplayer replayer = this.getClient().getReplayer();
        int commandNr = this.findCommandNr(pPosition);
        if (commandNr > 0) {
            replayer.replayToCommand(commandNr);
        }
    }

    public void enableReplay(boolean pEnabled) {
        if (pEnabled) {
            this.fLogTextPane.addReplayMouseListener(this);
        } else {
            this.fLogTextPane.removeReplayMouseListener();
        }
    }

    public ChatLogScrollPane getLogScrollPane() {
        return this.fLogScrollPane;
    }
}

