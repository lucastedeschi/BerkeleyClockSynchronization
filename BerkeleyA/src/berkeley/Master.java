package berkeley;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 41406133 - Lucas Rezende Tedeschi
 * @author 41404998 - Jonatas Alvarenga Maximiano
 */
public class Master {
    public void initialize(String[] IPsSlaves, int[] portas, long tol, FileWriter logFile) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        DatagramPacket sendPacket;
        InetAddress IPAddress;
        
        // Criação do arquivo de saída para log
        PrintWriter gravarArq = new PrintWriter(logFile);
        gravarArq.write("");
        gravarArq.flush();
        gravarArq.printf("+------ Log Master -------+%n");
        gravarArq.flush();
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        Util util = new Util(0, logFile);
        String sentence = "";
        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss.SSS");

        // Lê cada slave
        while (true) {
            long[] atrasos = {0, 0, 0};
            for (int i = 0; i < IPsSlaves.length; i++) {
                // Pega nome do slave
                IPAddress = InetAddress.getByName(IPsSlaves[i]);
                sentence = "getHora";
                sendData = sentence.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portas[i]);

                // Enviar a requisição de hora para o slave
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Recebe hora do slave
                clientSocket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData());

                // Exibe e calcula as diferenças de horas entre master e o slave em questão
                System.out.println("Foi recebido do slave " + IPsSlaves[i] + ":" + portas[i] + ": " + modifiedSentence.trim());
                gravarArq.printf("Foi recebido do slave " + IPsSlaves[i] + ":" + portas[i] + ": " + modifiedSentence.trim() + "%n");
                gravarArq.flush();
                
                long atrasoTemp = Long.valueOf(modifiedSentence.trim());
                atrasos[i] = atrasoTemp;

                System.out.println("Diferença entre master e slave de " 
                        + String.valueOf(util.getTempo() - Long.valueOf(modifiedSentence.trim())) + " milissegundos");
                gravarArq.printf("Diferença entre master e slave de " 
                        + String.valueOf(util.getTempo() - Long.valueOf(modifiedSentence.trim())) + " milissegundos%n");
                gravarArq.flush();
            }

            // Algoritmo de Berkeley
            atrasos = util.maiorConjunto(atrasos, tol);
            long correcao = util.avg(atrasos);

            // Atualiza própria hora
            util.setTempo(0);
            long tempoBefore = util.getTempo();
            System.out.println("Hora atual: " + hms.format(tempoBefore));
            gravarArq.printf("Hora atual: " + hms.format(tempoBefore) + "%n");
            gravarArq.flush();
            util.setTempo(correcao);
            long tempoAfter = util.getTempo();
            System.out.println("Hora após atualização: " + hms.format(tempoAfter));
            gravarArq.printf("Hora após atualização: " + hms.format(tempoAfter) + "%n");
            gravarArq.flush();
            util.setTempo(0);

            // Após calcular a correção, ela é enviada para cada slave
            for (int i = 0; i < IPsSlaves.length; i++) {
                System.out.println("Enviando para o slave " + IPsSlaves[i] + ":" + portas[i] + " correção de " + correcao + " milisegundos");
                gravarArq.printf("Enviando para o slave " + IPsSlaves[i] + ":" + portas[i] + " correção de " + correcao + " milisegundos%n");
                gravarArq.flush();
                sentence = "corrigeHora:" + correcao;
                sendData = sentence.getBytes();
                IPAddress = InetAddress.getByName(IPsSlaves[i]);
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portas[i]);
                clientSocket.send(sendPacket);
            }
            
            Thread.sleep(15000);
        }
    }
}
