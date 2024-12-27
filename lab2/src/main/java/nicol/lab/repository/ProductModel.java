package nicol.lab.repository;

import nicol.lab.domain.product.Product;

import java.util.List;

public class ProductModel {
    private final Product_rep_DB repository = new Product_rep_DB();

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void addProduct(Product product) {
        repository.insert(product);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}