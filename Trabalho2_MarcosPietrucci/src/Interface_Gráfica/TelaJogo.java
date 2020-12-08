package Interface_Gráfica;

import Engine_funcionamento.Engine;
import java.util.ArrayList;
import Elementos.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Classe que controla te gerencia toda a interface gráfica do jogo
 * @author Marcos Pietrucci
 * @since dec 2020
 */
public class TelaJogo extends Application {
    
    // Varíaveis necessárias para a tela
    private GraphicsContext gc;
    private Scene scene;
    private final int tamX = 1000, tamY = 800;

    // Classe que controla a engine do jogo
    private final Engine engine;
    
    //ArrayList com todos os elementos
    private ArrayList<Elemento> objetos;
    
    //O canhão do jogador
    Canhao player;
    
    //Imagem de fundo do jogo
    Image imgFundo = new Image("Imagens/fundo.png", tamX, tamY, false, false);
    
    /**
     * Método incial da tela, o qual inicia as configurações
     * @param primaryStage Stage - Tela do jogo
     */
    @Override
    public void start(Stage primaryStage) 
    {
        /* Criando variáveis necessárias para a tela */
        Group root = new Group();
        scene = new Scene(root, tamX, tamY);
        Canvas canvas = new Canvas(tamX, tamY);        
        gc = canvas.getGraphicsContext2D();          
        root.getChildren().add(canvas);
        
        /* Customizando a tela */
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("Imagens/icone.png"));
                    
        //Invoca o método que lê as teclas
        ler_Teclas();
        
        //Preparar fontes
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));
        
        //Ativar gameLoop
        game_loop(primaryStage);
    }

    public TelaJogo()
    {
        //Instanciar todos os objetos necessários
        this.engine = new Engine();
        this.objetos = new ArrayList<>();
        
        objetos = engine.criaElementos(tamX, tamY);
        
        player = (Canhao) objetos.get(objetos.size() - 1);
    }
    
    /**
     * Método que contém o game loop
     * @param primaryStage Stage - Tela do jogo
     */
    private void game_loop(Stage primaryStage)
    {
        //Ativar o AnimationTimer
        AnimationTimer an;
        an = new AnimationTimer() 
        {
            //Variável que controla o tempo de exibição da mensagem "atingido"
            private long timer_atingido = 0;
            
            @Override
            public void handle(long now) 
            {   
                
                int i;
                //Se o jogoe stiver ativo e o jogador não foi atingido
                if(engine.jogoAtivo && !engine.atingido)
                {
                    //Processa o jogo e recebe uma lista de objetos
                    objetos = engine.processaJogo(tamX, tamY);
                    
                    //Limpa a tela
                    gc.clearRect(0, 0, tamX, tamY);
                    
                    //Desenha o fundo
                    gc.drawImage(imgFundo, 0, 0);
                    
                    //Desenha o Status
                    desenha_status();
                    
                    //Desenha todos os elementos
                    for(i = 0; i < objetos.size(); i++)
                    {
                        Elemento elem = objetos.get(i);
                        gc.drawImage(elem.getImagem(), elem.x, elem.y );
                    }
                    //Reseta o contador
                    timer_atingido = System.currentTimeMillis();
                }
                else if(engine.jogoAtivo && engine.atingido)
                {
                    //Caso o jogador tenha sido atingido
                    
                    //Exibir mensagem de atingido
                    gc.drawImage(new Image("Imagens/atingido.png", 300, 300, false, false)
                                ,350, 250);
                   
                    //Aguardar 1 segundo com o jogo "pausado"
                    if(System.currentTimeMillis() - timer_atingido >= 1000)
                        engine.atingido = false;
                }
                else
                {
                    /****** O jogo acabou ******/
                    
                    if(engine.ganhou)
                    {
                        //Exibir mensagem de vitória e pontos
                        gc.setFont(new Font("Arial", 35));
                        gc.drawImage(new Image("Imagens/venceu.png", 300, 300, false, false)
                                ,350, 250);
                        gc.fillText(player.getPontos() + "", 510, 528, 100);
                    }   
                    else
                    {
                        //Exibir mensagem de derrota e pontos
                        gc.setFont(new Font("Arial", 35));
                        gc.drawImage(new Image("Imagens/perdeu.png", 300, 300, false, false)
                                ,350, 250);
                        gc.fillText(player.getPontos() + "", 510, 528, 100);
                    }
                }
            }
        };
            
        an.start();
        primaryStage.show();
    }
    
    /**
     * Método que desenha na tela os pontos e as vidas do jogador
     */
    public void desenha_status()
    {
        gc.fillText("Pontos: ", 20, 40, 100);
        gc.fillText(player.getPontos() + "", 120, 40, 100);
        
        gc.fillText("Vidas: ", 800, 40, 100);
        gc.fillText(player.getVidas()+ "", 890, 40, 100);
    }
    
    /**
    * Método lê as teclas pressionadas pelo usuário 
    */
    public void ler_Teclas() {
        
        scene.setOnKeyPressed((KeyEvent e) -> {            
            
           engine.addEntrada(e.getCode());
                        
        });  
        
        scene.setOnKeyReleased((KeyEvent e) -> {            
            
            engine.removeEntrada(e.getCode());
                        
        });  
    }
}
