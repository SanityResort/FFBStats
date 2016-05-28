/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.client.ui.ChatLogDocument;
import com.balancedbytes.games.ffb.client.ui.IReplayMouseListener;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Style;

public class ChatLogTextPane
extends JTextPane {
    private ChatLogDocument fChatLogDocument;
    private IReplayMouseListener fReplayMouseListener;

    public ChatLogTextPane() {
        this.setEditable(false);
        ((DefaultCaret)this.getCaret()).setUpdatePolicy(1);
        this.detachDocument();
        this.attachDocument();
    }

    public ChatLogDocument getChatLogDocument() {
        return this.fChatLogDocument;
    }

    public void addReplayMouseListener(IReplayMouseListener pReplayMouseListener) {
        this.fReplayMouseListener = pReplayMouseListener;
    }

    public void removeReplayMouseListener() {
        this.fReplayMouseListener = null;
    }

    @Override
    protected void processMouseEvent(MouseEvent pMouseEvent) {
        if (this.fReplayMouseListener != null) {
            if (501 == pMouseEvent.getID()) {
                int position = this.viewToModel(pMouseEvent.getPoint());
                this.fReplayMouseListener.mousePressedForReplay(position);
            }
        } else {
            super.processMouseEvent(pMouseEvent);
        }
    }

    public void detachDocument() {
        this.fChatLogDocument = new ChatLogDocument();
    }

    public void attachDocument() {
        this.setDocument(this.fChatLogDocument);
    }

    public void append(ParagraphStyle pTextIndent, TextStyle pStyle, String pText) {
        try {
            if (pText != null) {
                if (pStyle == null) {
                    pStyle = TextStyle.NONE;
                }
                if (pTextIndent == null) {
                    pTextIndent = ParagraphStyle.INDENT_0;
                }
                this.fChatLogDocument.setParagraphAttributes(this.fChatLogDocument.getLength(), 1, this.fChatLogDocument.getStyle(pTextIndent.getName()), false);
                this.fChatLogDocument.insertString(this.fChatLogDocument.getLength(), pText, this.fChatLogDocument.getStyle(pStyle.getName()));
            } else {
                this.fChatLogDocument.insertString(this.fChatLogDocument.getLength(), ChatLogDocument.LINE_SEPARATOR, this.fChatLogDocument.getStyle(TextStyle.NONE.getName()));
            }
        }
        catch (BadLocationException ex) {
            throw new FantasyFootballException(ex);
        }
    }
}

