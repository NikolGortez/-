package nicol.lab;

import nicol.lab.domain.product.Product;
import nicol.lab.repository.*;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ProductModel model = new ProductModel();
        ;
        MainView view = new MainView();
        new MainController(model, view);

        view.setVisible(true);

        SerializableRepository yamlRepo = new SerializableRepository(new Product_rep_yaml());
        yamlRepo.writeTo(Path.of("output.yaml"));

        SerializableRepository jsonRepo = new SerializableRepository(new Product_rep_json());
        jsonRepo.writeTo(Path.of("output.json"));

        Product_rep_DB dbRepo = new Product_rep_DB();

        int count = dbRepo.get_count();
        System.out.println(count);

        dbRepo.insert(new Product(1, "name", "description", 100, 100, 0));
        count = dbRepo.get_count();
        System.out.println(count);

        for (Product product : dbRepo.findAll()) {
            System.out.println(product);
            dbRepo.deleteById(product.id());
        }

        count = dbRepo.get_count();
        System.out.println(count);
    }
}
