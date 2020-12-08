package Elementos;

import javafx.scene.image.Image;

/**
 * Classe que define e controla o canhão do jogo
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class Canhao extends Elemento{
    
    //Atributos do canhão
    private final int velocidade;
    private int pontos;
   
    public Canhao(double x, double y, int largura, int altura, int vidas,
                  int velocidade, Image imagem)
    {
        super(x, y, largura, altura, vidas, imagem);
        this.velocidade = velocidade;
        this.pontos = 0;
    }
    
    /**
     * Método que retorna a velocidade do jogador
     * @return velocidade int - Quantidade de pixels por loop andados pelo canhão
     */
    public int getVelocidade()
    {
        return this.velocidade;
    }
    
    /**
     * Método que retorna a quantidade atual de pontos do jogador
     * @return pontos int - Pontuação baseada no número de abates
     */
    public int getPontos()
    {
        return this.pontos;
    }
    
    /**
     * Método que acrescenta pontos ao jogador após uma abate
     */
    public void ganhaPontos()
    {
        this.pontos += 10;
    }
}
