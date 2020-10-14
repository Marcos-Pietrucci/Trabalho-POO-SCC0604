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
public class Invasor extends Elemento{
    
    //0 é mover para direita, 1 mover para esquerda;
    private int direcao;
    
    //0 para invasor andar em linha reta, 1 para se descer
    private int descer;
    
    
    Invasor(int x, int y, char simbol, int vidas, int velocidade, int direcao)
    {
        super(x,y,simbol,vidas,velocidade);
        this.direcao = direcao;
    }
    
    public int getDirecao()
    {
        return this.direcao;
    }
    
    public void inverteSentido()
    {
        //Mudar de direcao significa que o invasor deve descer um degrau
        this.descer = 1;
        
        if(this.direcao == 1)
        {
            this.direcao = 0;
        }
        else
            this.direcao = 1;
        
        this.x++;
        
    }
    
    
    
    /**
     * Método que aumenta a velocidade dos invasores
     */
    public void aumentaVelocidade()
    {
    
    }
    
    public void move()
    {   
        if(this.descer == 1)
        {
            this.x++;
            this.descer = 0;
        }
        else
        {
            switch (direcao)
            {
                case 0: this.y += this.velocidade;
                        break;
            
                case 1: this.y -= this.velocidade;
                        break;
            } 
        }
            
        
    }
}
