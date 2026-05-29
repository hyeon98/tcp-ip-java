package product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import service.ServerService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import common.Casting;
import common.handler.DataHandler;

/**
 * Thread vs Runnable
 * [Thread 클래스]
 * 1️⃣ Java 에서는 다중 상속이 불가능 하므로,
 *    Thread 클래스를 상속하면 다른 클래스를 상속 받을 수 없습니다.
 * 2️⃣ Thread 클래스는 Runnable 인터페이스를 구현하고 있으며,
 *    이를 상속받으면 새로운 스레드를 생성할 때 더 많은 자원이 필요합니다.
 * 3️⃣ Thread 클래스를 상속하면 동작을 수정하기 위해 run() 메서드를 오버라이드해야 합니다.
 *
 * [Runnable 인터페이스]
 * 1️⃣ Runnbale 인터페이스는 단일 추상ㅇ 메소드(run())를 갖는 함수형 인터페이스입니다.
 *    따라서 람다식으로도 구현이 가능합니다.
 * 2️⃣ Runnable 인터페이스를 구현하는 클래스는 run() 메서드를 반드시 구현해야 합니다.
 * 3️⃣ Runnable 인터페이스를 구현하면 다른 클래스를 상속받을 수 있어
 *    다중 상속의 유연성을 제공합니다.
 * 4️⃣ 인터페이스 구현은 재사용성이 높고,
 *    코드의 일관성을 유지할 수 있어서 Thread 상속보다 효율적입니다.
 * 5️⃣ Runnable을 사용하는 스레드는 Thread 객체를 통해 실행되므로,
 *    스레드 풀과 같은 고급 스레드 관리 기능을 사용할 수 있습니다.
 */
public class ProductServer {

    private static final int PORT = 8080;
    private static DataHandler dataHandler;
    private static ServerService serverService;

    public static void main(String[] args) {
        /**
         * 10개의 쓰레드로 구성된 풀을 생성합니다.
         */
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            /*
             * Create a server socket
             */
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println(getCurrentTime() + " Server is ready.");

            while (true) {
                /*
                 * Accept a client connection
                 */
                System.out.println(getCurrentTime() + " Waiting for a connection request.");
                Socket clientSocket = serverSocket.accept(); // Socket for communication with client
                System.out.println(
                    getCurrentTime()
                    + " A connection request has been received from "
                    + clientSocket.getInetAddress()
                );

                Runnable runnable = new ProductServerImpl(clientSocket);
                executor.submit(runnable);
            }
        } catch (IOException e) {
            System.out.println(
                getCurrentTime() + " Error occurred while creating the server socket: "
                    + e.getMessage());
        }

        /**
         * 모든 작업이 완료된 후 풀을 종료합니다.
         */
        executor.shutdown();
    }


    public static class ProductServerImpl implements Runnable {
        private Socket clientSocket;
        ProductServerImpl(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            serverService = new ServerService();
            try {
                /*
                 * Initialize the streams
                 */
                BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(),
                    true);

                while (true) {

                    /**
                     * 1번
                     * server -> client (productList)
                     * 상품 목록을 Client로 보내기
                     */
                    sendProductListToClient(clientWriter);

                    /**
                     * 2번
                     * client -> server (menuData)
                     * Client에서 보낸 user가 입력한 정보 받아서 처리하기
                     */
                    processMenuDataFromClient(clientReader);

                    /**
                     * 3번
                     * server -> client (result)
                     * 2번의 처리 결과 Client로 보내기
                     */
                    clientWriter.println(dataHandler.toString());

                }
            } catch (IOException e) {
                dataHandler.setStatus("fail");
                System.out.println(
                    getCurrentTime()
                        + " Error occurred during communication with the client: "
                        + e.getMessage());
            }
            System.out.println(getCurrentTime() + " Client connection closed.");

        }

        /**
         * 1번
         * server -> client (productList)
         * 상품 목록을 Client로 보내기
         */
        void sendProductListToClient(PrintWriter clientWriter) {
            List<String> productList = serverService.getProductList();
            dataHandler = new DataHandler("success", productList);
            clientWriter.println(dataHandler.toString());
        }

        /**
         * 2번
         * client -> server (menuData)
         * Client에서 보낸 user가 입력한 정보 받아서 처리하기
         */
        void processMenuDataFromClient(BufferedReader clientReader) throws IOException {
            try {
                String menuDataString = clientReader.readLine();

                System.out.println(
                    getCurrentTime()
                        + "[" + Thread.currentThread().getName() + "] "
                        + menuDataString
                );

                JSONObject jsonData = Casting.toJson(menuDataString);

                int menuOption = Integer.parseInt(jsonData.get("menu").toString());
                JSONObject productData = (JSONObject) (jsonData.get("data"));

                switch (menuOption) {
                    case 1 -> serverService.createProduct(productData);
                    case 2 -> serverService.updateProduct(productData);
                    case 3 -> serverService.deleteProduct(productData);
                    case 4 -> {
                        serverService.exitApplication();
                        clientSocket.close();
                    }
                }

                dataHandler = new DataHandler("success");

            } catch (ParseException e) {
                dataHandler.setStatus("fail");
                System.out.println(
                    getCurrentTime() + " Error occurred while parsing JSON data: "
                        + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    static String getCurrentTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}