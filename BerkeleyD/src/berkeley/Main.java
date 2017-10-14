/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

import java.util.Scanner;

/**
 *
 * @author 41406133 - Lucas Rezende Tedeschi
 * @author 41404998 - Jonatas Alvarenga Maximiano
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Porta 9994 - Inicilizar como slave(-s) ou mater(-m)?");
        String entrada = sc.nextLine();
        
        if(entrada.equals("-m"))
            new Master().initialize();
        else if(entrada.equals("-s"))
            new Slave().initialize();
    }
}
