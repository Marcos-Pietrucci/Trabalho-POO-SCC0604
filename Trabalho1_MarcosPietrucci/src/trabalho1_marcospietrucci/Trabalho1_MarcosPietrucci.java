package trabalho1_marcospietrucci;

/**
 * Classe principal
 * @author Marcos Pietrucci
 * @since Oct 2020
 */
public class Trabalho1_MarcosPietrucci {

    /**Método main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        interfaceTexto it = new interfaceTexto();
        
        try
        {
            it.iniciaJogo();
        }
        catch(InterruptedException E)
        {
            System.out.println("Erro "+ E.getMessage());
        }
        catch(Error E)
        {
            System.out.println("Um erro inesperado ocorreu: "+ E.getMessage());
        }

    }

}
