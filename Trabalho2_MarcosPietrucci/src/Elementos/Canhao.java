/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elementos;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author marco
 */
public class Canhao extends Elemento{
    
    private final int velocidade;
    private int pontos;
   
    public Canhao(double x, double y, int largura, int altura, int vidas,
                  int velocidade, Image imagem)
    {
        super(x, y, largura, altura, vidas, imagem);
        this.velocidade = velocidade;
        this.pontos = 0;
    }
    
    public int getVelocidade()
    {
        return this.velocidade;
    }
    
    public int getPontos()
    {
        return this.pontos;
    }
    
    public void ganhaPontos()
    {
        this.pontos += 10;
    }
}
