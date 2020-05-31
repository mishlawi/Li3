import Models.Catalogos.ICliente;
import Models.Catalogos.IProduto;

/**
 * Interface correspondente á classe GereVendasController
 *
 *
 * @author Grupo4
 * @version 2020
 */
 public interface IGereVendasController {
    /**
     * Setter
     * @param model
     */
     void setModel (GereVendasModel model);

    /**
     * Setter
     * @param view
     */
     void setView (GereVendasView view);

    /**
     * Inicializa controller
     */
    void startController();

    /**
     * Lê Ficheiros
     */
     void readFiles();

    /**
     * Carrega o Estado
     */
    void carregaEstadoWrapper();


    /**
    * Guarda Estado
    */
     void guardaEstadoWrapper();

    /**
     * Tratamento das Queries estatisticas
     */
    void handlerQueriesEstatisticas();

    /**
     * Tratamento dos dados gerais
     */
     void handlerdadosGerais();

    /**
     * Tratamento das Queries interativas
     */
    void handlerQueriesInterativas();

    /**
     * Verifica a validade de um mes
     * @param x
     * @return
     */
     boolean validaMes(int x);

    /**
     * Verifica a validade de um Cliente
     * @param c
     * @return
     */
     boolean validaCliente(ICliente c);

    /**
     * Verifica a validade de um Produto
     * @param p
     * @return
     */
     boolean validaProduto(IProduto p);

}
