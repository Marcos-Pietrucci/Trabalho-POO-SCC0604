package trabalho1_marcospietrucci;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe responsável por gerenciar músicas e efeitos sonoros
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class Musica {

    AudioInputStream musica;
    Clip clip;
    
    /**
     * Método que inicia a música tema em loop
     */
    public void iniciaTheme()
    {        
        try
        {
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            musica = AudioSystem.getAudioInputStream(new File("theme.wav"));
            clip = AudioSystem.getClip();
            
            clip.open(musica);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
    
    /**
     * Método que inicia a música tema acelerada em loop
     */
    public void iniciaTheme2()
    {              
        try
        {
            //Para a musica atual
            clip.stop();
            
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            musica = AudioSystem.getAudioInputStream(new File("theme2.wav"));
            clip = AudioSystem.getClip();
            
            clip.open(musica);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
    
    /**
     * Método que inicia a música tema acelerada em loop
     */
    public void iniciaTheme3()
    {              
        try
        {
            //Para a musica atual
            clip.stop();
            
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            musica = AudioSystem.getAudioInputStream(new File("theme3.wav"));
            clip = AudioSystem.getClip();
            
            clip.open(musica);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
    
    
    /**
     * Método que toca o efeito sonoro do tiro do canhão
     */
    public void tiro()
    {
        try
        {
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            musica = AudioSystem.getAudioInputStream(new File("tiro.wav"));
            Clip clip2 = AudioSystem.getClip();
            
            clip2.open(musica);
            clip2.start();
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
    
    /**
     * Método que toca o efeito sonoro do invasor sendo destruído
     */
    public void destruiInvasor()
    {
        try
        {
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            musica = AudioSystem.getAudioInputStream(new File("invader.wav"));
            Clip clip3 = AudioSystem.getClip();
            
            clip3.open(musica);
            clip3.start();
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    
    }
}
