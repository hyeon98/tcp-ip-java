package product;

import static common.Casting.parseProductList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import exception.ProductException;
import service.ClientService;
import org.json.simple.parser.ParseException;
import common.handler.MenuDataHandler;

public class ProductClient {

    private static Socket socket;
    private static BufferedReader serverReader;
    private static PrintWriter serverWriter;
    private static boolean isQuit = false;

    public static void main(String[] args) {
        ProductClient productClient = new ProductClient();
        try {
            // Connect to the server at IP address "127.0.0.1" and port 8080
            String serverIp = "127.0.0.1";
            System.out.println("서버에 연결중입니다.\n서버 IP: " + serverIp);
            socket = new Socket(serverIp, 8080);

            // Initialize the streams
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);

            while (!isQuit) {
                List<String> productList = productClient.receiveProductListFromServer();

                productClient.sendMenuDataToServer(productList);
            }

            // End of communication
            System.out.println("Closing the connection.");

            // Close socket
            System.out.println("Connection closed.");
        } catch (ConnectException e) {
            e.printStackTrace();
            System.out.println("Connection error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I/O error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
        }
    }

    /**
     * 1번
     * server -> client (productList)
     * server에서 보낸 메뉴 목록 받기
     */
    public List<String> receiveProductListFromServer() throws IOException, ParseException {
        String productDataString = serverReader.readLine();

        return parseProductList(productDataString);
    }


    /**
     * 2번
     * client -> server (menuData)
     * user가 입력한 메뉴 번호, 추 정보를 Server로 보내기
     */
    public void sendMenuDataToServer(List<String> productList) throws IOException {
        try {
            ClientService clientService = new ClientService();
            MenuDataHandler menuData = clientService.getProductData(productList);

            serverWriter.println(menuData.toString());

            /**
             * 3번
             * server -> client (result)
             * Server에서 보낸 처리 결과 받기
             */
            String statusResult = serverReader.readLine();

            if (menuData.getMenuOption() == 4) {
                isQuit = !isQuit;
                socket.close();
            }
        } catch (ProductException e) {
            System.out.println(e.getMessage());
            sendMenuDataToServer(productList);
        }
    }
}