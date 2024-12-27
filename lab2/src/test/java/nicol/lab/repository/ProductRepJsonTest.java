package nicol.lab.repository;

import com.google.gson.Gson;
import nicol.lab.domain.product.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class ProductRepJsonTest {
    private static Gson gson;

    @BeforeAll
    static void setup() {
        gson = new Gson();
    }

    @AfterAll
    static void tearDown() {
        gson = null;
    }

    @MethodSource
    @ParameterizedTest
    void loadNewFrom(Product_rep_json productRepository, Path path, int expectedSize, int expectedId) {
        List<Product> products = productRepository.loadNewFrom(path);
        Assertions.assertEquals(expectedSize, products.size());
        Assertions.assertEquals(expectedSize, productRepository.get_count());
        Assertions.assertEquals(expectedId, productRepository.idCounter().get());
        Assertions.assertDoesNotThrow(() -> Files.deleteIfExists(path));
    }

    static Stream<Arguments> loadNewFrom() throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        List<Product> products = new ArrayList<>();
        int id;
        for (id = 0; id < 1000; id++) {
            products.add(new Product(id, "name", "description", ThreadLocalRandom.current().nextDouble(0, 1000), 1, 1));
        }
        Files.writeString(tempFile, gson.toJson(products));
        return Stream.of(Arguments.of(new Product_rep_json(), tempFile, products.size(), id));
    }
}
