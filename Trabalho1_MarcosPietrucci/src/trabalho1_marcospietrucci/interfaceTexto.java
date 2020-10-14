package trabalho1_marcospietrucci;
import java.util.ArrayList;

/**
 * Classe que trabalhará diretamente com a interface textual do netBeans
 * @author marco
 */
public class interfaceTexto {
    
    private final int tamX = 15, tamY = 20;
    private char tela[][] = new char[15][20];
    private ArrayList<Elemento> elem;
    
    
    interfaceTexto()
    {
        elem = new ArrayList<>();
    }
    
    public void iniciaJogo()
    {
        setElementos();
        escreve_tela();
        
    }
    
    /**
     * Método que vai instanciar todos os elementos do jogo
     */
    private void setElementos()
    {
        /* Os invasores estão divididos em 11 colunas e 5 linhas*/
            /* Na minha matriz de 15x20 eles devem começar em uma região especifica*/
        int i, j;
        for(i = 0; i <= 4; i++)
            for(j = 5; j <= 15; j++)
            {
                elem.add(new Invasor(i, j, '&', 1, 1));
            }
        
        //As barreiras ficarão na linha 27
        for(j = 4; j < 20; j+= 4)
            elem.add(new Barreira(tamX - 3, j, '=', 1, 1));
        
        //O canhão começa mais ou menos no canto esquerdo
        elem.add(new Canhao(tamX - 1, 5, (char) 177, 1, 1));
            
    }
    
    private void escreve_tela()
    {
        Elemento aux;
        int i, j;
        
        i = 0;
        while(i != elem.size())
        {
            aux = elem.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        
        for (i = 0; i < tamX; i++)
        {
            for(j = 0; j < tamY; j++)
            {
                if(tela[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(tela[i][j]);
            }
            System.out.print("\n");
        }
    }
    
    private void cls()
    {
        int i;
        for(i = 0; i < 50; i++)
            System.out.println("\n");
    }
    
}
