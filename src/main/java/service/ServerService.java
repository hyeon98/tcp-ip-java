package service;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import dto.ProductDto;

public class ServerService {

    static List<ProductDto> productList = new ArrayList<>();
    static final String INVALID_PRODUCT_NUMBER_MESSAGE = "유효하지 않은 상품 번호입니다.";

    public List<String> getProductList() {
        List<String> result = new ArrayList<>();
        for (ProductDto product : productList) {
            result.add(product.toString());
        }
        return result;
    }

    /**
     * Multi Thread 실행 시,
     * 여러 thread 에서 동시에 접근이 가능하므로,
     * 의도치 않게 변수의 값이 변경되는 것을 방지하기 위해
     * `synchronized` 키워드를 통해 동기화 처리합니다.
     *
     * 이를 통해 해당 메서드가 실행 중일 때, 다른 thread가
     * 동일한 메서드를 실행할 수 없게 됩니다. 즉, 한 번에
     * 하나의 thread만 이 메서드를 실행할 수 있습니다.
     *
     * `synchronized` 블록을 사용하면 특정 객체에 대해서만
     * 동기화할 수도 있습니다. 이는 성능 향상에 도움이 될 수 있습니다.
     */
    public synchronized void createProduct(JSONObject data) {
        String productName = data.get("name").toString();
        int productPrice = Integer.parseInt(data.get("price").toString());
        int productStock = Integer.parseInt(data.get("stock").toString());
        productList.add(new ProductDto(productName, productPrice, productStock));
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
        ProductDto product = findById(productId);
        ProductDto updateProduct = new ProductDto(productId, updatedProductName, updatedProductPrice, updatedProductStock);

        product.updatedProduct(updateProduct);
    }

    public synchronized void deleteProduct(JSONObject data) {
        long productId = Long.parseLong(data.get("id").toString());

        if (!(isValidProductId(productId))) {
            System.out.println(INVALID_PRODUCT_NUMBER_MESSAGE);
            return;
        }

        productList.remove(findById(productId));
    }

    public void exitApplication() {
        System.out.println("EXIT");
    }

    public boolean isValidProductId(long id) {
        boolean isValid = false;
        for (ProductDto product : productList) {
            if (product.getId() == id) {
                isValid = !isValid;
                break;
            }
        }
        return isValid;
    }

    public ProductDto findById(long id) {
        int index = -1;
        for (ProductDto product : productList) {
            if (product.getId() == id) {
                index = productList.indexOf(product);
                break;
            }
        }
        return productList.get(index);
    }
}