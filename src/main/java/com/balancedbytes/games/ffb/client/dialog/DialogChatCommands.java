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
import com.balancedbytes.games.ffb.model.Game;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;

public class DialogChatCommands
extends Dialog {
    private static final String _FONT_BOLD_OPEN = "<font face=\"Sans Serif\" size=\"-1\"><b>";
    private static final String _FONT_BOLD_CLOSE = "</b></font>";
    private static final String _FONT_MEDIUM_BOLD_OPEN = "<font face=\"Sans Serif\"><b>";
    private static final String _FONT_OPEN = "<font face=\"Sans Serif\" size=\"-1\">";
    private static final String _FONT_CLOSE = "</font>";

    public DialogChatCommands(FantasyFootballClient pClient) {
        super(pClient, "Chat Commands", true);
        JScrollPane aboutPane = new JScrollPane(this.createEditorPane());
        Game game = this.getClient().getGame();
        if (game.isTesting()) {
            aboutPane.setPreferredSize(new Dimension(aboutPane.getPreferredSize().width + 100, 500));
        } else {
            aboutPane.setPreferredSize(new Dimension(aboutPane.getPreferredSize().width + 10, 300));
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.add(aboutPane);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.CHAT_COMMANDS;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private JEditorPane createEditorPane() {
        JEditorPane aboutPane = new JEditorPane();
        aboutPane.setEditable(false);
        aboutPane.setContentType("text/html");
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<body>\n");
        html.append("<table border=\"0\" cellspacing=\"1\" width=\"100%\">\n");
        html.append("<tr><td>").append("<font face=\"Sans Serif\" size=\"-1\">");
        html.append("All commands can be given in the chat input field.<br><i>Spectator sounds are played with a 10 sec. enforced &quot;cooldown&quot; time between sounds.</i>");
        html.append("</font>").append("</td></tr>\n");
        html.append("</table>\n<br>\n");
        html.append("<table border=\"1\" cellspacing=\"0\" width=\"100%\">\n");
        html.append("<tr>\n");
        html.append("<td colspan=\"2\">").append("<font face=\"Sans Serif\"><b>").append("Spectator Commands").append("</b></font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/aah").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("aaahing spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/boo").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("booing spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/cheer").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("cheering spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/clap").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("clapping spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/crickets").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("the sound of crickets in the grass").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/laugh").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("laughing spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/ooh").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("ooohing spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/shock").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("shocked, gasping spectators").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/stomp").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("spectators stomping their feet").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("<tr>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/specs").append("</b></font>").append("</td>\n");
        html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("shows all logged in spectators by name").append("</font>").append("</td>\n");
        html.append("</tr>\n");
        html.append("</table>\n");
        if (this.getClient().getGame().isTesting()) {
            html.append("<br>\n<table border=\"1\" cellspacing=\"0\" width=\"100%\">\n");
            html.append("<tr>\n");
            html.append("<td colspan=\"2\">").append("<font face=\"Sans Serif\"><b>").append("Test Commands").append("</b></font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/animation &lt;name&gt &lt;x&gt; &lt;y&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("plays animation at the given coordinate.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/animations").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("lists all available animations.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/box &lt;box&gt &lt;playerlist&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("puts players on your team into a box (rsv, ko, bh, si, rip, ban).").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/card &lt;add|remove&gt; &lt;shortCardName&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("adds or removes card with given name to/from your inducements.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/injury &lt;injury&gt; &lt;playerlist&gt; ").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("gives players on your team an injury of that type (ni, -ma, -av, -ag or -st).").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/option &lt;name&gt; &lt;value&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("sets option with given name to given value.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/options").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("lists all available options with their current value.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/pitch &lt;name&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("changes the pitch (all weather conditions).").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/pitches").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("lists all available pitches.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/prone &lt;playerlist&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("places players on your team prone.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/roll &lt;roll1&gt; &lt;roll2&gt; &lt;roll3&gt; &lt;...&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("determines the next dicerolls (separated by space).").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/roll clear").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("removes all queued dicerolls from the RNG.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td valign=\"top\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/skill &lt;add|remove&gt; &lt;skillname&gt; &lt;playerlist&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("adds or removes a skill to players on your team.<br>skill names use underscores instead of blanks (diving_tackle, pass_block).").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/stat &lt;stat&gt; &lt;value&gt; &lt;playerlist&gt; ").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("sets a stat of players on your team to the given value.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/sound &lt;name&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("plays the given sound.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/sounds").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("lists all available sounds.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/stun &lt;playerlist&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("stuns players on your team.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/turn &lt;turnnr&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("jumps to the turn with the given number.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("/weather &lt;shortname&gt;").append("</b></font>").append("</td>\n");
            html.append("<td>").append("<font face=\"Sans Serif\" size=\"-1\">").append("changes the weather to nice, sunny, rain, heat or blizzard.").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("<tr>\n");
            html.append("<td colspan=\"2\">").append("<font face=\"Sans Serif\" size=\"-1\">").append("<i>Commands accepting a playerlist may either list player numbers separated by space or use the keyword &quot;all&quot; for all players.</i>").append("</font>").append("</td>\n");
            html.append("</tr>\n");
            html.append("</table>\n");
        }
        html.append("</body>\n");
        html.append("</html>");
        aboutPane.setText(html.toString());
        aboutPane.setCaretPosition(0);
        return aboutPane;
    }

    @Override
    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        Dimension menuBarSize = this.getClient().getUserInterface().getGameMenuBar().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (frameSize.height - dialogSize.height) / 2 - menuBarSize.height);
    }
}

