package trabalho1_marcospietrucci;

/**
 * Classe principal
 * @author marco
 * @since Oct 2020
 */
public class Trabalho1_MarcosPietrucci {

    /**Método main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        interfaceTexto it = new interfaceTexto();
        
        try{
            it.iniciaJogo();
        }
        catch(InterruptedException E)
        {
            System.out.println("Erro "+ E.getMessage());
        }

    }

}
