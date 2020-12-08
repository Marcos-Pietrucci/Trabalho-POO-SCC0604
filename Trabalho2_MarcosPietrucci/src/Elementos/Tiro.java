package Elementos;

import javafx.scene.image.Image;

/**
 * Classe que define os tiros do jogo
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class Tiro extends Elemento{
    
    //O tiro será de Aliens ou do Canhão
    public int origem;  //0 para Alien, 1 para canhão
    
    private final int velocidade;

    public Tiro(double x, double y, int largura, int altura, int vidas, int velocidade, int origem, Image imagem) {
        super(x, y, largura, altura, vidas, imagem);
        this.velocidade = velocidade;
        this.origem = origem;
    }
    
    /**
     * Método que move o tiro
     * @param tamY int - Número de linhas da telinha
     * @return boolean - Retorna true para tiros válidos, false para inválidos
     */
    public boolean move(int tamY)
    {
        //Verificar a origem do tiro
        if(origem == 1)
        {
            if(this.y > 0)
                this.y -= velocidade;
            else
                return false;
        }
        else
        {
            if(this.y < tamY)
                this.y += velocidade;
            else
                return false;
        }
            
        return true;
    }   
}
