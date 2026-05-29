package dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private static long cnt = 1;
    private long id;
    private String name;
    private int price;
    private int stock;


    public ProductDto(String name, int price, int stock) {
        this.id = cnt++;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public ProductDto updatedProduct(ProductDto updateProduct) {
        this.name = updateProduct.name;
        this.price = updateProduct.price;
        this.stock = updateProduct.stock;

        return this;
    }

    @Override
    public String toString() {
        String listFormat = String.format("%-6d%-20s\t%-15d\t%-10d", this.id, this.name, this.price, this.stock);
        return listFormat;
    }
}
