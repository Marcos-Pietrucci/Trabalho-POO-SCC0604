/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elementos;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author marco
 */
public class Invasor extends Elemento{
    
    private static double velocidade; 
    
    //0: mover para direita | 1: mover para esquerda
    private static int direcao;
    private static int descer;

    public Invasor(double x, double y, int largura, int altura, int vidas, Image imagem) {
        super(x, y, largura, altura, vidas, imagem);
        Invasor.direcao = 0;
        Invasor.descer = 0;
        Invasor.velocidade = 0.2;
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
     * Método que inverte o sentido de movimento do invasor ao inverter o valor da variável direcao. Além disso, este método programa o invasor
     * para se aproximar uma unidade do canhão.
     * 
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
        if(Invasor.descer == 1)
        {
            this.y += 20;
        }
        else
        {
            switch (direcao)
            {
                case 0: if(this.x < tamX && (this.x + Invasor.velocidade >= tamX))
                        {   
                            this.x++;
                        }
                        else
                        {
                            this.x += Invasor.velocidade;
                        }
                        break;
            
                case 1: if(this.x >= 1 && (this.x - Invasor.velocidade < 0))
                        {
                            this.x--;
                        }
                        else
                        {
                            this.x -= Invasor.velocidade;
                        }
                        break;
            } 
        }
        
    }
    
    /**
     * Método responsável por aumentar a velocidade dos invasores
     * @param invaders ArrayList - Contém todos os objetos dos invasores
     */
    public static void aumentaVelocidade(ArrayList<Invasor> invaders)
    {
        //O número original dos aliens é 55        
        Invasor.velocidade += 0.025;
    }

}
