package Elementos;

import javafx.scene.image.Image;

/**
 * Classe que define e controla as barreiras do jogo
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class Barreira extends Elemento{
    
    public Barreira(double x, double y, int largura, int altura, int vidas, Image imagem) 
    {
        super(x, y, largura, altura, vidas, imagem);
    }

    /**
     * Método que remove a vida a barreira e atualiza a imagem de acordo com seu
     * estado
     */
    @Override
    public boolean removeVida()
    {
        this.vidas--;
        
        if(this.vidas == 0)
            this.vivo = false;
        
        if(this.vivo)
        {
            //Verificar as vidas e atualizar a imagem de acordo
            if(this.vidas >= 4 && this.vidas <= 7 )
                this.setImagem(new Image("Imagens/barreira1.png", 100, 50, false, false));
            else if(this.vidas >= 1 && this.vidas <= 3  )
                this.setImagem(new Image("Imagens/barreira2.png", 100, 50, false, false));
        }
        
        return this.vivo;
    }
 
}
