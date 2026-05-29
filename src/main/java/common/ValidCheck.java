package common;

import static common.ErrorCode.INVALID_PRODUCT_ID;

import exception.ProductException;

public class ValidCheck {
    private static final String MENU_NUMBER = "^[1-4]";
    private static final String NAME = "^[a-zA-Z0-9ㄱ-힣]{1,10}";
    private static final String PRICE = "^\\d{0,6}$";
    private static final String STOCK = "^\\d{0,3}$";

    /**
     * 메뉴 번호 유효성 검사
     * 메뉴 번호: 1 ~ 4
     */
    public void isMenuValid(String menu) {
        if (!(menu.matches(MENU_NUMBER))) {
            throw new ProductException(ErrorCode.INVALID_MENU_OPTION);
        }
    }

    /**
     * 상품 이름 유효성 검사
     * [0~9, a~z, A~Z, ㄱ~힣]
     * 글자 수: 1~10
     */
    public void isProductNameValid(String name) {
        if (!(name.matches(NAME))) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT_NAME);
        }
    }

    /**
     * 상품 가격 유효성 검사
     * 상품 가격: 0 ~ 999,999
     */
    public void isProductPriceValid(String price) {
        if (!(price.matches(PRICE))) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
    }

    /**
     * 상품 재고 유효성 검사
     * 상품 재고: 0 ~ 999
     */
    public void isProductStockValid(String stock) {
        if (!(stock.matches(STOCK))) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT_STOCK);
        }
    }

    /**
     * 상품 Id 유효성 검사
     */
    public void isProductIdValid(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ProductException(INVALID_PRODUCT_ID);
        }
    }
}
