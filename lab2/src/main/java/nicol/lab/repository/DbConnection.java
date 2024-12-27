package nicol.lab.repository;

import nicol.lab.domain.product.Product;
import nicol.lab.domain.product.ProductShort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class DbConnection {
    private static final class InstanceHolder {
        private static final DbConnection SINGLETON = new DbConnection();
    }

    public static DbConnection getInstance() {
        return InstanceHolder.SINGLETON;
    }

    private final Connection connection;

    private DbConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:mem:product");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS product("
                    + "id int PRIMARY KEY AUTO_INCREMENT,"
                    + " name VARCHAR,"
                    + " description VARCHAR,"
                    + " price DOUBLE,"
                    + " stock_balance int,"
                    + " reserve_balance int)");
        } catch (SQLException sqlException) {
            throw new RuntimeException("Не удалось создать подключение к in-memory H2", sqlException);
        }
    }

    public Product findById(int id) throws SQLException {
        try (PreparedStatement selectById = connection.prepareStatement("SELECT * FROM product WHERE id = ?")) {
            selectById.setInt(1, id);
            ResultSet resultSet = selectById.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_balance"),
                        resultSet.getInt("reserve_balance")
                );
            }
            return null;
        }
    }

    public List<ProductShort> get_k_n_short_list(int k, int n) throws SQLException {
        try (PreparedStatement selectRanged = connection.prepareStatement("SELECT id, name FROM product WHERE id >= ? AND id < ?")) {
            selectRanged.setInt(1, (k - 1) * n);
            selectRanged.setInt(2, (k * n) - 1);
            ResultSet resultSet = selectRanged.executeQuery();
            List<ProductShort> products = new LinkedList<>();
            while (resultSet.next()) {
                products.add(new ProductShort(resultSet.getInt("id"), resultSet.getString("name")));
            }
            return List.copyOf(products);
        }
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement("INSERT INTO product (name, description, price, stock_balance, reserve_balance) VALUES (?, ?, ?, ?, ?)")) {
            insert.setString(1, product.name());
            insert.setString(2, product.description());
            insert.setDouble(3, product.price());
            insert.setInt(4, product.stockBalance());
            insert.setInt(5, product.reserveBalance());
            insert.execute();
        }
    }

    public int update(Product product) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement("UPDATE product SET name = ?, description = ?, price = ?, reserve_balance = ?, stock_balance = ? WHERE id = ?")) {
            update.setString(1, product.name());
            update.setString(2, product.description());
            update.setDouble(3, product.price());
            update.setInt(4, product.stockBalance());
            update.setInt(5, product.reserveBalance());
            update.setInt(6, product.id());
            return update.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        try (PreparedStatement delete = connection.prepareStatement("DELETE FROM product WHERE id = ?")) {
            delete.setInt(1, id);
            return delete.executeUpdate();
        }
    }

    public int get_count() throws SQLException {
        try (PreparedStatement select = connection.prepareStatement("SELECT COUNT(*) FROM product")) {
            ResultSet resultSet = select.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : -1;
        }
    }

    public List<Product> findAll() throws SQLException {
        try (PreparedStatement select = connection.prepareStatement("SELECT * FROM product")) {
            return List.copyOf(selectProducts(select));
        }
    }

    public List<Product> findAllSortedByPrice() throws SQLException {
        try (PreparedStatement select = connection.prepareStatement("SELECT * FROM product ORDER BY price")) {
            return List.copyOf(selectProducts(select));
        }
    }

    private List<Product> selectProducts(PreparedStatement select) throws SQLException {
        ResultSet resultSet = select.executeQuery();
        List<Product> products = new LinkedList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stock_balance"),
                    resultSet.getInt("reserve_balance")
            ));
        }
        return products;
    }
}
