package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * TODO
 * 상품의 상태를 표현한다.
 * 상품의 핵심 변경 규칙을 가진다.
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static long cnt = 1;

    private long id;
    private String name;
    private int price;
    private int stock;


    public Product(String name, int price, int stock) {
        this.id = cnt++;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void updatedProduct(Product updateProduct) {
        this.name = updateProduct.name;
        this.price = updateProduct.price;
        this.stock = updateProduct.stock;

    }

    @Override
    public String toString() {
        return String.format("%-6d%-20s\t%-15d\t%-10d", this.id, this.name, this.price, this.stock);
    }
}
