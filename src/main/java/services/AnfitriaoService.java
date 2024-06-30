package services;

import java.util.List;

import model.entity.Anfitriao;
import model.repository.AnfitriaoRepository;


public class AnfitriaoService {
	AnfitriaoRepository repo = new AnfitriaoRepository();

    public Anfitriao salvar(Anfitriao anfitriao) {
        return repo.salvar(anfitriao);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Anfitriao anfitriao) {
        return repo.alterar(anfitriao);
    }

    public Anfitriao buscarPorId(int id){
        return repo.consultarPorId(id);
    }

    public List<Anfitriao> buscarTodos() {
        return repo.consultarTodos();
    }
    
    public Anfitriao buscarPorIdSessao(String idSessao) {
    	return repo.consultarPorIdSessao(idSessao);
    }
}
