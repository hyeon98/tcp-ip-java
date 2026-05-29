## TCP 소켓 통신 상품 관리 프로그램

<br>

### 기능 요구 사항

#### Client

1. `Client`가 실행되면 [상품 목록]과 메뉴 출력
2. 선택에서 메뉴 번호를 입력
3. 입력 받은 메뉴에 맞는 정보를 키보드로 추가 입력
   - `[1.Create]` 선택 시, 상품 생성을 위한 정보 입력
   - `[2.Update]` 선택 시, 변경할 상품 번호와, 정보 입력
   - `[3.Delete]` 선택 시, 삭제할 상품 번호 입력
   - `[4.Exit]` 선택 시, 프로그램 종료
4. 메뉴 번호와 입력된 데이터를 JSON 형식으로 만들고, 서버로 처리 요청
5. Server에서 받은 결과 JSON을 해석하고 status가 success일 경우 1번 반복

---

#### Server

1.  Client에서 온 요청 JSON을 해석하고 처리 (동시 요청 처리를 위한 스레드풀 적용)
2.  처리 결과를 JSON 형식으로 만들고 Client로 응답 전달

---

#### Exception

- 메뉴에 없는 번호 입력 (`MENU_OPTION_NOT_FOUND_EXCEPTION`)
- 없는 상품 번호 입력 (`PRODUCT_NOT_FOUND_EXCEPTION`)
- 상품 정보 null 입력 (`NULL_PRODUCT_INFO_EXCEPTION`)
- 0 ~ 999,999 외 상품 가격 (`PRODUCT_PRICE_OUT_OF_RANGE_EXCEPTION`)
- 0 ~ 999 외 상품 재고 (`PRODUCT_STOCK_OUT_OF_RANGE_EXCEPTIO`)
- 상품 번호 / 가격 / 재고 숫자 입력 X (`INVALID_INPUT_TYPE_EXCEPTION`)

---

### 패키지

- common
  Casting.java
  ErrorCode.java
  MenuText.java
  ValidCheck.java
  - handler
    DataHandler.java
    MenuDataHandler.java
- dto
  ProductDtao.java
- exception
  ProductException.java
- product
  ProductClient.java
  ProductServer.java
- service
  ClientService.java
  ServerService.java
