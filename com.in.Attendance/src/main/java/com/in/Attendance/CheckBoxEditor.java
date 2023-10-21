package com.in.Attendance;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckBoxEditor extends DefaultCellEditor {
    private JCheckBox checkBox;

    CheckBoxEditor() {
        super(new JCheckBox());
        checkBox = (JCheckBox) getComponent();
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the state of the checkbox based on the provided value
        if (value instanceof Boolean) {
            checkBox.setSelected((Boolean) value);
        }
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected();
    }
}
