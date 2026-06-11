package service;

import dto.ProductDto;
import org.json.simple.JSONObject;
import repository.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerService {

    private final ProductRepository repository = new ProductRepository();
    static final String INVALID_PRODUCT_NUMBER_MESSAGE = "유효하지 않은 상품 번호입니다.";

    public List<String> getProductList() {
        List<String> result = new ArrayList<>();
        List<ProductDto> all = repository.findAll();
        for (ProductDto product : all) {
            result.add(product.toString());
        }
        return result;
    }

    /**
     * Multi Thread 실행 시,
     * 여러 thread 에서 동시에 접근이 가능하므로,
     * 의도치 않게 변수의 값이 변경되는 것을 방지하기 위해
     * `synchronized` 키워드를 통해 동기화 처리합니다.
     * <p>
     * 이를 통해 해당 메서드가 실행 중일 때, 다른 thread가
     * 동일한 메서드를 실행할 수 없게 됩니다. 즉, 한 번에
     * 하나의 thread만 이 메서드를 실행할 수 있습니다.
     * <p>
     * `synchronized` 블록을 사용하면 특정 객체에 대해서만
     * 동기화할 수도 있습니다. 이는 성능 향상에 도움이 될 수 있습니다.
     */
    public synchronized void createProduct(JSONObject data) throws SQLException {
        String productName = data.get("name").toString();
        int productPrice = Integer.parseInt(data.get("price").toString());
        int productStock = Integer.parseInt(data.get("stock").toString());
        ProductDto product = new ProductDto(productName, productPrice, productStock);
        repository.save(product);
    }

    public synchronized void updateProduct(JSONObject data) {
        long productId = Long.parseLong(data.get("id").toString());

        if (!isValidProductId(productId)) {
            System.out.println(INVALID_PRODUCT_NUMBER_MESSAGE);
            return;
        }

        String updatedProductName = data.get("name").toString();
        int updatedProductPrice = Integer.parseInt(data.get("price").toString());
        int updatedProductStock = Integer.parseInt(data.get("stock").toString());
        ProductDto updateProduct = new ProductDto(updatedProductName, updatedProductPrice, updatedProductStock);

        repository.update(productId, updateProduct);
    }

    public synchronized void deleteProduct(JSONObject data) {
        long productId = Long.parseLong(data.get("id").toString());

        if (!(isValidProductId(productId))) {
            System.out.println(INVALID_PRODUCT_NUMBER_MESSAGE);
            return;
        }
        repository.delete(productId);
    }

    public void exitApplication() {
        System.out.println("EXIT");
    }

    public boolean isValidProductId(long id) {
        return repository.existsById(id);
    }
}