package services;

import java.util.List;

import model.entity.Endereco;
import model.repository.EnderecoRepository;


public class EnderecoService {
	EnderecoRepository repo = new EnderecoRepository();

    public Endereco salvar(Endereco endereco) {
        return repo.salvar(endereco);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Endereco endereco) {
        return repo.alterar(endereco);
    }

    public Endereco buscarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Endereco> buscarTodos() {
        return repo.consultarTodos();
    }
}
