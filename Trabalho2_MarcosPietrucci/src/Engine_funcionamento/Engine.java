/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine_funcionamento;
import Elementos.*;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 *
 * @author marco
 */
public class Engine {
    
    //Separa os elementos em ArrayList Separados
    private ArrayList<Barreira> barreiras = new ArrayList<>();
    private ArrayList<Invasor> aliens = new ArrayList<>();
    private ArrayList<Tiro> tiroPlayer = new ArrayList<>();
    private ArrayList<Tiro> tiroAlien = new ArrayList<>();
    private Canhao player;
    
    private ArrayList<KeyCode> entradas= new ArrayList<>();
    
    private long tempo_tiro;
    
    public Engine()
    {
        tempo_tiro = System.currentTimeMillis();
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
        
        /* Os invasores estão divididos em 11 colunas e 5 linhas*/
        int i, j;
        for (j = 80; j < 340; j += 60)
            for (i = 80; i < 720 ; i+= 60)
                aliens.add(new Invasor(i, j, 30, 30, 1, new Image("Imagens/plat.png", 30, 30, false, false)));
       
        /* As barreiras ficarão na linha tamX - 40 */
        barreiras.add(new Barreira( 90, tamY - 130, 80, 50, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 270, tamY - 130, 80, 50, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 450, tamY - 130, 80, 50, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 630, tamY - 130, 80, 50, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        /* O canhão começa mais ou menos no canto esquerdo */
        player = new Canhao( 200, tamY - 50, 40, 40, 1, 4, new Image("Imagens/plat.png", 40, 40, false,false));
        
        //Adicionar os outros elementos no arrayList       
        objetos.addAll(aliens);
        objetos.addAll(barreiras);
        objetos.add(player);
        return objetos;
    }
    
    private void  processa_movimento_jogador()
    {
        if(entradas.contains(KeyCode.LEFT))
        {
            player.x -= player.getVelocidade();
        }
        if(entradas.contains(KeyCode.RIGHT))
        {
            player.x += player.getVelocidade();
        }
        if(entradas.contains(KeyCode.SPACE))
        {
            this.criaTiro_player();
        }
    }
    
    /**
     * Processa colisões e movimentos do jogo
     * @return objetos ArrayList<Elementos> - Arraylist contendo todos os elementos do jogo
     * @param tamX int - Número de colunas da telinha 
     * @param tamY int - Número de linhas da telinha
     */
    public ArrayList<Elemento> processaJogo(int tamX, int tamY)
    {   
        processa_movimento_jogador();
        
        
        ArrayList<Invasor> remove_inv = new ArrayList<>();
        ArrayList<Tiro> remove_tiro = new ArrayList<>();
        Invasor aux_inv;
        Tiro aux_tiro;
        
        boolean flag_inversao = false;
        int i, j, aux_tamanho1, aux_tamanho2;
        
        for(i = 0; i < aliens.size(); i++)
        {
            aliens.get(i).move(tamX);
            
            aux_inv = aliens.get(i);
            //Verificar se preciso descer
            //Caso algum dos invasores esteja prestes a sair da borda, devemos mudar o sentido de seu movimento
            if((Invasor.getDirecao() == 0 && aux_inv.x + aux_inv.largura >= tamX) || (Invasor.getDirecao() == 1 && aux_inv.x <= 0)
                || (aux_inv.x + aux_inv.largura + Invasor.getVelocidade() > tamX - 1 && Invasor.getDirecao() == 0)
                ||(Invasor.getDirecao() == 1 && aux_inv.x - Invasor.getVelocidade() < 0))
            {
                //Já sabemos o que fazer com todos os invasores                
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
        
        /********************* Movimento dos tiros *************************/
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
                        //Remover os elementos envolvidos
                        remove_tiro.add(aux_tiro);   //Adiciona numa coleção de removidos
                        remove_inv.add(aux_inv);
                       
                        //Contar pontos!
                        //player.getPontos();

                        //Aumentar a velocidade
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
                    player.setPos(200, tamY - 50);
                    
                    remove_tiro.add(aux_tiro);
                    
                    //Adicionar aqui variável que indica fim de jogo
                    //Talvez fazer uma transição bonitinha, talvez nao
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
            tiroPlayer.removeAll(remove_tiro);
        }      
        /*********************************************************************/
        
        //Cria um arrayList com todos os elementos e retorna
        ArrayList<Elemento> obj = new ArrayList<>();
        obj.addAll(aliens);
        obj.addAll(barreiras);
        obj.addAll(tiroPlayer);
        obj.addAll(tiroAlien);
        obj.add(player);
        return obj;    
    }
    
    private boolean verifica_colisao(Elemento a, Elemento b)
    {   

        Rectangle2D elem_A = new Rectangle2D(a.x, a.y - a.altura, a.largura, a.altura);
        Rectangle2D elem_B = new Rectangle2D(b.x, b.y - b.altura, b.largura, b.altura);
        
        if(elem_A.intersects(elem_B))
            return true;
        else
            return false;

          
    }
    
    public void criaTiro_player()
    {
        if(System.currentTimeMillis() - tempo_tiro > 800)
         {
            tiroPlayer.add(new Tiro(player.x + (player.largura)/2 - 5, player.y - player.altura/2 , 5, 20, 1, 6, 1, new Image("Imagens/plat.png", 5, 20, false, false)));
            tempo_tiro = System.currentTimeMillis();
        }
    }

    public void removeEntrada(KeyCode code) 
    {
        if(entradas.contains(code))
            entradas.remove(code);     
    }

    public void addEntrada(KeyCode code) 
    {
        if(!entradas.contains(code))
            entradas.add(code); 
    }
    
}
