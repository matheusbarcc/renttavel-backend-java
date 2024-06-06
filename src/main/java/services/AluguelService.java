package services;

import exception.RenttavelException;
import model.entity.Aluguel;
import model.entity.AluguelSeletor;
import model.repository.AluguelRepository;

import java.util.List;

public class AluguelService {

    AluguelRepository repo = new AluguelRepository();

    public Aluguel salvar(Aluguel aluguel) throws RenttavelException{
        validarValorTotal(aluguel);
        return repo.salvar(aluguel);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Aluguel aluguel) throws RenttavelException{
        validarValorTotal(aluguel);
        return repo.alterar(aluguel);
    }

    public Aluguel consultarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Aluguel> consultarTodos() {
        return repo.consultarTodos();
    }

    public List<Aluguel> consultarPorImovel(int idImovel) {
        return repo.consultarPorImovel(idImovel);
    }

    public List<Aluguel> consultarPorInquilino(int idInquilino) {
        return repo.consultarPorInquilino(idInquilino);
    }

    public List<Aluguel> consultarComSeletor(AluguelSeletor seletor){
        return repo.consultarComSeletor(seletor);
    }

    public int contarRegistros(AluguelSeletor seletor){
        return repo.contarRegistros(seletor);
    }

    public int contarPaginas(AluguelSeletor seletor){
        return repo.contarPaginas(seletor);
    }

    public void validarValorTotal(Aluguel aluguel) throws RenttavelException {
        double valorTotalReal = (aluguel.getValorDiaria() * aluguel.getQtdDias())
                                + aluguel.getValorLimpeza() + aluguel.getValorMulta();
        double margemErro = 0.10;

        if (Math.abs(aluguel.getValorTotal() - valorTotalReal) > margemErro) {
            throw new RenttavelException("O Valor Total deve ser o resultado de [(Valor Diária * Quantidade de dias) + Valor Limpeza + Valor Multa] com uma margem de erro de 0,10.");
        }
    }

}
