package com.in.Attendance;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
    CheckBoxRenderer() {
        setHorizontalAlignment(JCheckBox.CENTER);
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Do not need to explicitly set selected state in the renderer
            // The cell editor handles this
            fireEditingStopped();}
        });
    }


    protected void fireEditingStopped() {
    }


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelected(value != null && (boolean) value);
        return this;
    }
}
