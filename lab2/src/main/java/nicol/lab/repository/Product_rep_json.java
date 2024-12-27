package nicol.lab.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import nicol.lab.domain.product.Product;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class Product_rep_json implements RepositoryDelegate {
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Product> read(String fileContents) {
        Type type = TypeToken.getParameterized(List.class, Product.class).getType();
        return PRETTY_GSON.fromJson(fileContents, type);
    }

    @Override
    public boolean writeTo(Path pathToFile, List<Product> products) {
        try {
            Files.writeString(pathToFile, PRETTY_GSON.toJson(products));
            return true;
        } catch (IOException io) {
            log.error("Не удалось сохранить файл {}", pathToFile, io);
            return false;
        }
    }
}