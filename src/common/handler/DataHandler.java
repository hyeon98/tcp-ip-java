package common.handler;

import java.io.Serializable;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataHandler implements Serializable {
    private JSONObject productField;
    public DataHandler() {

    }
    public DataHandler(String status) {
        productField = new JSONObject();
        productField.put("status", status);
    }

    public DataHandler(String status, List<String> productList) {
        productField = new JSONObject();
        JSONArray productArray = new JSONArray();
        for (String product : productList) {
            productArray.add(product);
        }
        productField.put("status", status);
        productField.put("products", productArray);
    }

    public JSONObject getProductField() {
        return productField;
    }
    public void setStatus(String status) {
        productField.put("status", status);
    }

    @Override
    public String toString() {
        return productField.toJSONString();
    }
}
