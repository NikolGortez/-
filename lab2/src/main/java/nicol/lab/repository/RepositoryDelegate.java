package nicol.lab.repository;

import nicol.lab.domain.product.Product;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface RepositoryDelegate {
    List<Product> read(String fileContents) throws IOException;
    boolean writeTo(Path pathToFile, List<Product> products) throws IOException;
}