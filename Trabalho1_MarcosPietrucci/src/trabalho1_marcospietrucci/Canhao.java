package trabalho1_marcospietrucci;

/**
 * Classe que define o canhão controlado pelo jogador 
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class Canhao extends Elemento {
    
    private int pontos;
    
    Canhao(int x, int y, char simbol, int vidas, int velocidade)
    {
        super(x,y,simbol,vidas,velocidade);
        pontos = 0;
    }
    
    /**
     * Método que retorna os pontos do canhão
     * @return pontos - Pontos do jogador
     */
    public int getPontos()
    {
        return this.pontos;
    }
    
    /**
     * Método que aumenta em uma unidade os pontos do jogador
     */
    public void aumentaPontos()
    {
        this.pontos++;
    }
}
