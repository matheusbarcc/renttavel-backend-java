package services;

import java.util.List;

import exception.RenttavelException;
import model.entity.Anfitriao;
import model.repository.AnfitriaoRepository;


public class AnfitriaoService {
	AnfitriaoRepository repo = new AnfitriaoRepository();

    public Anfitriao salvar(Anfitriao anfitriao) throws RenttavelException {
    	this.verificarDuplicidade(anfitriao);
        return repo.salvar(anfitriao);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Anfitriao anfitriao) throws RenttavelException {
    	this.verificarDuplicidade(anfitriao);
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
    public void verificarDuplicidade(Anfitriao anfitriao) throws RenttavelException {
    	List<Anfitriao> anfitrioes = repo.consultarTodos();
    	for(Anfitriao a : anfitrioes) {
    		if(a.getEmail().equals(anfitriao.getEmail())) {
    			throw new RenttavelException("Não foi possível salvar, pois este e-mail já está cadastrado!");
    		}
    	}
    }
}
