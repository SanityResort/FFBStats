/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.client.ActionKeyBindings;
import com.balancedbytes.games.ffb.client.ActionKeyGroup;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.ReplayControl;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.ui.ChatLogScrollPane;
import com.balancedbytes.games.ffb.client.ui.ChatLogTextPane;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatComponent
extends JPanel
implements MouseMotionListener {
    public static final int WIDTH = 389;
    public static final int HEIGHT = 226;
    private static final int _MAX_CHAT_LENGTH = 512;
    private static final int _MAX_INPUT_LOG_SIZE = 100;
    private ChatLogScrollPane fChatScrollPane;
    private ChatLogTextPane fChatTextPane;
    private JTextField fChatInputField;
    private ReplayControl fReplayControl;
    private boolean fReplayShown;
    private List<String> fInputLog;
    private int fInputLogPosition;
    private FantasyFootballClient fClient;

    public ChatComponent(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fInputLog = new LinkedList<String>();
        this.fInputLogPosition = -1;
        this.fChatTextPane = new ChatLogTextPane();
        this.fChatScrollPane = new ChatLogScrollPane(this.fChatTextPane);
        this.getClient().getActionKeyBindings().addKeyBindings(this.fChatScrollPane, ActionKeyGroup.ALL);
        this.fChatInputField = new JTextField(35);
        this.getClient().getActionKeyBindings().addKeyBindings(this.fChatInputField, ActionKeyGroup.PLAYER_ACTIONS);
        this.getClient().getActionKeyBindings().addKeyBindings(this.fChatInputField, ActionKeyGroup.TURN_ACTIONS);
        this.fChatInputField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String talk = ChatComponent.this.fChatInputField.getText();
                if (talk != null) {
                    if ((talk = talk.trim()).length() > 512) {
                        talk = talk.substring(0, 512);
                    }
                    if (talk.length() > 0) {
                        ChatComponent.this.getClient().getCommunication().sendTalk(talk);
                        ChatComponent.this.fInputLog.add(talk);
                        if (ChatComponent.this.fInputLog.size() > 100) {
                            ChatComponent.this.fInputLog.remove(0);
                        }
                        ChatComponent.this.fInputLogPosition = ChatComponent.this.fInputLog.size();
                    }
                }
                ChatComponent.this.fChatInputField.setText("");
            }
        });
        this.fChatInputField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent pKeyEvent) {
                super.keyReleased(pKeyEvent);
                if (pKeyEvent.getKeyCode() == 40) {
                    if (ChatComponent.this.fInputLogPosition > 0) {
                        ChatComponent.this.fInputLogPosition--;
                    }
                    if (ChatComponent.this.fInputLogPosition >= 0 && ChatComponent.this.fInputLogPosition < ChatComponent.this.fInputLog.size()) {
                        ChatComponent.this.fChatInputField.setText((String)ChatComponent.this.fInputLog.get(ChatComponent.this.fInputLogPosition));
                    }
                }
                if (pKeyEvent.getKeyCode() == 38) {
                    if (ChatComponent.this.fInputLogPosition < ChatComponent.this.fInputLog.size() - 1) {
                        ChatComponent.this.fInputLogPosition++;
                    }
                    if (ChatComponent.this.fInputLogPosition >= 0 && ChatComponent.this.fInputLogPosition < ChatComponent.this.fInputLog.size()) {
                        ChatComponent.this.fChatInputField.setText((String)ChatComponent.this.fInputLog.get(ChatComponent.this.fInputLogPosition));
                    }
                }
            }
        });
        this.setLayout(new BorderLayout(0, 1));
        this.add((Component)this.fChatScrollPane, "Center");
        this.add((Component)this.fChatInputField, "South");
        Dimension size = new Dimension(389, 226);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fChatTextPane.addMouseMotionListener(this);
        this.fChatScrollPane.addMouseMotionListener(this);
        this.fChatInputField.addMouseMotionListener(this);
        this.fReplayShown = false;
        this.fReplayControl = new ReplayControl(this.getClient());
    }

    public void append(ParagraphStyle pTextIndent, TextStyle pStyle, String pText) {
        this.fChatTextPane.append(pTextIndent, pStyle, pText);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    public void showReplay(boolean pShowReplay) {
        this.removeAll();
        if (pShowReplay) {
            this.add((Component)this.fReplayControl, "North");
        }
        this.add((Component)this.fChatScrollPane, "Center");
        this.add((Component)this.fChatInputField, "South");
        this.revalidate();
        this.repaint();
        this.fReplayShown = pShowReplay;
    }

    public boolean isReplayShown() {
        return this.fReplayShown;
    }

    public ReplayControl getReplayControl() {
        return this.fReplayControl;
    }

    public boolean hasChatInputFocus() {
        return this.fChatInputField.hasFocus();
    }

    public void requestChatInputFocus() {
        this.fChatInputField.requestFocus();
    }

}

