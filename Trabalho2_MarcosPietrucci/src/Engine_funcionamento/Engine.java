/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine_funcionamento;
import Elementos.*;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author marco
 */
public class Engine {
    
    //Separa os elementos em ArrayList Separados
    ArrayList<Barreira> barreiras = new ArrayList<>();
    ArrayList<Invasor> aliens = new ArrayList<>();
    ArrayList<Tiro> tiroPlayer = new ArrayList<>();
    ArrayList<Tiro> tiroAlien = new ArrayList<>();
    Canhao player;
    
    
    public Engine()
    {
    
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
                aliens.add(new Invasor(i, j, 1, 40, 40, new Image("Imagens/plat.png", 40, 40, false, false)));
       
        /* As barreiras ficarão na linha tamX - 40 */
        barreiras.add(new Barreira( 90, tamY - 130, 40, 40, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 270, tamY - 130, 40, 40, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 450, tamY - 130, 40, 40, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        barreiras.add(new Barreira( 630, tamY - 130, 40, 40, 1, new Image("Imagens/plat.png", 80, 50, false, false)));
        
        /* O canhão começa mais ou menos no canto esquerdo */
        player = new Canhao( 200, tamY -20, 80, 80, 1, new Image("Imagens/plat.png", 40, 40, false,false));
        
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
        Invasor aux;
        boolean flag_inversao = false;
        int i, j;
        for(i = 0; i < aliens.size(); i++)
        {
            aliens.get(i).move(tamX);
            
            aux = aliens.get(i);
            //Verificar se preciso descer
            //Caso algum dos invasores esteja prestes a sair da borda, devemos mudar o sentido de seu movimento
            if((Invasor.getDirecao() == 0 && aux.x + aux.largura >= tamX) || (Invasor.getDirecao() == 1 && aux.x <= 0)
                || (aux.x + aux.largura + Invasor.getVelocidade() > tamX - 1 && Invasor.getDirecao() == 0)
                ||(Invasor.getDirecao() == 1 && aux.x - Invasor.getVelocidade() < 0))
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
        for(i = 0; i < tiroPlayer.size(); i++)
        {
            tiroPlayer.get(i).move();
            
            for(j = 0; i < aliens.size(); i++)
            {
                //Verificar se ouve colisão, se tiver, destruir ambos e contar pontos
                if(verifica_colisao(tiroPlayer.get(i), aliens.get(j)))
                {
                    //Remover os elementos envolvidos
                    tiroPlayer.remove(i);
                    aliens.remove(j);
                    
                    //Contar pontos!
                    //player.getPontos();
                
                    //Aumentar a velocidade
                    Invasor.aumentaVelocidade(aliens);
                }
            }
        }
        
        for(i = 0; i < tiroAlien.size(); i++)
        {
            tiroAlien.get(i).move();
            
            if(verifica_colisao(player, tiroAlien.get(i)))
            {
                player.removeVida();
                player.setPos(200, tamY - 50);
            }
        }
        
        //Cria um arrayList com todos os elementos e retorna
        ArrayList<Elemento> obj= new ArrayList<>();
        obj.addAll(aliens);
        obj.addAll(barreiras);
        obj.addAll(tiroPlayer);
        obj.addAll(tiroAlien);
        obj.add(player);
        return obj;    
    }
    
    private boolean verifica_colisao(Elemento a, Elemento b)
    {
        return true;
    }
    
    public void criaTiro()
    {}
    
}
