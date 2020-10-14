/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1_marcospietrucci;

/**
 *
 * @author marco
 */
public class Canhao extends Elemento {
    
    //0 para a direita, 1 para a esquerda
    private int direcao;
    
    Canhao(int x, int y, char simbol, int vidas, int velocidade)
    {
        super(x,y,simbol,vidas,velocidade);
    }
    
    
    public void setDirecao(int direcao)
    {
        this.direcao = direcao;
    }
    
    public int getDirecao()
    {
        return this.direcao;
    }
    
    
    public void move(int direcao)
    {               
        switch (direcao)
        {
            case 0: this.y += this.velocidade;
            
            case 1: this.y -= this.velocidade;
        } 
    }
    
}
