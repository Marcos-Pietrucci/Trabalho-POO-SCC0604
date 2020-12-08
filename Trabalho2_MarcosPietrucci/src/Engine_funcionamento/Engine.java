/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine_funcionamento;
import Elementos.*;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 *
 * @author marco
 */
public class Engine {
    
    //Variaveis de controle externo
    public boolean ganhou;
    public boolean jogoAtivo;
    
    //Separa os elementos em ArrayList Separados
    private ArrayList<Barreira> barreiras = new ArrayList<>();
    private ArrayList<Invasor> aliens = new ArrayList<>();
    private ArrayList<Tiro> tiroPlayer = new ArrayList<>();
    private ArrayList<Tiro> tiroAlien = new ArrayList<>();
    private Canhao player;
    
    private ArrayList<KeyCode> entradas= new ArrayList<>();
    
    private long tempo_tiro_player;
    private long tempo_tiro_alien;
    
    public Engine()
    {
        tempo_tiro_player = System.currentTimeMillis();
        tempo_tiro_alien = System.currentTimeMillis();
        ganhou = false;
        jogoAtivo = true;
    }
    
    /**
     * Método que instancia todos os elementos do jogo
     * @param tamX int - Número de colunas da telinha 
     * @param tamY int - Número de linhas da telinha
     * @return objetos ArrayList<Elementos> - Arraylist contendo todos os elementos do jogo
     */
    public ArrayList<Elemento> criaElementos(int tamX, int tamY)
    {
        ArrayList<Elemento> objetos = new ArrayList<>();
        
        //Imagens a serem linkadas
        Image invasor1 = new Image("Imagens/alien1.png", 40, 40, false, false);
        Image invasor2 = new Image("Imagens/alien2.png", 40, 40, false, false);
        Image invasor3 = new Image("Imagens/alien3.png", 40, 40, false, false);
        Image barreiraImg = new Image("Imagens/barreira.png", 100, 50, false, false);
        Image img = invasor1;
        
        
        /* Os invasores estão divididos em 11 colunas e 5 linhas*/
        int cont_invasor = 0;
        int i, j;
        for (j = 100; j < 400; j += 60)
            for (i = 80; i < 740; i += 60)
            {
                if(cont_invasor == 11)
                    img = invasor2;
                else if(cont_invasor == 33)
                    img = invasor3;
                aliens.add(new Invasor(i, j, 40, 40, 1, img));
                
                cont_invasor++;
            }
        //Gerar uma certa aleatoriedade no tiro dos aliens
        Collections.shuffle(aliens);
        
        /* As barreiras ficarão na linha tamX - 40 */
        barreiras.add(new Barreira( 90, tamY - 130, 100, 50, 8, barreiraImg));
        
        barreiras.add(new Barreira( 330, tamY - 130, 100, 50, 8, barreiraImg));
        
        barreiras.add(new Barreira( 570, tamY - 130, 100, 50, 8, barreiraImg));
        
        barreiras.add(new Barreira( 810, tamY - 130, 100, 50, 8, barreiraImg));
        
        /* O canhão começa mais ou menos no canto esquerdo */
        player = new Canhao(300, tamY - 50, 40, 40, 3, 4,
                    new Image("Imagens/nave.png", 40, 40, false,false));
        
        //Adicionar os outros elementos no arrayList       
        objetos.addAll(aliens);
        objetos.addAll(barreiras);
        objetos.add(player);
        return objetos;
    }
    
    /**
     * Processa colisões e movimentos do jogo
     * @return objetos ArrayList<Elementos> - Arraylist contendo todos os elementos do jogo
     * @param tamX int - Número de colunas da telinha 
     * @param tamY int - Número de linhas da telinha
     */
    public ArrayList<Elemento> processaJogo(int tamX, int tamY)
    {
        //Verificar se o jogo está ativo
        if(aliens.size() == 0)
        {
            ganhou = true;
            jogoAtivo = false;
        } 
        if(!player.esta_vivo())
        {
            ganhou = false;
            jogoAtivo = false;
        }
        
        //Processa o movimento do player
        processa_movimento_jogador(tamX);
        
        //Gera os tiros dos aliens
        gera_tiro_alien();
        
        //Processa os movimentos e as colisões do jogo
        processa_colisoes_e_movimento(tamX, tamY);      
        
        //Verifica o estado das barreiras
        verifica_barreiras();
        
        //Cria um arrayList com todos os elementos e retorna
        ArrayList<Elemento> obj = new ArrayList<>();
        obj.addAll(aliens);
        obj.addAll(barreiras);
        obj.addAll(tiroPlayer);
        obj.addAll(tiroAlien);
        obj.add(player);
        return obj;    
    }
    
    /**
     * Método que processa as colisões e os movimentos do jogo
     * @param tamX int - Número de colunas da telinha
     * @param tamY int - Número de linhas da telinha
     */
    public void processa_colisoes_e_movimento(int tamX, int tamY)
    {
        //Array para remoção de elementos
        ArrayList<Invasor> remove_inv = new ArrayList<>();
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        ArrayList<Tiro> remove_tiro2 = new ArrayList<>();
        ArrayList<Barreira> remove_bar = new ArrayList<>();
        
        //Variáveis auxiliares
        Invasor aux_inv;
        Tiro aux_tiro, aux_tiro2;
        Barreira aux_barreira;
        boolean flag_inversao = false;
        int i, j;
        
        for(i = 0; i < aliens.size(); i++)
        {
            aliens.get(i).move(tamX);
            
            aux_inv = aliens.get(i);
                        //Caso algum dos invasores esteja prestes a sair da borda, devemos
            // descer emudar o sentido de seu movimento
            if((Invasor.getDirecao() == 0 && aux_inv.x + aux_inv.largura >= tamX)
                || (Invasor.getDirecao() == 1 && aux_inv.x <= 0)
                || (aux_inv.x + aux_inv.largura + Invasor.getVelocidade() > tamX - 1
                && Invasor.getDirecao() == 0)
                ||(Invasor.getDirecao() == 1 && aux_inv.x - Invasor.getVelocidade() < 0))
            {
                //Temos que inverter o sentido dos invasores              
                flag_inversao = true;
            
            }
        }
        
        if(flag_inversao == true)
        {
            Invasor.inverteSentido();
            for(i = 0; i < aliens.size(); i++)
            {
                aliens.get(i).move(tamX);
            }
        }
        
        /********************* Movimento dos tiros do jogador *************************/
        for(i = 0; i < tiroPlayer.size(); i++)
        {
            aux_tiro = tiroPlayer.get(i);
            
            //Verifica se o tiro é válido ao movê-lo
            if(aux_tiro.move(tamY))
            {
                for(j = 0; j < aliens.size(); j++)
                {
                    aux_inv = aliens.get(j);
                    
                    //Verificar se ouve colisão, se tiver, destruir ambos e contar pontos
                    if(verifica_colisao(tiroPlayer.get(i), aliens.get(j)))
                    {
                        //Adiciona numa coleção de removidos
                        //Marcar os elementos para remoção
                        remove_tiro.add(aux_tiro);   
                        remove_inv.add(aux_inv);
                       
                        //Contar pontos!
                        player.ganhaPontos();

                        //Aumentar a velocidade dos aliens
                        Invasor.aumentaVelocidade(aliens);  
                    }
                }
            }
            else
            {
                remove_tiro.add(aux_tiro);
            }
        }
        
        //Remover todos os elementos marcados como removidos
        if(!remove_inv.isEmpty())
            aliens.removeAll(remove_inv);
        if(!remove_tiro.isEmpty())
        {
            tiroPlayer.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        
        /************ Verificar colisões dos tiros dos Aliens *****************/
        for(i = 0; i < tiroAlien.size(); i++)
        {
            aux_tiro = tiroAlien.get(i);
            
            if(aux_tiro.move(tamY))
            {
                if(verifica_colisao(player, tiroAlien.get(i)))
                {
                    player.removeVida();
                    player.setPos(tamX - 50, 300);
                    
                    remove_tiro.add(aux_tiro);
                    
                    /*
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event ->
                        label.setText("Finished: 1 second elapsed");
                    );  
                    pause.play();*/
                }
            }
            else
            {
                remove_tiro.add(aux_tiro);
            }
        }
        
        //Remover todos os elementos marcados como removidos
        if(!remove_tiro.isEmpty())
        {
            tiroAlien.removeAll(remove_tiro);
            remove_tiro.clear();
        }      
        /**********************************************************************/
        
        /****************** Verificar colisões entre tiros ********************/
        for(i = 0; i < tiroPlayer.size(); i++)
        {
            aux_tiro = tiroPlayer.get(i);           
            for(j = 0; j < tiroAlien.size(); j++)
            {
                aux_tiro2 = tiroAlien.get(j);
                    
                //Verificar se ouve colisão, se tiver, destruir ambos
                if(verifica_colisao(aux_tiro,aux_tiro2))
                {
                    //Remover os elementos envolvidos
                    remove_tiro.add(aux_tiro);   //Adiciona numa coleção de removidos
                    remove_tiro2.add(aux_tiro2);
                }     
            }
        }
        
        //Remover todos os elementos marcados como removidos
        if(!remove_tiro.isEmpty())
        {
            tiroPlayer.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        if(!remove_tiro2.isEmpty())
        {
            tiroAlien.removeAll(remove_tiro2);
            remove_tiro2.clear();
        }
        /**********************************************************************/
        
        /******** Verificar colisões entre tiros do jogador e barreira ********/
        for(i = 0; i < tiroPlayer.size(); i++)
        {
            aux_tiro = tiroPlayer.get(i);
            
            for(j = 0; j < barreiras.size(); j++)
            {
                if(verifica_colisao(aux_tiro, barreiras.get(j)))
                {
                    //Marca o tiro para remoção
                    remove_tiro.add(aux_tiro);
                    
                    //Remove uma vida
                    barreiras.get(j).removeVida();
                }
            }
        }
        
        //Remove todos tiros marcados
        if(!remove_tiro.isEmpty())
        {
            tiroPlayer.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        /**********************************************************************/
        
        /******** Verificar colisões entre tiros dos aliens e barreira ********/
        for(i = 0; i < tiroAlien.size(); i++)
        {
            aux_tiro = tiroAlien.get(i);
            
            for(j = 0; j < barreiras.size(); j++)
            {
                if(verifica_colisao(aux_tiro, barreiras.get(j)))
                {
                    //Marca o tiro para remoção
                    remove_tiro.add(aux_tiro);
                    
                    //Remove uma vida
                    barreiras.get(j).removeVida();
                }
            }
        }
        
        //Remove todos tiros marcados
        if(!remove_tiro.isEmpty())
        {
            tiroAlien.removeAll(remove_tiro);
            remove_tiro.clear();
        }
        /**********************************************************************/
        
        /******** Verificar colisões entre aliens e barreira ********/
        for(i = 0; i < aliens.size(); i++)
        {
            aux_inv = aliens.get(i);
            
            for(j = 0; j < barreiras.size(); j++)
            {
                aux_barreira = barreiras.get(j);
                if(verifica_colisao(aux_barreira, aux_inv))
                {
                    //Marca o tiro para remoção
                    remove_bar.add(aux_barreira);
                }
            }
        }
        
        //Remove todos tiros marcados
        if(!remove_bar.isEmpty())
        {
            barreiras.removeAll(remove_bar);
            remove_bar.clear();
        }
        /**********************************************************************/
    }
    
    /**
     * Método que gera os tiros dos alienígenas
     */
    private void gera_tiro_alien()
    {
        //Variáveis auxiliares
        Invasor aux_inv;
        int i;
        boolean gerou = false;
        
        //Quero gerar os tiros com uma certa aleatoriedade na cadencia
        int valor = (int) Math.floor(Math.random() * 501) + 1500; 
        Image img = new Image("Imagens/tiro.png", 3, 20, false, false);
        
        if(System.currentTimeMillis() - tempo_tiro_alien > valor)
        {
            //Gerar um tiro na coluna do jogador
            for(i = 0 ; i < aliens.size() && !gerou; i++)
            {
                aux_inv = aliens.get(i);
                if(Math.abs(aux_inv.x - player.x) < 10.0)
                {
                    //Se o alien estiver suficientemente proximo do jogador
                    tiroAlien.add(new Tiro(aux_inv.x + (aux_inv.largura)/2, 
                            aux_inv.y + aux_inv.altura/2 , 3, 20, 1, 4, 0, img));
                    gerou = true;
                }
            }
            
            //Gerar um tiro aleatório!
            valor = (int) Math.floor(Math.random() * (aliens.size()));
            aux_inv = aliens.get(valor);
            tiroAlien.add(new Tiro(aux_inv.x + (aux_inv.largura)/2, 
                            aux_inv.y + aux_inv.altura/2 , 3, 20, 1, 4, 0, img));
            
            //Significa que não foi possível atirar perto do jogador
            if(!gerou)
            {
                //Gerar mais um tiro aleatório
                valor = (int) Math.floor(Math.random() * (aliens.size()));
                aux_inv = aliens.get(valor);
                tiroAlien.add(new Tiro(aux_inv.x + (aux_inv.largura)/2, 
                            aux_inv.y + aux_inv.altura/2 , 3, 20, 1, 4, 0, img));
            }
            
            tempo_tiro_alien = System.currentTimeMillis();
        }
    }
    
    /**
     * Método que verifica o estado das barreiras, mudando imagens se necessário
     */
    private void verifica_barreiras()
    {
        Barreira aux;
        int i;
        
        for(i = 0; i < barreiras.size(); i++)
        {
            aux = barreiras.get(i);
            
            //Se a barreira estiver marcada como morta            
            if(!aux.esta_vivo())
            {
                barreiras.remove(aux);
            }
            //Verificar as vidas e atualizar a imagem
            else if(aux.getVidas() >= 4 && aux.getVidas() <= 6 )
                aux.setImagem(new Image("Imagens/barreira1.png", 100, 50, false, false));
            else if(aux.getVidas() >= 1 && aux.getVidas() <= 3  )
                aux.setImagem(new Image("Imagens/barreira2.png", 100, 50, false, false));
        }
    }
    
    /**
     * Método que processa os movimentos do jogador contidos na lista de teclas
     * @param tamX int - Número de colunas da telinha
     */
    private void  processa_movimento_jogador(int tamX)
    {
        if(entradas.contains(KeyCode.LEFT) && player.x > 0)
        {
            player.x -= player.getVelocidade();
        }
        if(entradas.contains(KeyCode.RIGHT) && player.x + player.largura < tamX)
        {
            player.x += player.getVelocidade();
        }
        if(entradas.contains(KeyCode.SPACE))
        {
            this.criaTiro_player();
        }
    }
    
    /**
     * Método que verifica a colisão entre dois elementos
     * @param a Elemento - Elemento cuja intersecção será testada
     * @param b Elemento - Elemento cuja intersecção será testada
     * @return boolean - True caso tenha colisão, false caso contrário
     */
    private boolean verifica_colisao(Elemento a, Elemento b)
    {   
        Rectangle2D elem_A = new Rectangle2D(a.x, a.y - a.altura, a.largura, a.altura);
        Rectangle2D elem_B = new Rectangle2D(b.x, b.y - b.altura, b.largura, b.altura);
        
        if(elem_A.intersects(elem_B))
            return true;
        else
            return false;   
    }
    
    /**
     * Método que cria os tiros do jogador
     */
    public void criaTiro_player()
    {
        if(System.currentTimeMillis() - tempo_tiro_player > 800)
        {
            tiroPlayer.add(new Tiro(player.x + (player.largura)/2 - 5, 
                    player.y - player.altura/2 , 3, 20, 1, 6, 1,
                    new Image("Imagens/tiro.png", 3, 20, false, false)));
            tempo_tiro_player = System.currentTimeMillis();
        }
    }
    
    /**
     * Método que remove uma tecla da lista de entradas
     * @param code KeyCode - Código da tecla que foi "solta"
     */
    public void removeEntrada(KeyCode code) 
    {
        if(entradas.contains(code))
            entradas.remove(code);     
    }
    
    /**
     * Método que adiciona uma tecla na lista de entradas
     * @param code KeyCode - Código da tecla que foi pressionada
     */
    public void addEntrada(KeyCode code) 
    {
        if(!entradas.contains(code))
            entradas.add(code); 
    }
}
