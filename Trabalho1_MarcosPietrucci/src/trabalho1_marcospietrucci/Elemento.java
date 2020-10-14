package trabalho1_marcospietrucci;

/**
 * Classe mãe de todos os elementos do jogo: Insavor, canhão, tiro e barreira 
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public abstract class Elemento {
    
    public int x, y;
    protected char simbol;
    protected int vidas;
    protected int velocidade;
    
    
    Elemento(int x, int y, char simbol, int vidas, int velocidade)
    {
        this.x = x;
        this.y = y;
        this.simbol = simbol;
        this.vidas = vidas;
        this.velocidade = velocidade;
    }
    
    /**
     * Método que seta a atual posição do elemento
     * @param x int - Posição X do elemento na matriz da tela 
     * @param y int - Posição Y do elemento na matriz da tela
     */
    public final void setPos(int x, int y)
    {
        this.x = x;
        this.x = y;
    }
    
    /**
     * Método que seta a quantidade de vidas
     * @param vidas int - Seta a quantidade de vidas do elemento
     */
    public final void setVida(int vidas)
    {
        this.vidas = vidas;
    }
    
    
    /**
     * Método que retorna a quantidade atual de vidas
     * @return vidas int - Número de vidas do elemento
     */
    public final int getVidas()
    {
        return this.vidas;
    }
    
    /**
     * Método que retorna o símbolo
     * @return simbol char - Símbolo deste elemento
     */
    public char getSimbol()
    {
        return this.simbol;     
    }
   
}
