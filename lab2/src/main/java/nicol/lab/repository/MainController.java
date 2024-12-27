package nicol.lab.repository;

import nicol.lab.domain.product.Product;

import javax.swing.*;

public class MainController {
    private final ProductModel model;
    private final MainView view;

    public MainController(ProductModel model, MainView view) {
        this.model = model;
        this.view = view;

        // Обновление таблицы при запуске
        refreshTableData();

        // Обработка событий
        view.addAddButtonListener(e -> openAddProductWindow());
    }

    private void refreshTableData() {
        var products = model.getAllProducts();
        Object[][] data = products.stream()
                .map(p -> new Object[]{p.id(), p.name(), p.price()})
                .toArray(Object[][]::new);
        view.setTableData(data);
    }

    private void openAddProductWindow() {
        AddProductView addView = new AddProductView(view);
        addView.addSaveButtonListener(e -> {
            try {
                String name = addView.getName();
                double price = addView.getPrice();

                if (name.isEmpty() || price <= 0) {
                    throw new IllegalArgumentException("Неверные данные!");
                }

                model.addProduct(new Product(0, name, "", price, 0, 0));
                addView.dispose();
                refreshTableData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Ошибка: " + ex.getMessage());
            }
        });
        addView.setVisible(true);
    }

    private void deleteSelectedProduct() {
        int id = view.getSelectedProductId();
        if (id >= 0) {
            int confirm = JOptionPane.showConfirmDialog(view, "Удалить продукт с ID " + id + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                model.deleteProduct(id);
                refreshTableData();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Выберите продукт для удаления.");
        }
    }
}