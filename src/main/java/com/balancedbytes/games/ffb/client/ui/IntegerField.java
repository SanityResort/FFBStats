/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IntegerField
extends JTextField {
    public IntegerField() {
    }

    public IntegerField(int cols) {
        super(cols);
    }

    public int getInt() {
        String text = this.getText();
        if (text == null || text.length() == 0) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    public void setInt(int value) {
        this.setText(String.valueOf(value));
    }

    @Override
    protected Document createDefaultModel() {
        return new IntegerDocument();
    }

    static class IntegerDocument
    extends PlainDocument {
        IntegerDocument() {
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str != null) {
                try {
                    Integer.decode(str);
                    super.insertString(offs, str, a);
                }
                catch (NumberFormatException ex) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

}

