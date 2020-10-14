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
    private final int tamX = 15;
    private final int tamY = 20;
    
    /** Matriz de caracteres que será printada, simulando a tela do jogo **/
    private char tela[][];
    
    /** Lista e objetos de Elementos que serão usados **/
    private ArrayList<Invasor> invaders;
    private ArrayList<Barreira> base;
    private Canhao player;
    
    /** Indica se o jogo acabou ou não **/
    private boolean jogo = true; 
    
    /** Construtor
     */
    interfaceTexto() 
    {
        invaders = new ArrayList<>();
        base = new ArrayList<>();
        tela = new char[tamX][tamY];
    }
    
    /** Método responsável por deixar o jogo em loop, atualizando a matriz do jogo
     *  e limpando o Output console
     *  @throws InterruptedException - Por conta do método cls()
     */
    public void iniciaJogo() throws InterruptedException 
    {
        //Inicia a música
        Musica m = new Musica();
        m.startTheme();
        
        //A cada nova fase, incrementa-se a posição inicial dos invasores
        setElementos(0);
        
        while(jogo)
        {
           //Core loop do jogo
           processaMovimento();
           cls();
           escreve_tela();
        }
                
        System.out.println("Você foi derrotado, sua pontuação foi de: " + player.getPontos());
    }

    /** Método que vai instanciar todos os elementos do jogo
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
            base.add(new Barreira(tamX - 3, j, '=', 1, 1));

        /* O canhão começa mais ou menos no canto esquerdo */
        player = new Canhao(tamX - 1, 5, (char) 177, 1, 1);

    }

    /** Método responsável por escrever os objetos na tela em suas posições corretas
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
     * Nele é decidido se os invasores vão para a esquerda, direta ou para baixo
     */
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
            else if(auxInv.x == tamX -1) //Caso algum invasor chegue até a posicão do jogador o jogo acaba
            {
                jogo = false;
                break;
            }
            i++;
        }
        
        //Modificações na direção não foram necessárias, posso simplesmente mover os invasores
        i = 0;
        while(i != invaders.size() && !ready && jogo)
        {
            auxInv = invaders.get(i);
            auxInv.move();
            i++;
        }
        
        /* Ler as teclas e processar o movimento do personagem */
        /* Não é possível. Com o terminal, apenas o Scanner funciona, e não é viável o jogador ter que apertar "ENTER" toda vez que inserir um comando */
        /* Para usar KeyEvents e a interface KeyListener é preciso interface gráfica */
    
    }

    /** Método responsável por limpar o console
     * @throws InterruptedException - Por conta do comando Thread.sleep
     */
    private void cls() throws InterruptedException
    {
        int i,j;
        
        //Dando este sleep causa o efeito de refresh
        Thread.sleep(500); 
        
        //Além disso devo limpar a matriz tela
        for(i = 0; i < tamX; i++)
            for(j = 0; j< tamY; j++)
                tela[i][j] = (char) 0;
        
    }
}
