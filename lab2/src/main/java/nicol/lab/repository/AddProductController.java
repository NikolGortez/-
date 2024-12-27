package nicol.lab.repository;

import nicol.lab.domain.product.Product;

import javax.swing.*;

public class AddProductController {
    private final ProductModel model;
    private final AddProductView view;

    public AddProductController(ProductModel model, AddProductView view) {
        this.model = model;
        this.view = view;

        view.addSaveButtonListener(e -> addProduct());
    }

    private void addProduct() {
        try {
            String name = view.getName();
            String description = view.getDescription();
            double price = view.getPrice();

            if (name.isEmpty() || description.isEmpty() || price <= 0) {
                throw new IllegalArgumentException("Неверные данные!");
            }

            Product product = new Product(0, name, description, price, 0, 0);
            model.addProduct(product);
            view.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ошибка: " + e.getMessage());
        }
    }
}