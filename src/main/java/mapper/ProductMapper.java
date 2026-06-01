package mapper;

import domain.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findAll();

    Product findById(long id);

    int insert(Product product);

    int update(Product product);

    int deleteById(long id);

    int countById(long id);
}
