package common;

public enum ErrorCode {
    INVALID_MENU_OPTION(1001, "❗️잘못된 메뉴 번호입니다. 다시 입력해주세요.❗️"),
    INVALID_PRODUCT_ID(1002, "❗️잘못된 상품 번호입니다. 다시 입력해주세요.❗️"),
    INVALID_PRODUCT_NAME(1003, "❗️잘못된 상품 이름입니다.❗️\n❗️숫자, 영문, 한글 문자로 1 ~ 10자로 작성해주세요.❗️"),
    NON_EXISTENT_PRODUCT_ID(1004, "❗️존재하지 않는 상품 번호입니다. 다시 입력해주세요.❗️"),
    NULL_PRODUCT_INFO(1005, "❗️상품 정보는 빈값일 수 없습니다.❗️"),
    INVALID_PRODUCT_PRICE(1006, "❗️상품 가격이 유효 범위를 벗어났습니다.❗️\n❗️0원부터 999,999원 사이의 가격을 입력해주세요.❗️"),
    INVALID_PRODUCT_STOCK(1007, "❗️상품 재고가 유효 범위를 벗어났습니다.❗️\n❗️0개부터 999개 사이의 재고를 입력해주세요.❗️");

    private int code;
    private String msg;

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ErrorCode: " + code +
            "\t" + "ErrorMessage: "+ msg;
    }
}
