/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Gráfica;

import Engine_funcionamento.Engine;
import java.util.ArrayList;
import Elementos.*;
import java.util.List;


import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author marco
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
    Image imgFundo = new Image("Imagens/fundo.png", tamX, tamY, false, false);  //Reajustar o tamanho da imagem direto na declaração
    
    @Override
    public void start(Stage primaryStage) 
    {
        /* Criando variáveis necessárias para a tela */
        Group root = new Group();
        scene = new Scene(root, tamX, tamY);
        Canvas canvas = new Canvas(tamX, tamY);        
        gc = canvas.getGraphicsContext2D();          
        root.getChildren().add(canvas);
        
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("Imagens/icone.png"));
                    
        //Invoca o método que lê as teclas
        ler_Teclas();
        
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
    
    private void game_loop(Stage primaryStage)
    {
        AnimationTimer an;
        an = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                int i;
                if(engine.jogoAtivo)
                {
                    objetos = engine.processaJogo(tamX, tamY);
                    gc.clearRect(0, 0, tamX, tamY);
                    gc.drawImage(imgFundo, 0, 0);
                    desenha_status();
                    for(i = 0; i < objetos.size(); i++)
                    {
                        Elemento elem = objetos.get(i);
                        if(elem.esta_vivo())
                            gc.drawImage(elem.getImagem(), elem.x, elem.y );
                    }
                }
                else
                {
                    if(engine.ganhou)
                    {
                        
                        System.out.println("GANHOU!");
                    }   
                    else
                    {
                        gc.setFill(Color.BLUE);
                        gc.setFont(new Font(40));
                        gc.fillText("Perdeu...", 400, 400, 900000000);
                        System.out.println("Perdeu men");
                    }
                }
            }
        };
            
        an.start();
        primaryStage.show();
    }
    
    public void desenha_status()
    {
        gc.setFill(Color.WHITE);
        Font f;
                       
        f = new Font("Space Invaders", 30);
        gc.setFont(f);
        
        gc.fillText("Pontos: ", 20, 40, 100);
        gc.fillText(player.getPontos() + "", 120, 40, 100);
        
        gc.fillText("Vidas: ", 800, 40, 100);
        gc.fillText(player.getVidas()+ "", 900, 40, 100);
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
