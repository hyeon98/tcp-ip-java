package common.handler;

import java.io.Serializable;
import org.json.simple.JSONObject;

public class MenuDataHandler implements Serializable {
    private JSONObject menuField;

    public MenuDataHandler() {
        menuField = new JSONObject();
        JSONObject data = new JSONObject();

        data.put("id", null);
        data.put("name", null);
        data.put("price", null);
        data.put("stock", null);

        menuField.put("menu", null);
        menuField.put("data", data);
    }

    public JSONObject getmenuField() {
        return menuField;
    }

    public void setMenuOption(int menuOption) {
        menuField.put("menu", menuOption);
    }

    public void setId(long id) {
        ((JSONObject) (menuField.get("data"))).put("id", id);
    }

    public void setName(String name) {
        ((JSONObject) (menuField.get("data"))).put("name", name);
    }

    public void setPrice(int price) {
        ((JSONObject) (menuField.get("data"))).put("price", price);
    }

    public void setStock(int stock) {
        ((JSONObject) (menuField.get("data"))).put("stock", stock);
    }

    // int: primitive type, cannot hold null value
    // Integer: reference type, can hold null value
    public Integer getMenuOption() {
        Object menu = menuField.get("menu");
        return menu != null ? Integer.parseInt(menuField.get("menu").toString()) : null;
    }

    // long: primitive type, cannot hold null value
    // Long: reference type, can hold null value
    public Integer getId() {
        Object id = ((JSONObject) (menuField.get("data"))).get("id");
        return id != null ? Integer.parseInt(((JSONObject) (menuField.get("data"))).get("id").toString()) : null;
    }

    public String getName() {
        Object name = ((JSONObject) (menuField.get("data"))).get("name");
        return name != null ? ((JSONObject) (menuField.get("data"))).get("name").toString() : null;
    }

    public Integer getPrice() {
        Object price = ((JSONObject) (menuField.get("data"))).get("price");
        return price != null ? Integer.parseInt(((JSONObject) (menuField.get("data"))).get("price").toString()) : null;
    }

    public Integer getStock() {
        Object stock = ((JSONObject) (menuField.get("data"))).get("stock");
        return stock != null ? Integer.parseInt(((JSONObject) (menuField.get("data"))).get("stock").toString()) : null;
    }

    @Override
    public String toString() {
        return menuField.toJSONString();
    }
}
