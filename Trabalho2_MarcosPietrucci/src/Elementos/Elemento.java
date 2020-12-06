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
public abstract class Elemento {
    
    public double x, y;
    public boolean vivo = true;
    public Image imagem;
    public int altura, largura;
    protected int vidas;
    
    
    
    Elemento(double x, double y, int largura, int altura, int vidas , Image imagem)
    {
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.imagem = imagem;
        this.largura = largura;
        this.altura = altura;
    }
    
    /**
     * Método que seta a atual posição do elemento
     * @param x int - Posição X do elemento na matriz da tela 
     * @param y int - Posição Y do elemento na matriz da tela
     */
    public final void setPos(int x, int y)
    {
        this.x = x;
        this.x = y;
    }
    
    /**
     * Método que retorna a quantidade de vidas do elemento
     * @return vidas int - Quantidade de vidas restantes
     */
    protected int getVidas()
    {
        return this.vidas;
    }
    
    /**
     * Método que retorna o estado do elemento
     * @return vivo boolean - True para vivo, false para morto
     */
    public boolean esta_vivo()
    {
        return this.vivo;
    }
    
    /**
     * Método que remove uma vida deste elemento
     */
    public void removeVida()
    {
        this.vidas--;
        
        if(this.vidas == 0)
            this.vivo = false;
    }
    
    /**
     * Método que seta para false a vida de um elemento
     */
    public void destruir()
    {
        this.vivo = false;
    }
    
}