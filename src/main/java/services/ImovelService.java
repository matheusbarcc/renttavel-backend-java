package services;

import exception.RenttavelException;
import model.entity.Imovel;
import model.repository.AluguelRepository;
import model.repository.ImovelRepository;

import java.util.List;

public class ImovelService {

    ImovelRepository repo = new ImovelRepository();
    AluguelRepository aluguelRepo = new AluguelRepository();

    public Imovel salvar(Imovel imovel) {
        return repo.salvar(imovel);
    }

    public boolean excluir(int id) throws RenttavelException {
        if(aluguelRepo.consultarPorImovel(id).isEmpty()){
            return repo.excluir(id);
        } else {
            throw new RenttavelException("O imovel não pode ser excluído pois possui " + aluguelRepo.consultarPorImovel(id).size()
                    + " aluguel(s) cadastrado(s).");
        }
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

    public List<Imovel> buscarPorEndereco(int idEndereco) {
        return repo.consultarPorEndereco(idEndereco);
    }

    public List<Imovel> buscarPorAnfitriao(int idAnfitriao) {
        return repo.consultarPorAnfitriao(idAnfitriao);
    }
}
