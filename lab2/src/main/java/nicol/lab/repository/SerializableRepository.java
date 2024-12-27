package nicol.lab.repository;

import lombok.extern.slf4j.Slf4j;
import nicol.lab.domain.product.Product;
import nicol.lab.domain.product.ProductShort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SerializableRepository {
    private final RepositoryDelegate delegate;
    protected int idCounter = 0;
    protected final List<Product> products = new ArrayList<>();

    public SerializableRepository(RepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    public int idCounter() {
        return idCounter;
    }

    public final List<Product> loadNewFrom(Path pathToFile) {
        try {
            String fileContents = Files.readString(pathToFile, StandardCharsets.UTF_8);
            List<Product> newProducts = delegate.read(fileContents);
            products.clear();
            products.addAll(newProducts);
            int maxId = newProducts.stream()
                    .mapToInt(Product::id)
                    .max()
                    .orElse(0);
            idCounter = maxId + 1;
            return products;
        } catch (IOException e) {
            log.error("Не удалось прочитать файл {}", pathToFile, e);
            return List.of();
        }
    }

     public boolean writeTo(Path pathToFile) {
        try {
            return delegate.writeTo(pathToFile, products);
        } catch (IOException e) {
            log.error("Не удалось сохранить файл {}", pathToFile, e);
            return false;
        }
    }

    public final Optional<Product> findProductById(int id) {
        return products.stream().filter(p -> p.id() == id).findFirst();
    }

    public final List<ProductShort> get_k_n_short_list(int k, int n) throws IndexOutOfBoundsException {
        int startIndex = (k - 1) * n;
        int endIndex = k * n;
        if (startIndex < 0 || endIndex > products.size()) {
            throw new IndexOutOfBoundsException("Invalid range for k and n");
        }
        List<ProductShort> result = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            Product product = products.get(i);
            result.add(new ProductShort(product.id(), product.description()));
        }
        return result;
    }

    public final List<Product> sortByPrice() {
        products.sort(Comparator.comparingDouble(Product::price).reversed());
        return products;
    }

    public final Product addAndGenerateId(Product product) {
        Product productWithId = new Product(
                idCounter,
                product.name(),
                product.description(),
                product.price(),
                product.stockBalance(),
                product.reserveBalance()
        );
        idCounter++;
        products.add(productWithId);
        return productWithId;
    }

    public boolean replaceById(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id() == product.id()) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public Product removeById(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id() == id) {
                return products.remove(i);
            }
        }
        return null;
    }
}
