/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author marco
 */
public class TelaJogo extends Application {
    
    // Varíaveis necessárias para a tela
    private GraphicsContext gc;
    private Scene scene;
    private final int tamX = 800, tamY = 800;

    // Classe que controla a engine do jogo
    private Engine engine;
    
    //ArrayList com todos os elementos
    private ArrayList<Elemento> objetos;
    
    //O canhão do jogador
    Canhao player;
    
    //Imagem de fundo do jogo
    Image imgFundo = new Image("Imagens/Curso.png", tamX, tamY, false, false);  //Reajustar o tamanho da imagem direto na declaração
    
    @Override
    public void start(Stage primaryStage) 
    {
        
        /* Criando variáveis necessárias para a tela */
        Group root = new Group();
        scene = new Scene(root, tamX, tamY);
        Canvas canvas = new Canvas(tamX, tamY);
        //Image a = new Image("Imagens/plat.png");
        
        gc = canvas.getGraphicsContext2D();   
        
        root.getChildren().add(canvas);
        
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
                    
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
                objetos = engine.processaJogo(tamX, tamY);
                gc.clearRect(0, 0, tamX, tamY);
                gc.drawImage(imgFundo, 0, 0);
                for(i = 0; i < objetos.size(); i++)
                {
                    Elemento elem = objetos.get(i);
                    gc.drawImage(elem.imagem, elem.x, elem.y );
                }
                
            }
        };
            
        an.start();
        primaryStage.show();
    }
            
    
    /**
    * Método lê as teclas pressionadas pelo usuário 
    */
    public void ler_Teclas() {
        scene.setOnKeyPressed((KeyEvent e) -> {
            
            System.out.println("vagina");
            
            if(e.getCode() == KeyCode.SPACE)
                engine.criaTiro();
            else
                player.move(e);
                        
        });
    
    
    }
    
}
