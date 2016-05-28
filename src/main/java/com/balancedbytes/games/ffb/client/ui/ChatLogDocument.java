/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.client.ParagraphStyle;
import com.balancedbytes.games.ffb.client.TextStyle;
import java.awt.Color;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ChatLogDocument
extends DefaultStyledDocument {
    public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    public static final String DEFAULT_FONT_FAMILY = "Arial";
    public static final int DEFAULT_FONT_SIZE = 12;

    public ChatLogDocument() {
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle("default");
        StyleConstants.setFontFamily(defaultStyle, "Arial");
        this.addStyle(TextStyle.NONE.getName(), defaultStyle);
        Style bold = this.addStyle(TextStyle.BOLD.getName(), defaultStyle);
        StyleConstants.setFontFamily(bold, "Arial");
        StyleConstants.setFontSize(bold, 12);
        StyleConstants.setBold(bold, true);
        Style home = this.addStyle(TextStyle.HOME.getName(), defaultStyle);
        StyleConstants.setFontFamily(home, "Arial");
        StyleConstants.setFontSize(home, 12);
        StyleConstants.setForeground(home, Color.RED);
        Style homeBold = this.addStyle(TextStyle.HOME_BOLD.getName(), defaultStyle);
        StyleConstants.setFontFamily(homeBold, "Arial");
        StyleConstants.setFontSize(homeBold, 12);
        StyleConstants.setForeground(homeBold, Color.RED);
        StyleConstants.setBold(homeBold, true);
        Style away = this.addStyle(TextStyle.AWAY.getName(), defaultStyle);
        StyleConstants.setFontFamily(away, "Arial");
        StyleConstants.setFontSize(away, 12);
        StyleConstants.setForeground(away, Color.BLUE);
        Style awayBold = this.addStyle(TextStyle.AWAY_BOLD.getName(), defaultStyle);
        StyleConstants.setFontFamily(awayBold, "Arial");
        StyleConstants.setFontSize(awayBold, 12);
        StyleConstants.setForeground(awayBold, Color.BLUE);
        StyleConstants.setBold(awayBold, true);
        Style roll = this.addStyle(TextStyle.ROLL.getName(), defaultStyle);
        StyleConstants.setFontFamily(roll, "Arial");
        StyleConstants.setFontSize(roll, 12);
        StyleConstants.setBold(roll, true);
        Style neededRoll = this.addStyle(TextStyle.NEEDED_ROLL.getName(), defaultStyle);
        StyleConstants.setFontFamily(neededRoll, "Arial");
        StyleConstants.setFontSize(neededRoll, 12);
        StyleConstants.setForeground(neededRoll, Color.DARK_GRAY);
        Style explanation = this.addStyle(TextStyle.EXPLANATION.getName(), defaultStyle);
        StyleConstants.setItalic(explanation, true);
        StyleConstants.setFontFamily(explanation, "Arial");
        StyleConstants.setFontSize(explanation, 12);
        Style spectator = this.addStyle(TextStyle.SPECTATOR.getName(), defaultStyle);
        StyleConstants.setFontFamily(spectator, "Arial");
        StyleConstants.setFontSize(spectator, 12);
        StyleConstants.setForeground(spectator, new Color(0, 128, 0));
        Style turn = this.addStyle(TextStyle.TURN.getName(), defaultStyle);
        StyleConstants.setFontFamily(turn, "Arial");
        StyleConstants.setFontSize(turn, 14);
        StyleConstants.setBold(turn, true);
        Style turnHome = this.addStyle(TextStyle.TURN_HOME.getName(), defaultStyle);
        StyleConstants.setFontFamily(turnHome, "Arial");
        StyleConstants.setFontSize(turnHome, 14);
        StyleConstants.setBold(turnHome, true);
        StyleConstants.setForeground(turnHome, Color.RED);
        Style turnAway = this.addStyle(TextStyle.TURN_AWAY.getName(), defaultStyle);
        StyleConstants.setFontFamily(turnAway, "Arial");
        StyleConstants.setFontSize(turnAway, 14);
        StyleConstants.setBold(turnAway, true);
        StyleConstants.setForeground(turnAway, Color.BLUE);
        Style indent0 = this.addStyle(ParagraphStyle.INDENT_0.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent0, 0.0f);
        StyleConstants.setSpaceAbove(indent0, 0.0f);
        StyleConstants.setSpaceBelow(indent0, 0.0f);
        Style indent1 = this.addStyle(ParagraphStyle.INDENT_1.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent1, 12.0f);
        StyleConstants.setSpaceAbove(indent1, 0.0f);
        StyleConstants.setSpaceBelow(indent1, 0.0f);
        Style indent2 = this.addStyle(ParagraphStyle.INDENT_2.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent2, 24.0f);
        StyleConstants.setSpaceAbove(indent2, 0.0f);
        StyleConstants.setSpaceBelow(indent2, 0.0f);
        Style indent3 = this.addStyle(ParagraphStyle.INDENT_3.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent3, 36.0f);
        StyleConstants.setSpaceAbove(indent3, 0.0f);
        StyleConstants.setSpaceBelow(indent3, 0.0f);
        Style indent4 = this.addStyle(ParagraphStyle.INDENT_4.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent4, 48.0f);
        StyleConstants.setSpaceAbove(indent4, 0.0f);
        StyleConstants.setSpaceBelow(indent4, 0.0f);
        Style indent5 = this.addStyle(ParagraphStyle.INDENT_5.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent5, 60.0f);
        StyleConstants.setSpaceAbove(indent5, 0.0f);
        StyleConstants.setSpaceBelow(indent5, 0.0f);
        Style indent6 = this.addStyle(ParagraphStyle.INDENT_6.getName(), defaultStyle);
        StyleConstants.setLeftIndent(indent6, 72.0f);
        StyleConstants.setSpaceAbove(indent6, 0.0f);
        StyleConstants.setSpaceBelow(indent6, 0.0f);
        Style spaceAbove = this.addStyle(ParagraphStyle.SPACE_ABOVE.getName(), defaultStyle);
        StyleConstants.setLeftIndent(spaceAbove, 0.0f);
        StyleConstants.setSpaceAbove(spaceAbove, 4.0f);
        StyleConstants.setSpaceBelow(spaceAbove, 0.0f);
        Style spaceBelow = this.addStyle(ParagraphStyle.SPACE_BELOW.getName(), defaultStyle);
        StyleConstants.setLeftIndent(spaceBelow, 0.0f);
        StyleConstants.setSpaceAbove(spaceBelow, 0.0f);
        StyleConstants.setSpaceBelow(spaceBelow, 4.0f);
        Style spaceAboveBelow = this.addStyle(ParagraphStyle.SPACE_ABOVE_BELOW.getName(), defaultStyle);
        StyleConstants.setLeftIndent(spaceAboveBelow, 0.0f);
        StyleConstants.setSpaceAbove(spaceAboveBelow, 4.0f);
        StyleConstants.setSpaceBelow(spaceAboveBelow, 4.0f);
    }
}

