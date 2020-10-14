package trabalho1_marcospietrucci;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe responsável por gerenciar músicas e possíveis efeitos sonoros
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class Musica {
        
    /** Método que inicia a música tema em loop
     */
    public void startTheme()
    {        
        try
        {
            /* Queria criar uma pasta separada para a música, mas o formato de indexação de arquivos do Windows é diferente do Mac e do Linux */
            /* Então tive que deixar o arquivo na raiz do projeto, para garantir que funcione */
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("theme2.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(musica);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
}
