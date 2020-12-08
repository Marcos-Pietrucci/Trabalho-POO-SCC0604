package Elementos;

import javafx.scene.image.Image;

/**
 * Classe que define e controla os invasores do jogo
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class Invasor extends Elemento{
    
    //Variável que armazena se estamos no último invasor ou não
    private static boolean EhUltimo;
    
    private static double velocidade; 
    
    //0: mover para direita | 1: mover para esquerda
    private static int direcao;
    private static int descer;

    public Invasor(double x, double y, int largura, int altura, int vidas, Image imagem) {
        super(x, y, largura, altura, vidas, imagem);
        Invasor.direcao = 0;
        Invasor.descer = 0;
        Invasor.velocidade = 0.2;
        EhUltimo = false;
    }
    
    /**
    * Método que retorna a atual direção do invasor
    * @return direcao int - Se 0, o invasor está andando para a direita; Se 1, o invasor está andando para a esquerda
    */
    public static int getDirecao()
    {
        return Invasor.direcao;
    }
    
    /**
    * Método que retorna a velocidade dos invasores
    * @return velocidade double - Velocidade dos invasores
    */
    public static double getVelocidade()
    {
        return Invasor.velocidade;
    }
    
   
    /**
    * Método que inverte o sentido de movimento do invasor e o programa para
    * descer
    */
    public static void inverteSentido()
    {
        //Mudar de direcao significa que o invasor deve descer um degrau
        if(Invasor.descer == 0)
        {
           Invasor.descer = 1;
        }   
        else
        {
            Invasor.descer = 0;
            if (Invasor.direcao == 1)
                Invasor.direcao = 0;
            else
                Invasor.direcao = 1;
        
        }    
    }
    
    /**
     * Método que efetivamente atualiza a posição do invasor 
     * @param tamX int - Número de colunas do campo
     */
    public void move(int tamX)
    {   
        //Se o invasor deve descerm mover apenas em y
        if(Invasor.descer == 1)
        {
            this.y += 20;
        }
        else
        {
            //Mover o invasor de acordo com sua direção
            switch (direcao)
            {
                case 0: if(this.x < tamX && (this.x + Invasor.velocidade >= tamX))
                        {   
                            this.x += Invasor.velocidade/2;
                        }
                        else
                        {
                            this.x += Invasor.velocidade;
                        }
                        break;
            
                case 1: if(this.x >= 1 && (this.x - Invasor.velocidade < 0))
                        {
                            this.x -= Invasor.velocidade/2;
                        }
                        else
                        {
                            //O último inimigo se move para a esquerda mais lentamente
                            if(Invasor.EhUltimo)
                                this.x -= Invasor.velocidade/2;
                            else
                                this.x -= Invasor.velocidade;
                        }
                        break;
            } 
        }
        
    }
    
    /**
     * Método que aumenta a velocidade dos invasores em uma quantidade arbitraria
     * Usado para o último invasor apenas
     * @param valor int - Incremento na velocidade do último invasor
     */
    public static void aumentaVelocidadeUltimo(int valor)
    {
        Invasor.velocidade += valor;
        Invasor.EhUltimo = true;    
    }
    
    /**
     * Método responsável por aumentar a velocidade dos invasores
     */
    public static void aumentaVelocidade()
    {
        //O número original dos aliens é 55        
        Invasor.velocidade += 0.02;
    }
}
