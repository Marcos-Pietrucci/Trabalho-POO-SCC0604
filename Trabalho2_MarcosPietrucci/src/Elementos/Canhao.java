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
    
    private int velocidade;
   
    public Canhao(double x, double y, int altura, int largura, int vidas, Image imagem) {
        super(x, y, altura, largura, vidas, imagem);
        this.velocidade = 5;
    }
    
    public void move(KeyEvent e)
    {
        switch(e.getCode())
        {
            case LEFT:  this.x -= this.velocidade;
                        break;
                            
            case RIGHT: this.x += this.velocidade;
                        break;           
        }   
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
