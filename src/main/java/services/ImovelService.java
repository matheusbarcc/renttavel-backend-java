package services;

import exception.RenttavelException;
import model.entity.Aluguel;
import model.entity.Imovel;
import model.entity.ImovelSeletor;
import model.repository.AluguelRepository;
import model.repository.ImovelRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ImovelService {

    ImovelRepository repo = new ImovelRepository();
    AluguelRepository aluguelRepo = new AluguelRepository();

    public Imovel salvar(Imovel imovel) throws RenttavelException {
    	validarCamposObrigatorios(imovel);
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

    public boolean alterar(Imovel imovel) throws RenttavelException {
    	validarCamposObrigatorios(imovel);
        return repo.alterar(imovel);
    }

    public Imovel consultarPorId(int id){
        Imovel imovel = repo.consultarPorId(id);
        atualizarOcupacao(imovel);

        return imovel;
    }

    public List<Imovel> consultarTodos() {
        List<Imovel> imoveis = repo.consultarTodos();

        for(Imovel imovel : imoveis){
            atualizarOcupacao(imovel);
        }

        return imoveis;
    }

    public List<Imovel> consultarPorEndereco(int idEndereco) {
        return repo.consultarPorEndereco(idEndereco);
    }

    public List<Imovel> consultarPorAnfitriao(int idAnfitriao) {
        return repo.consultarPorAnfitriao(idAnfitriao);
    }

    public List<Imovel> consultarComSeletor(ImovelSeletor seletor) {
        List<Imovel> imoveis = repo.consultarComSeletor(seletor);

        for(Imovel imovel : imoveis){
            atualizarOcupacao(imovel);
        }

        return imoveis;
    }

    public int contarRegistros(ImovelSeletor seletor){
        return repo.contarRegistros(seletor);
    }

    public int contarPaginas(ImovelSeletor seletor){
        return repo.contarPaginas(seletor);
    }

    public void atualizarOcupacao(Imovel imovel) {
        List<Aluguel> alugueis = aluguelRepo.consultarPorImovel(imovel.getId());
        LocalDateTime dataAtual = LocalDateTime.now();

        boolean ocupado = false;

        for (Aluguel aluguel : alugueis) {
            if((dataAtual.isAfter(aluguel.getDataCheckin()) || dataAtual.isEqual(aluguel.getDataCheckin()))
                    && (aluguel.getDataCheckoutEfetivo() == null)){
                ocupado = true;
                break;
            }

            if ((dataAtual.isAfter(aluguel.getDataCheckin()) || dataAtual.isEqual(aluguel.getDataCheckin()))
                    && (dataAtual.isBefore(aluguel.getDataCheckoutEfetivo()) || dataAtual.isEqual(aluguel.getDataCheckoutEfetivo()))) {
                ocupado = true;
                break;
            }
        }

        imovel.setIsOcupado(ocupado);
        repo.alterar(imovel);
    }
    
    public void validarCamposObrigatorios(Imovel i) throws RenttavelException{
    	boolean invalido = false;
    	if(i.getNome() == null || i.getNome().trim().length() < 1) {
    		invalido = true;
    	}
    	if(i.getTipo() < 1) {
    		invalido = true;
    	}
    	if(i.getCapacidadePessoas() < 1) {
    		invalido = true;
    	}
    	if(i.getQtdQuarto() < 1) {
    		invalido = true;
    	}
    	if(i.getQtdCama() < 1) {
    		invalido = true;
    	}
    	if(i.getQtdBanheiro() < 1) {
    		invalido = true;
    	}
    	if(i.getEndereco() == null) {
    		invalido = true;
    	}
    	if(invalido) {
    		throw new RenttavelException("Preencha o(s) campo(s) obrigatório(s)");
    	}
    }
}
