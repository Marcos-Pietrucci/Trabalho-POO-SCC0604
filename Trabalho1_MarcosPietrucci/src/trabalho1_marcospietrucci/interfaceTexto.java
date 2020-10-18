package trabalho1_marcospietrucci;

import java.util.ArrayList;

/**
 * Classe responsável pela interação com o Output Console do NetBeans
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class interfaceTexto{
    
    /** Atributos que controlam o tamanho da matriz na qual o jogo ocorrerá **/
    //O campo será uma matriz de 15 linhas por 20 colunas
    private final int tamX = 20;
    private final int tamY = 20;
    
    /** Matriz de caracteres que será printada, simulando a tela do jogo **/
    private char tela[][];
    
    /** Lista e objetos de Elementos que serão usados **/
    private ArrayList<Invasor> invaders;
    private ArrayList<Barreira> base;
    private ArrayList<Tiro> tiroPlayer;
    private ArrayList<Tiro> tiroInvader; //Ainda não implementado
    private Canhao jogador;
    private final Musica musica;
    private long tempo_tiro; //Controla o tempo para tocar sons do tiro
    
    /** Indica se o jogo acabou ou não **/
    private boolean jogo = true; 
    
    interfaceTexto() 
    {
        invaders = new ArrayList<>();
        base = new ArrayList<>();
        tiroPlayer = new ArrayList<>();
        tela = new char[tamX][tamY];
        musica = new Musica();
    }
    
    /** Método responsável por deixar o jogo em loop, atualizando a matriz do jogo e travando o Output console
     *  @throws InterruptedException - Por conta do método cls()
     */
    public void iniciaJogo() throws InterruptedException 
    {
        //Inicia a música
        musica.iniciaTheme();
        
        //A cada nova fase, incrementa-se a posição inicial dos invasores
        setElementos(0);
        
        //Variáveis de controle do canhão (pois não é possível ler o teclado de forma funcional nesta etapa)
        int mov = 1, cont_mov = 0;
        
        while(jogo)
        {
           //Core loop do jogo
           escreve_tela();
           processaMovimento(mov);
           cls();
           
           //Reseta a variavel de movimentos do canhao
           cont_mov++;
           if(cont_mov == 10)
           {
               cont_mov = 0;
               if(mov == 1)
                   mov = -1;
               else
                   mov = 1;
           }
           
        }
                
        System.out.println("\nVocê foi derrotado, sua pontuação foi de: " + jogador.getPontos());
    }

    /** Método que instancia todos os elementos do jogo
     *  @param nFase int - Indica em qual fase o jogador está, para que os invasores nasçam avançados
     */
    private void setElementos(int nFase)
    {
        /* Os invasores estão divididos em 11 colunas e 5 linhas*/
        /* Na minha matriz de 15x20 eles devem começar em uma região especifica*/
        int i, j;
        for (i = 0; i <= 4; i++) 
            for (j = 5; j <= 15; j++) 
                invaders.add(new Invasor(i + nFase, j, '&', 1, 1, 0));
            
        /* As barreiras ficarão na linha 27 */
        for (j = 4; j < 20; j += 4)
            base.add(new Barreira(tamX - 3, j, '=', 2, 0));

        /* O canhão começa mais ou menos no canto esquerdo */
        jogador = new Canhao(tamX - 1, 5, (char) 177, 1, 1);

    }

    /** Método responsável por escrever os objetos na tela em suas posições corretas
     */
    private void escreve_tela() 
    {
        Elemento aux;
        int i, j;
        
        //Atualiza a posição dos invasores
        i = 0;
        while (i != invaders.size())
        {
            aux = invaders.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        //Atualiza a posição dos tiros
        i = 0;
        while (i != tiroPlayer.size())
        {
            aux = tiroPlayer.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        //Atualiza a posição barreiras
        i = 0;
        while (i != base.size())
        {
            aux = base.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        //Atualiza a posição do jogador
        tela[jogador.x][jogador.y] = jogador.getSimbol();
        
        //Escreve todo o conteúdo no Output Console
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
    
    
    /** Método responsável por processar o movimento dos elementos
     * @param mov int - Contém o incremento da posição do canhão
     */
    private void processaMovimento(int mov)
    {        
        Invasor auxInv;
        Tiro tiro;
        Barreira auxBase;
        int i,j;  
        
        processa_colisoes();
        
        /******************************** Tiros_jogador ******************************************/
        //Atirar apenas em intervalo de tempo de 1 segundo
        if(System.nanoTime() - tempo_tiro >= 800000000 )
        {
            tiro = new Tiro(jogador.x, jogador.y, (char) 42, 1, 1);
            tiroPlayer.add(tiro);
            
            tempo_tiro = System.nanoTime();
            
            musica.tiro();
        }
        
        //Avançar com todos os tiros
        i = 0;
        while(i < tiroPlayer.size())
        {
            tiro = tiroPlayer.get(i);
            tiro.x--;
            
            if(tiro.x < 0) //Tiro inválido, deve ser retirado
                tiroPlayer.remove(tiro);
            
            i++;
        }
       
   
        /********************************** Invasores ******************************/
        //Pesquisar a localização atual dos invasores e setar o seu proximo movimento
        i = 0;
        while(i != invaders.size())
        {
            auxInv = invaders.get(i);
                     
            //Caso algum dos invasores esteja prestes a sair da borda, devemos mudar o sentido de seu movimento
            if((auxInv.getDirecao() == 0 && auxInv.y == tamY -1) || (auxInv.getDirecao() == 1 && auxInv.y == 0) || (auxInv.y + Invasor.velocidade > tamY - 1 && auxInv.getDirecao()== 0)
                ||(auxInv.getDirecao() == 1 && auxInv.y - Invasor.velocidade < 0))
            {
                //Já sabemos o que fazer com todos os invasores                
                j = 0;
                while(j != invaders.size())
                {
                    //Internamente, a classe se encarrega de mover os invasores para baixo
                    auxInv = invaders.get(j);
                    auxInv.inverteSentido(); //Inverter sua direção
                    auxInv.move(tamY);
                    j++;
                }
                return;
            }
            else if(auxInv.x == tamX -1) //Caso algum invasor chegue até a posição do jogador, o jogo acaba
            {
                jogo = false;
                return;
            }
            i++;
        }
        
        //Se modificações não foram necessárias, posso simplesmente mover os invasores
        i = 0;
        while(i != invaders.size() && jogo)
        {
            auxInv = invaders.get(i);
            auxInv.move(tamY);
            i++;
        }
        
        
        /********************************** Canhão ***********************************************/
        /* Ler as teclas e processar o movimento do personagem */
        /* Não é possível. Com o OutputConsole do NetBeans, apenas o Scanner funciona, e não é viável o jogador ter que apertar "ENTER" toda vez que inserir um comando */
        /* Para usar KeyEvents e a interface KeyListener é preciso interface gráfica */
        
        /* Vou programar alguns movimentos para o canhão */
        jogador.y = jogador.y + mov;
        
        
        //Remover todas as barreiras cuja vida seja 0
        i = 0;
        while(i < base.size() && jogo)
        {
            auxBase = base.get(i);
            
            if(auxBase.getVidas() == 0)
                base.remove(auxBase);
            
            i++;
        }
        
    }
    
    /** Método responsável por processar todas as colisões do jogo
     */
    private void processa_colisoes()
    {
        Barreira auxBase;
        Invasor auxInv;
        Tiro tiro;
        int i, j;
        
        /********** Colisão tiro-invasor ********************/
        i = 0;
        while(i < tiroPlayer.size())
        {
            j = 0;
            tiro = tiroPlayer.get(i);
            
            while(j < invaders.size())
            {
                auxInv = invaders.get(j);
                
                //Testar colisão entre todos os tiros e todos os invasores
                if(tiro.x == auxInv.x && tiro.y == auxInv.y)
                {
                    //Colisão!!
                    //Vou retirar ambos os elementos de seus ArrayList
                    tiroPlayer.remove(tiro);
                    invaders.remove(auxInv);
                    
                    //O jogador ganha pontos!!
                    jogador.aumentaPontos();
                    
                    //Toca o som de invasor destruido
                    musica.destruiInvasor();
                    
                    //Aumentar a velocidade dos invasores conforme vão sendo abatidos
                    Invasor.aumentaVelocidade(invaders, musica);
                }
                j++;
            }
            i++;
        }
        
        
        /****************************** Colisão Barreira-Tiro **************************************/
        i = 0;
        while(i < base.size())
        {
            j = 0;
            auxBase = base.get(i);
            
            while(j < tiroPlayer.size())
            {
                tiro = tiroPlayer.get(j);
                
                //Testar colisão entre todos os tiros e todos os invasores
                if(tiro.x == auxBase.x && tiro.y == auxBase.y)
                {
                    //Colisão!!
                    //Devo retirar uma vida da barreira e remover o tiro
                    tiroPlayer.remove(tiro);
                    auxBase.removeVida();
                }
                j++;
            }
            i++;
        }
    }
    

    /** Método responsável por limpar o console
     * @throws InterruptedException - Por conta do comando Thread.sleep
     */
    private void cls() throws InterruptedException
    {
        int i,j;
        
        //Dando este sleep causa o efeito de refresh
        Thread.sleep(300); 
        
        //Além disso devo limpar a matriz tela
        for(i = 0; i < tamX; i++)
            for(j = 0; j< tamY; j++)
                tela[i][j] = (char) 0;
    }
}
