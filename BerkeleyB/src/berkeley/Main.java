/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 41406133 - Lucas Rezende Tedeschi
 * @author 41404998 - Jonatas Alvarenga Maximiano
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Inicializar como slave(-s) ou master(-m)?");
        String entrada = sc.nextLine();
        
        if(entrada.equals("-m")) {
            System.out.println("ip:port");
            String[] ipPort = sc.nextLine().split(":");
            System.out.println("time: default");
            System.out.println("d");
            long tol = sc.nextLong();
            System.out.println("slavesfile");
            String slavesFile = sc.next();
            System.out.println("logfile");
            String logFile = sc.next();
            
            ArrayList<String> IPsSlaves = new ArrayList<>();
            ArrayList<Integer> portas = new ArrayList<>(); 
            try {
                FileReader arq = new FileReader(slavesFile);
                BufferedReader lerArq = new BufferedReader(arq);

                String linha = lerArq.readLine(); 
                while (linha != null) {
                    String[] linhaVetor = linha.split(":");
                    IPsSlaves.add(linhaVetor[0]);
                    portas.add(Integer.valueOf(linhaVetor[1]));
                    linha = lerArq.readLine();
                }
                arq.close();
            } catch (IOException e) {
                System.err.printf("Erro na abertura do arquivo: %s.\n",
                e.getMessage());
            }
            
            int[] portasVetor = new int[portas.size()];
            int i = 0;
            for (Integer porta : portas) {
                portasVetor[i] = porta;
                i++;
            }
            String[] IPsSlavesVetor = new String[IPsSlaves.size()];
            i = 0;
            for (String IPsSlave : IPsSlaves) {
                IPsSlavesVetor[i] = IPsSlave;
                System.out.println(IPsSlavesVetor[i] + ":" + portasVetor[i]);
                i++;
            }
            
            FileWriter arq = new FileWriter(logFile);
            
            new Master().initialize(IPsSlavesVetor, portasVetor, tol, arq);
        }
        else if(entrada.equals("-s")){
            System.out.println("ip:port");
            String[] ipPort = sc.nextLine().split(":");
            System.out.println("time: default");
            
            System.out.println("logfile");
            String logFile = sc.nextLine();
            FileWriter arq = new FileWriter(logFile);
            
            new Slave().initialize(Integer.parseInt(ipPort[1]), arq);
        }
    }
}
