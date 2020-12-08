package Engine_funcionamento;

import Elementos.Invasor;
import java.util.Comparator;

/**
 * Classe que define um comparador de coordenadas dos invasores
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class Comparador implements Comparator<Invasor> {
    
    //Atributo vai possuir a posição x do jogador naquele instante
    private final double x_player;
    
    Comparador(double x_player)
    {
        this.x_player = x_player;
    }
    
    /**
     * Método que compara dois invasores, decidindo qual deles está mais próximo
     * do canhão
     * @param In1 Invasor - Um dos invasores a ser comparado
     * @param In2 Invasor - O outro invasor a ser comparado
     * @return int - Valores negativos para In1 maior que In2, e positivos caso contrário
     */
    @Override
    public int compare(Invasor In1, Invasor In2)
    {
        double v1 = Math.abs(In1.x - x_player);
        double v2 = Math.abs(In2.x - x_player);
        
        return Double.compare(v1, v2);
    }
    
}
