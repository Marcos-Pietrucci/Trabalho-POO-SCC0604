package trabalho1_marcospietrucci;

/**
 * Classe que define os invasores, sendo filha de "Elemento"
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class Invasor extends Elemento{
    
    /** 0: mover para direita
       1: mover para esquerda
    */
    private int direcao;
    
    /** 0: invasor anda em linha reta
       1: invasor desce 1 linha
    */
    private int descer;
    
    /** Controla a mudança do caractere do invasor */
    private boolean alt_simbol = false;
    private final char simbol2 = '$';
    
    
    Invasor(int x, int y, char simbol, int vidas, int velocidade, int direcao)
    {
        super(x,y,simbol,vidas,velocidade);
        this.direcao = direcao;
    }
    
    /**
     * Método que retorna a atual direção do invasor
     * @return direcao int - Se 0, o invasor está andando para a direita; Se 1, o invasor está andando para a esquerda
     */
    public int getDirecao()
    {
        return this.direcao;
    }
    
    
    /**
     * Método que retorna um símbolo para o invasor. Existem 2 símbolos diferentes que alternam entre si.
     * @return simbol char - Retorna um símbolo que representa o invasor na tela. Existem 2 símbolos diferentes que se alternam entre si
     */
    @Override
    public char getSimbol()
    {
        /* Decidi fazer esta alternância para simular o movimento que os invasores fazem, ao se moverem, no jogo original */
        this.alt_simbol = !this.alt_simbol;
        if(this.alt_simbol)
        {
            return this.simbol;
        }
        else
            return this.simbol2;
    }
    
    /**
     * Método que inverte o sentido de movimento do invasor ao inverter o valor da variável direcao. Além disso, este método programa o invasor
     * para se aproximar uma unidade do canhão.
     * 
     */
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
    }    
    
    /**
     * Método que efetivamente atualiza a posição do invasor 
     */
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
