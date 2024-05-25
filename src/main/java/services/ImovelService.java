package services;

import model.entity.Imovel;
import model.repository.ImovelRepository;

import java.util.List;

public class ImovelService {

    ImovelRepository repo = new ImovelRepository();

    public Imovel salvar(Imovel imovel) {
        return repo.salvar(imovel);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Imovel imovel) {
        return repo.alterar(imovel);
    }

    public Imovel buscarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Imovel> buscarTodos() {
        return repo.consultarTodos();
    }
}
