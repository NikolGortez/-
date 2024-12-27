package nicol.lab.repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddProductView extends JFrame {
    private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JTextField priceField = new JTextField(10);
    private final JButton saveButton = new JButton("Сохранить");

    public AddProductView() {
        setTitle("Добавить продукт");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Название:"));
        add(nameField);
        add(new JLabel("Описание:"));
        add(descriptionField);
        add(new JLabel("Цена:"));
        add(priceField);
        add(saveButton);
    }

    public AddProductView(MainView view) {
        setTitle("Добавить продукт");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Название:"));
        add(nameField);
        add(new JLabel("Описание:"));
        add(descriptionField);
        add(new JLabel("Цена:"));
        add(priceField);
        add(saveButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public double getPrice() {
        return Double.parseDouble(priceField.getText());
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}