/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;

public class DialogKeyBindings
extends Dialog {
    private static final String _FONT_BOLD_OPEN = "<font face=\"Sans Serif\" size=\"-1\"><b>";
    private static final String _FONT_BOLD_CLOSE = "</b></font>";
    private static final String _FONT_MEDIUM_BOLD_OPEN = "<font face=\"Sans Serif\"><b>";
    private static final String _FONT_OPEN = "<font face=\"Sans Serif\" size=\"-1\">";
    private static final String _FONT_CLOSE = "</font>";

    public DialogKeyBindings(FantasyFootballClient pClient) {
        super(pClient, "Key Bindings", true);
        JScrollPane keyBindingsPane = new JScrollPane(this.createKeyBindingsEditorPane());
        keyBindingsPane.setPreferredSize(new Dimension(keyBindingsPane.getPreferredSize().width + 20, 500));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.add(keyBindingsPane);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.KEY_BINDINGS;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private JEditorPane createKeyBindingsEditorPane() {
        JEditorPane keyBindingsPane = new JEditorPane();
        keyBindingsPane.setEditable(false);
        keyBindingsPane.setContentType("text/html");
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<body>\n");
        html.append("<table border=\"1\" cellspacing=\"0\">\n");
        html.append("<tr>\n");
        html.append("  <td colspan=\"3\">").append("<font face=\"Sans Serif\"><b>").append("Player Moves").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move North").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.north")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 8").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move Northeast").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.northeast")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 9").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move East").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.east")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 6").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move Southeast").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.southeast")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 3").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move South").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.south")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 2").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move Southwest").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.southwest")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 1").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move West").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.west")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 4").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Move Northwest").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.move.northwest")).append("</b></font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("NUMPAD 7").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("<br>\n");
        html.append("<table border=\"1\" cellspacing=\"0\">\n");
        html.append("<tr>\n");
        html.append("  <td colspan=\"2\">").append("<font face=\"Sans Serif\"><b>").append("Player Selection").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Select Current Player").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.select")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Selection Cycle Left").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.cycle.left")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Selection Cycle Right").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.cycle.right")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("<br>\n");
        html.append("<table border=\"1\" cellspacing=\"0\">\n");
        html.append("<tr>\n");
        html.append("  <td colspan=\"2\">").append("<font face=\"Sans Serif\"><b>").append("Player Actions").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Block").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.block")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Blitz").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.blitz")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Move").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.move")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Foul").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.foul")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Stand Up").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.standup")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Hand Over").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.handover")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Pass").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.pass")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Stab").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.stab")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action Gaze").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.gaze")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Action End Move").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.player.action.endMove")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("<br>\n");
        html.append("<table border=\"1\" cellspacing=\"0\">\n");
        html.append("<tr>\n");
        html.append("  <td colspan=\"2\">").append("<font face=\"Sans Serif\"><b>").append("Toolbar &amp; Menu Shortcuts").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("End Turn").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.toolbar.turn.end")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Load Team Setup").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.menu.setup.load")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("Save Team Setup").append("</font>").append("</td>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(this.getClient().getProperty("key.menu.setup.save")).append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("<br>\n");
        html.append("<table border=\"1\" cellspacing=\"0\">\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\"><b>").append("Dialogs").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("  <td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("In all dialogs buttons can be activated by the first<br>letter of their label. So &lt;Y&gt; for Yes or &lt;N&gt; for No.<br>Block dices are numbered 1, 2, 3 from left to right<br>and can be activated this way.").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        html.append("</body>\n");
        html.append("</html>");
        keyBindingsPane.setText(html.toString());
        keyBindingsPane.setCaretPosition(0);
        return keyBindingsPane;
    }

    @Override
    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        Dimension menuBarSize = this.getClient().getUserInterface().getGameMenuBar().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (frameSize.height - dialogSize.height) / 2 - menuBarSize.height);
    }
}

