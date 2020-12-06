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
public class Barreira extends Elemento{
    
    public Barreira(double x, double y, int altura, int largura, int vidas, Image imagem) {
        super(x, y, altura, largura, vidas, imagem);
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 
}
