package trabalho1_marcospietrucci;

import java.util.ArrayList;

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
     * @param tamY int - Número de colunas do campo
     */
    public void move(int tamY)
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
                case 0: if(this.y < tamY - 1 && (this.y + Invasor.velocidade >= tamY))
                        {
                            System.out.println("TESTE");
                            this.y++;
                        }
                        else
                            this.y += Invasor.velocidade;
                
                        break;
            
                case 1: if(this.y > 1 && (this.y - Invasor.velocidade <= 0))
                            this.y--;
                        else
                            this.y -= Invasor.velocidade;
                
                        break;
            } 
        } 
    }
    
    /**
     * Método responsável por aumentar a velocidade dos invasores
     * @param invaders ArrayList - Contém todos os objetos dos invasores
     * @param m Musica - Controla os efeitos sonoros
     */
    public static void aumentaVelocidade(ArrayList<Invasor> invaders, Musica m)
    {
        //Temos 11x5 invasores = 55
        //Devo aumentar a velocidade de movimento dos aliens quando abater mais de 15
        Invasor auxInv;
        if(invaders.size() == 50)
        {
            //Velocidade é um atributo estático, por isso aumenta a velocidade de todos ao mesmo tempo
            Invasor.velocidade++;
            m.iniciaTheme2();
        }
        else if(invaders.size() == 20)
        {
            Invasor.velocidade++;
            m.iniciaTheme3();
        }
        
    }
}
