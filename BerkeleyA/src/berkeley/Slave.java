package berkeley;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author 41406133 - Lucas Rezende Tedeschi
 * @author 41404998 - Jonatas Alvarenga Maximiano
 */
public class Slave {

    public void initialize() throws Exception {
        
        // Processo de abrir slave para receber requisiçoes
        DatagramSocket serverSocket = new DatagramSocket(9998);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        String sentence;
        Util util = new Util(1);
        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss.SSS");
        
        while (true) {
            util.setTempo(0);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            sentence = new String(receivePacket.getData()).trim();
            receiveData = new byte[1024];
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
                
            // Verifica se requisição do master foi GetHora
            if (sentence.contains("getHora")) {
                sentence = Long.toString(util.getTempo());
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } else if (sentence.contains("corrigeHora")){
                sentence = sentence.replace("corrigeHora:", "");
                long correcao = Long.valueOf(sentence);
                System.out.println("Correção recebida: " + correcao);
                System.out.println("Hora atual: " + hms.format(util.getTempo()));
                util.setTempo(correcao);
                System.out.println("Hora após atualização: " + hms.format(util.getTempo()));
            } else {
                System.out.println("Hora atualizada: " + sentence.substring(12));
            }
        }
    }
}
