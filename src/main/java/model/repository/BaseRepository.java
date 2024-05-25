package model.repository;

import java.util.ArrayList;

/**
 *
 * Interface que contém os métodos que todos os DAOs devem implementar.
 * -
 * T é um tipo genérico, que indica a classe que implementará o DAO.
 * -
 * Ex.: para a entidade Pessoa, deve ser criada uma classe PessoaDAO que
 * implementa BaseDAO<Pessoa>.
 *-
 * @author Vilmar César Pereira Júnior
 * @param <T> o tipo da classe de entidade (ou VO) que o DAO implementará
 *
 */
public interface BaseRepository<T> {

    /**
     * Insere um novo registro na tabela de entidade T
     *
     * @param novaEntidade o objeto que contém o novo registro que será inserido na
     *                     tabela.
     *
     * @return a novaEntidade salva, agora contendo um id.
     */
    public T salvar(T novaEntidade);

    /**
     * Exclui um determinado registro na tabela T, dado a sua chave primária.
     *
     * @param id a chave primária da entidade.
     * @return true caso excluiu, false caso contrário.
     */
    public boolean excluir(int id);

    /**
     * Altera um determinado registro na tabela T, dado o objeto escolhido para ser
     * alterado.
     *
     * @param entidade o objeto que terá o registro atualizado na tabela T.
     * @return true caso atualizou, false caso contrário.
     */
    public boolean alterar(T entidade);

    /**
     * Retorna um objeto do tipo T, dado a sua chave primária.
     *
     * @param id a chave primária do objeto/registro buscado
     * @return o objeto retornado pela consulta, ou null caso não exista registro com o id informado.
     */
    public T consultarPorId(int id);

    /**
     * Retorna todos os registros da tabela T
     *
     * @return uma lista de objetos do tipo T.
     */
    public ArrayList<T> consultarTodos();
}