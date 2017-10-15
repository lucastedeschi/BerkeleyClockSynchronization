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

    public void initialize(int portActual, FileWriter logFile) throws Exception {
        
        // Processo de abrir slave para receber requisiçoes
        DatagramSocket serverSocket = new DatagramSocket(portActual);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        String sentence;
        Util util = new Util(1, logFile);
        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss.SSS");
        
        // Criação do arquivo de saída para log
        PrintWriter gravarArq = new PrintWriter(logFile);

        gravarArq.printf("+------ Log Slave -------+\n");
        gravarArq.flush();
        
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
                gravarArq.printf("Correção recebida: " + correcao + "%n");
                gravarArq.flush();
                long actualTempo = util.getTempo();
                System.out.println("Hora atual: " + hms.format(actualTempo));
                gravarArq.printf("Hora atual: " + hms.format(actualTempo) + "%n");
                gravarArq.flush();
                util.setTempo(correcao);
                actualTempo = util.getTempo();
                System.out.println("Hora após atualização: " + hms.format(actualTempo));
                gravarArq.printf("Hora após atualização: " + hms.format(actualTempo) + "%n");
                gravarArq.flush();
            } else {
                System.out.println("Hora atualizada: " + sentence.substring(12));
            }
        }
    }
}
