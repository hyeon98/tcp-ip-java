package service;

import common.ValidCheck;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import common.handler.MenuDataHandler;
import common.MenuText;


public class ClientService {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static List<String> productList;
    private static int menuNum;
    private static MenuDataHandler menuDataHandler;
    private static ValidCheck validCheck = new ValidCheck();

    public ClientService() {
        this.menuDataHandler = new MenuDataHandler();
    }

    public MenuDataHandler getProductData(List<String> inputProductList) throws IOException {
        productList = inputProductList;
        displayMenuList();
        selectMenuOption();
        return menuDataHandler;
    }

    /**
     * 메뉴 정보 출력
     */
    public void displayMenuList() {
        System.out.println(MenuText.HEADER.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU_TITLE.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        getProductList();
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU.getText());
    }

    /**
     * 저장된 상품 List 출력
     */
    public void getProductList() {
        for (String product : productList) {
            System.out.println(product);
        }
    }

    /**
     * 메뉴 번호 선택
     */
    public void selectMenuOption() throws IOException {
        System.out.print(MenuText.SELECT.getText());
        String inputMenu = br.readLine().trim();

        validCheck.isMenuValid(inputMenu);
        menuNum = Integer.parseInt(inputMenu);
        menuDataHandler.setMenuOption(menuNum);

        switch (menuNum) {
            case 1 -> createProduct();
            case 2 -> updateProduct();
            case 3 -> deleteProduct();
            case 4 -> exitApplication();
        }
    }

    /**
     * 메뉴: 1. Create (상품 생성)
     * 입력: 상품 이름, 상품 가격, 상품 재고
     */
    public void createProduct() throws IOException {
        System.out.println(MenuText.MENU1.getText());
        System.out.print("상품 이름: ");
        String productName = br.readLine();
        validCheck.isProductNameValid(productName);

        System.out.print("상품 가격: ");
        String inputPrice = br.readLine().trim();
        validCheck.isProductPriceValid(inputPrice);
        int productPrice = Integer.parseInt(inputPrice);

        System.out.print("상품 재고: ");
        String inputStock = br.readLine().trim();
        validCheck.isProductStockValid(inputStock);
        int productStock = Integer.parseInt(inputStock);

        menuDataHandler.setName(productName);
        menuDataHandler.setPrice(productPrice);
        menuDataHandler.setStock(productStock);
    }

    /**
     * 메뉴: 2. Update (상품 수정)
     * 입력: 상품 번호, 상품 이름, 상품 가격, 상품 재고
     */
    public void updateProduct() throws IOException {
        System.out.println(MenuText.MENU2.getText());

        System.out.print("상품 번호: ");
        String inputId = br.readLine().trim();
        validCheck.isProductIdValid(inputId);
        long productId = Long.parseLong(inputId);

        System.out.print("이름 변경: ");
        String updatedProductName = br.readLine();

        System.out.print("가격 변경: ");
        String inputPrice = br.readLine().trim();
        validCheck.isProductPriceValid(inputPrice);
        int updatedProductPrice = Integer.parseInt(inputPrice);

        System.out.print("재고 변경: ");
        String inputStock = br.readLine().trim();
        validCheck.isProductStockValid(inputStock);
        int updatedProductStock = Integer.parseInt(inputStock);

        menuDataHandler.setId(productId);
        menuDataHandler.setName(updatedProductName);
        menuDataHandler.setPrice(updatedProductPrice);
        menuDataHandler.setStock(updatedProductStock);
    }

    /**
     * 메뉴: 3. Delete (상품 삭제)
     * 입력: 상품 번호
     */
    public void deleteProduct() throws IOException {
        System.out.println(MenuText.MENU3.getText());
        System.out.print("상품 번호: ");
        String inputId = br.readLine().trim();

        validCheck.isProductIdValid(inputId);
        long productId = Long.parseLong(inputId);

        menuDataHandler.setId(productId);
    }

    /**
     * 메뉴: 4. Exit (프로그램 종료)
     */
    public void exitApplication() {
        System.out.println(MenuText.MENU4.getText());
    }



}