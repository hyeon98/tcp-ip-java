package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Casting implements Serializable {

    Casting() {

    }

    public static JSONObject toJson(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject result = (JSONObject) parser.parse(data);

        return result;
    }

    public static List<String> parseProductList(String data) throws ParseException {
        List<String> productList = new ArrayList<>();

        JSONObject productObject = toJson(data);
        JSONArray productArray = (JSONArray) productObject.get("products");

        for (Object product : productArray) {
            productList.add(product.toString());
        }

        return productList;
    }
}
