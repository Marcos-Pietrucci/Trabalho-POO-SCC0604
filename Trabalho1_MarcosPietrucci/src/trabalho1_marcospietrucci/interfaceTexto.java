package trabalho1_marcospietrucci;

import java.util.ArrayList;

/**
 * Classe que trabalhará diretamente com a interface textual do netBeans
 *
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class interfaceTexto{

    private final int tamX = 15, tamY = 20;
    
    private char tela[][] = new char[15][20];
    
    private ArrayList<Invasor> invaders;
    private ArrayList<Barreira> base;
    private Canhao player;

    interfaceTexto() 
    {
        invaders = new ArrayList<>();
        base = new ArrayList<>();
    }

    public void iniciaJogo() throws InterruptedException 
    {
        setElementos();
        for(;;)
        {
           processaMovimento();
           cls();
           escreve_tela();
           Thread.sleep(500);
        }
    }

    /**
     * Método que vai instanciar todos os elementos do jogo
     */
    private void setElementos()
    {
        /* Os invasores estão divididos em 11 colunas e 5 linhas*/
        /* Na minha matriz de 15x20 eles devem começar em uma região especifica*/
        int i, j;
        for (i = 0; i <= 4; i++) 
            for (j = 5; j <= 15; j++) 
                invaders.add(new Invasor(i, j, '&', 1, 1, 0));
            

        //As barreiras ficarão na linha 27
        for (j = 4; j < 20; j += 4)
            base.add(new Barreira(tamX - 3, j, '=', 1, 1));

        //O canhão começa mais ou menos no canto esquerdo
        player = new Canhao(tamX - 1, 5, (char) 177, 1, 1);

    }

    /**
     * Método responsável por escrever os objetos na tela em suas posições corretas
     */
    private void escreve_tela() 
    {
        Elemento aux;
        int i, j;
        
        //Atualiza a posição barreiras
        i = 0;
        while (i != base.size())
        {
            aux = base.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        //Atualiza a posição dos invasores
        //Os invasores podem destruir a barreira por estar em cima dela
        i = 0;
        while (i != invaders.size())
        {
            aux = invaders.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        
        //Atualiza a posição do player
        tela[player.x][player.y] = player.getSimbol();
        
        for (i = 0; i < tamX; i++) 
        {
            for (j = 0; j < tamY; j++) 
            {
                if (tela[i][j] == 0) 
                    System.out.print(" ");
                 else
                    System.out.print(tela[i][j]);
            }
            System.out.print("\n");
        }
    }

    private void processaMovimento()
    {        
        Invasor auxInv;
        int i,j;
        boolean ready = false; //Evitar operações desnecessárias
        
        
        
        //Pesquisar a localização atual dos invasores e setar o seu proximo movimento
        i = 0;
        while(i != invaders.size())
        {
            auxInv = invaders.get(i);
                     
            //Caso algum dos invasores esteja prestes a sair da borda, devemos mudar o sentido de seu movimento
            if((auxInv.getDirecao() == 0 && auxInv.y == tamY -1) || (auxInv.getDirecao() == 1 && auxInv.y == 0))
            {
                //Já sabemos o que fazer com todos os invasores                
                j = 0;
                while(j != invaders.size())
                {
                    //Internamente, a classe se encarrega de mover os invasores para baixo
                    auxInv = invaders.get(j);
                    auxInv.inverteSentido(); //Inverter sua direção
                    auxInv.move();
                    j++;
                }
                ready = true;
                break;
            }
         
            i++;
        }
        
        
        //Modificações na direção não foram necessárias, posso simplesmente mover os invasores
        i = 0;
        while(i != invaders.size() && !ready)
        {
            auxInv = invaders.get(i);
            auxInv.move();
            i++;
        }
        
        /** Ler as teclas e processar o movimento do personagem **/
        /** Não é possível. Com o terminal, apenas o Scanner funciona, e não é viável o jogador ter que apertar "ENTER" toda vez que inserir um comando **/
        /** Para usar KeyEvents e a interface KeyListener é preciso interface gráfica**/
    }


    private void cls() 
    {
        int i,j;
        System.out.println("\n\n\n");       
        
        //Além disso devo limpar a matriz tela

        for(i = 0; i < tamX; i++)
            for(j = 0; j< tamY; j++)
                tela[i][j] = (char) 0;
            
    }
    
   
}
