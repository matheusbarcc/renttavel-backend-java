package services;

import java.util.List;

import exception.RenttavelException;
import model.entity.Inquilino;
import model.entity.InquilinoSeletor;
import model.repository.AluguelRepository;
import model.repository.InquilinoRepository;



public class InquilinoService {
	InquilinoRepository repo = new InquilinoRepository();
	AluguelRepository aluguelRepo = new AluguelRepository();

    public Inquilino salvar(Inquilino inquilino) {
        return repo.salvar(inquilino);
    }

    public boolean excluir(int id) throws RenttavelException {
        if(aluguelRepo.consultarPorInquilino(id).isEmpty()){
            return repo.excluir(id);
        } else {
            throw new RenttavelException("O inquilino não pode ser excluído pois possui " + aluguelRepo.consultarPorInquilino(id).size()
                    + " aluguel(s) cadastrado(s).");
        }
    }

    public boolean alterar(Inquilino inquilino) {
        return repo.alterar(inquilino);
    }

    public Inquilino buscarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Inquilino> buscarTodos() {
        return repo.consultarTodos();
    }
    
    public List<Inquilino> buscarPorAnfitriao(int idAnfitriao) {
        return repo.consultarPorAnfitriao(idAnfitriao);
    }

    public List<Inquilino> consultarComSeletor(InquilinoSeletor seletor) {
        return repo.consultarComSeletor(seletor);
    }

    public int contarRegistros(InquilinoSeletor inquilino) {
        return repo.contarRegistros(inquilino);
    }

    public int contarPaginas(InquilinoSeletor inquilino) {
        return repo.contarPaginas(inquilino);
    }
}
