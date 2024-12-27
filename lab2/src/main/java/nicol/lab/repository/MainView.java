package nicol.lab.repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private final JTable table = new JTable();
    private final JButton addButton = new JButton("+ Добавить");
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Название", "Цена"}, 0);

    public MainView() {
        setTitle("Управление товарами");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Таблица
        table.setModel(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    public void setTableData(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public int getSelectedProductId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    /*public void addDeleteButtonListener(ActionListener listener) {
        JButton deleteButton = new JButton("-");
        deleteButton.addActionListener(listener);
    }*/
}