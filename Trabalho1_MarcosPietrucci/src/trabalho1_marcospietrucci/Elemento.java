
package trabalho1_marcospietrucci;

/**
 * Classe mãe de todos os elementos do jogo: Nave, tiros, inimigos e barreiras
 * 
 * @author marco
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
     * @param x
     * @param y
     */
    public final void setPos(int x, int y)
    {
        this.x = x;
        this.x = y;
    }
    
    /**
     * Método que seta a quantidade de vidas
     * @param vidas 
     */
    public final void setVida(int vidas)
    {
        this.vidas = vidas;
    }
    
    /**
     * Método que retorna a quantidade atual de vidas
     * @return vidas
     */
    public final int getVidas()
    {
        return this.vidas;
    }
    
    /**
     * Método que retorna o símbolo
     * @return simbol - Símbolo deste elemento
     */
    public final char getSimbol()
    {
        return this.simbol;
                
    }
}
