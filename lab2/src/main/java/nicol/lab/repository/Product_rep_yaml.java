package nicol.lab.repository;

import lombok.extern.slf4j.Slf4j;
import nicol.lab.domain.product.Product;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class Product_rep_yaml implements RepositoryDelegate {

    @Override
    public List<Product> read(String fileContents) throws IOException {
        ConfigurationNode root = YamlConfigurationLoader.builder().buildAndLoadString(fileContents);
        return root.getList(Product.class);
    }

    @Override
    public boolean writeTo(Path pathToFile, List<Product> products) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder().path(pathToFile).build();
            CommentedConfigurationNode root = loader.createNode();
            root.setList(Product.class, products);
            loader.save(root);
            return true;
        } catch (IOException io) {
            log.error("Не удалось сохранить файл {}", pathToFile, io);
            return false;
        }
    }
}