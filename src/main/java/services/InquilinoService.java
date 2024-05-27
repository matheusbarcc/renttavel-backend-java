package services;

import java.util.List;

import model.entity.Inquilino;
import model.repository.InquilinoRepository;



public class InquilinoService {
	InquilinoRepository repo = new InquilinoRepository();

    public Inquilino salvar(Inquilino inquilino) {
        return repo.salvar(inquilino);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
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
}
