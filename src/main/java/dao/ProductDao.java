package dao;

import common.config.MyBatisConfig;
import domain.Product;
import mapper.ProductMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProductDao {

    public List<Product> findAll() {
        try (SqlSession session = MyBatisConfig.openSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.findAll();
        }
    }

    public Product findById(long id) {
        try (SqlSession session = MyBatisConfig.openSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.findById(id);
        }
    }

    public Product save(Product product) {
        try (SqlSession session = MyBatisConfig.openSession(false)) {
            try {
                ProductMapper mapper = session.getMapper(ProductMapper.class);
                mapper.insert(product);
                session.commit();

                return product;
            } catch (RuntimeException e) {
                session.rollback();
                throw e;
            }
        }
    }

    public int update(Product product) {
        try (SqlSession session = MyBatisConfig.openSession(false)) {
            try {
                ProductMapper mapper = session.getMapper(ProductMapper.class);
                int affectedRows = mapper.update(product);
                session.commit();

                return affectedRows;
            } catch (RuntimeException e) {
                session.rollback();
                throw e;
            }
        }
    }

    public int deleteById(long id) {
        try (SqlSession session = MyBatisConfig.openSession(false)) {
            try {
                ProductMapper mapper = session.getMapper(ProductMapper.class);
                int affectedRows = mapper.deleteById(id);
                session.commit();

                return affectedRows;
            } catch (RuntimeException e) {
                session.rollback();
                throw e;
            }
        }
    }

    public boolean existsById(long id) {
        try (SqlSession session = MyBatisConfig.openSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.countById(id) > 0;
        }
    }
}
