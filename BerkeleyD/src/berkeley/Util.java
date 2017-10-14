package berkeley;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 *
 * @author 41406133 - Lucas Rezende Tedeschi
 * @author 41404998 - Jonatas Alvarenga Maximiano
 */
public class Util {

    long tempo1, tempo1Real;
    SimpleDateFormat sdf;
    long atraso, atrasoAcumulado;
    String MasterOuSlave;
    long correcao = 0;

    public Util(int param) {
        tempo1 = System.currentTimeMillis();
        tempo1Real = tempo1;
        sdf = new SimpleDateFormat("HH:mm:ss");
        if (param == 0) {
            MasterOuSlave = "Master";
            atraso = 0;
        } else {
            MasterOuSlave = "Slave";
            atraso = (int) ((Math.random()*4 - 2) * 1000); // desfasado entre -2seg e +2seg
        }
        atrasoAcumulado = 0;
        System.out.println(MasterOuSlave + " - O atraso/adianto do relógio neste sistema é de " + atraso + " milisegundos a cada 10 segundos.");
    }

    public long getTempo() {
        long tempo2 = System.currentTimeMillis();
        long diff = tempo2 - tempo1Real;
        long esteAtraso = (long) (diff / 10000. * atraso);
        tempo1 = esteAtraso + diff + tempo1 + correcao;
        atrasoAcumulado += esteAtraso;
        tempo1Real = tempo2;
        System.out.println("Atraso acumulado: " + atrasoAcumulado + " milisegundos");
        return tempo1;
    }
    
    public void setTempo(long diff) {
        correcao = diff;
    }

    public String getTempoReal() {
        return sdf.format(System.currentTimeMillis());
    }

    public long[] maiorConjunto(long[] atrasos, long maiorAtraso) {
        List<Long> lista1 = new ArrayList<Long>();
        List<Long> lista2 = new ArrayList<Long>();
        for (int i = 0; i < atrasos.length; i++) {
            lista2.clear();
            for (int j = 0; j < atrasos.length; j++) {
                if (i != j) {
                    long diff = 0;
                    diff = atrasos[i] - atrasos[j];
                    if (Math.abs(diff) <= maiorAtraso) {
                        lista2.add(diff);
                    } else{
                        lista2.add(diff);
                    }
                }
            }
            if (lista2.size() > lista1.size()) {
                lista1.clear();
                for (int k = 0; k < lista2.size(); k++) {
                    lista1.add(lista2.get(k));
                }
            }
        }

        long[] diffs = new long[lista1.size()];
        for (int i = 0; i < lista1.size(); i++) {
            diffs[i] = lista1.get(i);
        }

        return diffs;
    }

    public long avg(long[] diffs) {
        long soma = 0;
        for (int i = 0; i < diffs.length; i++) {
            soma += diffs[i];
        }
        return soma / diffs.length;
    }
}
