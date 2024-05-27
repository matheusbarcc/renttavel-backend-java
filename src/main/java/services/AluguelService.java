package services;

import model.entity.Aluguel;
import model.repository.AluguelRepository;

import java.util.List;

public class AluguelService {

    AluguelRepository repo = new AluguelRepository();

    public Aluguel salvar(Aluguel aluguel) {
        return repo.salvar(aluguel);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Aluguel aluguel) {
        return repo.alterar(aluguel);
    }

    public Aluguel buscarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Aluguel> buscarTodos() {
        return repo.consultarTodos();
    }
}
