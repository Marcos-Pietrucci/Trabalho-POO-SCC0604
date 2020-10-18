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
    protected static int velocidade;
    
    
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
     * Método que retorna a quantidade de vidas do elemento
     * @return vidas int - Quantidade de vidas restantes
     */
    public int getVidas()
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
    
    /**
     * Método que remove uma vida deste elemento
     */
    public void removeVida()
    {
        this.vidas--;
    }

}
