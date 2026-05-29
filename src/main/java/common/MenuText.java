package common;

public enum MenuText {
    HEADER("\n[상품 목록]"),
    BORDER_LINE("----------------------------------------------------"),
    MENU_TITLE("no\t\tname\t\t\t\t\t\t\t\t\tprice\t\t\t\t\t\tstock"),
    MENU("메뉴: 1.Create | 2. Update | 3. Delete | 4.Exit"),
    SELECT("선택: "),
    MENU1("[상품 생성]"),
    MENU2("[상품 수정]"),
    MENU3("[상품 삭제]"),
    MENU4("[종료]");


    private final String text;

    MenuText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
