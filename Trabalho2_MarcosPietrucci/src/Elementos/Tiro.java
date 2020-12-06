/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elementos;

import javafx.scene.image.Image;

/**
 *
 * @author marco
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

    public boolean move(int tamY)
    {
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
