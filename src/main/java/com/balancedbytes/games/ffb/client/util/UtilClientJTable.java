/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.util;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class UtilClientJTable {
    public static void packTableColumn(JTable pTable, int pColIndex, int pMargin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel)pTable.getColumnModel();
        TableColumn col = colModel.getColumn(pColIndex);
        int width = 0;
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = pTable.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(pTable, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
        for (int row = 0; row < pTable.getRowCount(); ++row) {
            renderer = pTable.getCellRenderer(row, pColIndex);
            comp = renderer.getTableCellRendererComponent(pTable, pTable.getValueAt(row, pColIndex), false, false, row, pColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        col.setPreferredWidth(width += 2 * pMargin);
        col.setMinWidth(width);
        col.setMaxWidth(width);
    }
}

