package nicol.lab.repository;

import nicol.lab.domain.product.Product;
import nicol.lab.domain.product.ProductShort;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class Product_rep_DB {
    public Product findById(int id) {
        try {
            return DbConnection.getInstance().findById(id);
        } catch (SQLException e) {
            System.err.println("Не удалось найти по id " + id);
            e.printStackTrace(System.err);
            return null;
        }
    }

    public List<ProductShort> get_k_n_short_list(int k, int n) {
        try {
            return DbConnection.getInstance().get_k_n_short_list(k, n);
        } catch (SQLException e) {
            System.err.println("Не удалось найти по k: " + k + ", n : " + n);
            e.printStackTrace(System.err);
            return Collections.emptyList();
        }
    }

    public void insert(Product product) {
        try {
            DbConnection.getInstance().insert(product);
        } catch (SQLException e) {
            System.err.println("Не удалось добавить " + product);
            e.printStackTrace(System.err);
        }
    }

    public int update(Product product) {
        try {
            return DbConnection.getInstance().update(product);
        } catch (SQLException e) {
            System.err.println("Не удалось обновить " + product);
            e.printStackTrace(System.err);
            return -1;
        }
    }

    public int deleteById(int id) {
        try {
            return DbConnection.getInstance().deleteById(id);
        } catch (SQLException e) {
            System.err.println("Не удалось удалить по id " + id);
            e.printStackTrace(System.err);
            return -1;
        }
    }

    public int get_count() {
        try {
            return DbConnection.getInstance().get_count();
        } catch (SQLException e) {
            System.err.println("Не удалось получить кол-во записей в БД");
            e.printStackTrace(System.err);
            return -1;
        }
    }

    public List<Product> findAll() {
        try {
            return DbConnection.getInstance().findAll();
        } catch (SQLException e) {
            System.err.println("Не удалось найти все записи в бд");
            e.printStackTrace(System.err);
            return Collections.emptyList();
        }
    }

    public List<Product> findAllSortedByPrice() {
        try {
            return DbConnection.getInstance().findAllSortedByPrice();
        } catch (SQLException e) {
            System.err.println("Не удалось найти все записи в бд, отсортированные по цене");
            e.printStackTrace(System.err);
            return Collections.emptyList();
        }
    }
}
