package services;

import java.util.List;

import exception.RenttavelException;
import model.entity.Aluguel;
import model.entity.AluguelSeletor;
import model.repository.AluguelRepository;

public class AluguelService {

    AluguelRepository repo = new AluguelRepository();

    public Aluguel salvar(Aluguel aluguel) throws RenttavelException{
    	this.validarCamposObrigatorios(aluguel);
        this.validarValorTotal(aluguel);
        this.validarDatas(aluguel);
        this.validarImovelDisponivel(aluguel);
        this.validarImovelInquilinoPorAnfitriao(aluguel);
        return repo.salvar(aluguel);
    }

    public boolean excluir(int id) {
        return repo.excluir(id);
    }

    public boolean alterar(Aluguel aluguel) throws RenttavelException{
    	this.validarCamposObrigatorios(aluguel);
        this.validarValorTotal(aluguel);
        this.validarDatas(aluguel);
        this.validarImovelDisponivel(aluguel);
        this.validarImovelInquilinoPorAnfitriao(aluguel);
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
    
    public List<Aluguel> consultarPorAnfitriao(int idAnfitriao) {
        return repo.consultarPorAnfitriao(idAnfitriao);
    }

    public List<Aluguel> consultarComSeletor(AluguelSeletor seletor) throws RenttavelException{
    	this.validarFiltroValorTotal(seletor);
    	this.validarFiltroDatas(seletor);
        return repo.consultarComSeletor(seletor);
    }

    public int contarRegistros(AluguelSeletor seletor){
        return repo.contarRegistros(seletor);
    }

    public int contarPaginas(AluguelSeletor seletor){
        return repo.contarPaginas(seletor);
    }

    public void validarDatas(Aluguel aluguel) throws RenttavelException {
    	if(aluguel.getDataCheckoutEfetivo() != null) {
			if(aluguel.getDataCheckin().isAfter(aluguel.getDataCheckoutEfetivo()) || aluguel.getDataCheckin().isEqual(aluguel.getDataCheckoutEfetivo())) {
	    		throw new RenttavelException("A data 'Checkin' deve ser anterior à data 'Checkout Efetivo'.");
	    	}
    	}
    	if(aluguel.getDataCheckin().isAfter(aluguel.getDataCheckoutPrevisto()) || aluguel.getDataCheckin().isEqual(aluguel.getDataCheckoutPrevisto())) {
    		throw new RenttavelException("A data 'Checkin' deve ser anterior à data 'Checkout Previsto'.");
    	}
    }

    public void validarValorTotal(Aluguel aluguel) throws RenttavelException {
        double valorTotalReal = (aluguel.getValorDiaria() * aluguel.getQtdDias())
                                + aluguel.getValorLimpeza() + aluguel.getValorMulta();
        double margemErro = 0.10;

        if (Math.abs(aluguel.getValorTotal() - valorTotalReal) > margemErro) {
            throw new RenttavelException("O Valor Total deve ser o resultado de [(Valor Diária * Quantidade de dias) + Valor Limpeza + Valor Multa] com uma margem de erro de 0,10.");
        }
    }

    public void validarFiltroValorTotal(AluguelSeletor seletor) throws RenttavelException {
    	if(seletor.getValorTotalMin() > 0 && seletor.getValorTotalMax() > 0) {
    		if(seletor.getValorTotalMin() > seletor.getValorTotalMax()) {
    			throw new RenttavelException("O 'Valor Mínimo' deve ser menor ou igual ao 'Valor máximo'.");
    		}
    	}
    }

    public void validarFiltroDatas(AluguelSeletor seletor) throws RenttavelException {
    	if(seletor.getDataCheckinInicio() != null && seletor.getDataCheckoutEfetivoFinal() != null) {
    		if(seletor.getDataCheckinInicio().isAfter(seletor.getDataCheckoutEfetivoFinal())) {
    			throw new RenttavelException("A data 'Checkin' deve ser anterior à data 'Checkout'.");
    		}
    	}
    	if(seletor.getDataCheckinInicio() != null && seletor.getDataCheckinFinal() != null) {
    		if(seletor.getDataCheckinInicio().isAfter(seletor.getDataCheckinFinal())) {
    			throw new RenttavelException("A data 'Checkin Início' deve ser anterior à data 'Checkin Final'.");
    		}
    	}
    	if(seletor.getDataCheckoutEfetivoInicio() != null && seletor.getDataCheckoutEfetivoFinal() != null) {
    		if(seletor.getDataCheckoutEfetivoInicio().isAfter(seletor.getDataCheckoutEfetivoFinal())) {
    			throw new RenttavelException("A data 'Checkout Início' deve ser anterior à data 'Checkout Final'.");
    		}
    	}
    	if(seletor.getDataCheckoutPrevistoInicio() != null && seletor.getDataCheckoutPrevistoFinal() != null) {
    		if(seletor.getDataCheckoutPrevistoInicio().isAfter(seletor.getDataCheckoutPrevistoFinal())) {
    			throw new RenttavelException("A data 'Checkout Previsto Início' deve ser anterior à data 'Checkout Previsto Final'.");
    		}
    	}
    }

    public void validarImovelDisponivel(Aluguel novoAluguel) throws RenttavelException{
    	List<Aluguel> alugueis = this.consultarPorImovel(novoAluguel.getImovel().getId());

    	for(Aluguel aluguel : alugueis) {
    		
    		if(novoAluguel.getAnfitriao().getId() != aluguel.getAnfitriao().getId()) {
    			continue;
    		}
    		
    		if(novoAluguel.getId() > 0) {
    			if(novoAluguel.getId() == aluguel.getId()) {
    				continue;
    			}
    		}

    		if (aluguel.getDataCheckoutEfetivo() != null) {
    			if(novoAluguel.getDataCheckin().isAfter(aluguel.getDataCheckoutEfetivo())) {
    				continue;
    			}
    		} else if (novoAluguel.getDataCheckin().isAfter(aluguel.getDataCheckoutPrevisto())) {
    			continue;
    		}

    		if (novoAluguel.getDataCheckoutEfetivo() != null) {
    			if(novoAluguel.getDataCheckoutEfetivo().isBefore(aluguel.getDataCheckin())) {
    				continue;
    			}
    		} else if (novoAluguel.getDataCheckoutPrevisto().isBefore(aluguel.getDataCheckin())) {
    			continue;
    		}
    		throw new RenttavelException("<b>Verifique o Check-in e/ou Checkouts</b><br><br> Já existe um aluguel no imóvel '" + novoAluguel.getImovel().getNome() + "' cadastrado durante esse período.");
    	}
    }
    
    public void validarImovelInquilinoPorAnfitriao(Aluguel novoAluguel) throws RenttavelException{
		if(novoAluguel.getImovel().getAnfitriao().getId() != novoAluguel.getAnfitriao().getId()) {
			throw new RenttavelException("Não é permitido cadastrar aluguéis com imóveis de outros usuários.");
		}
		if(novoAluguel.getInquilino().getAnfitriao().getId() != novoAluguel.getAnfitriao().getId()) {
			throw new RenttavelException("Não é permitido cadastrar aluguéis com inquilinos de outros usuários.");
		}
    }

    public void validarCamposObrigatorios(Aluguel a) throws RenttavelException{
    	boolean invalido = false;
    	if(a == null) {
    		throw new RenttavelException("Preencha o(s) campo(s) obrigatório(s)");
    	}
    	if(a.getAnfitriao() == null || a.getAnfitriao().getId() < 1) {
    		throw new RenttavelException("Preencha o(s) campo(s) obrigatório(s)");
    	}
    	if(a.getDataCheckin() == null) {
    		invalido = true;
    	}
    	if(a.getDataCheckoutPrevisto() == null) {
    		invalido = true;
    	}
    	if(a.getValorTotal() < 1) {
    		invalido = true;
    	}
    	if(a.getValorDiaria() < 1) {
    		invalido = true;
    	}
    	if(a.getValorLimpeza() < 0) {
    		invalido = true;
    	}
    	if(a.getImovel() == null) {
    		invalido = true;
    	}
    	if(a.getInquilino() == null) {
    		invalido = true;
    	}
    	if(invalido) {
    		throw new RenttavelException("Preencha o(s) campo(s) obrigatório(s)");
    	}
    }

}
