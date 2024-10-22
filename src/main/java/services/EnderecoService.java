package services;

import java.util.List;

import exception.RenttavelException;
import model.entity.Endereco;
import model.entity.EnderecoSeletor;
import model.repository.EnderecoRepository;
import model.repository.ImovelRepository;

public class EnderecoService {
	EnderecoRepository repo = new EnderecoRepository();
	ImovelRepository imovelRepo = new ImovelRepository();

	public Endereco salvar(Endereco endereco) throws RenttavelException {
		this.verificarDuplicidade(endereco);
		return repo.salvar(endereco);
	}

	public boolean excluir(int id) throws RenttavelException {
		if (imovelRepo.consultarPorEndereco(id).isEmpty()) {
			return repo.excluir(id);
		} else {
			throw new RenttavelException("O endereço não pode ser excluído pois possui "
					+ imovelRepo.consultarPorEndereco(id).size() + " imóveis(s) cadastrado(s).");
		}
	}

	public boolean alterar(Endereco endereco) throws RenttavelException {
		this.verificarDuplicidade(endereco);
		return repo.alterar(endereco);
	}

	public Endereco buscarPorId(int id) {
		return repo.consultarPorId(id);
	}

	public List<Endereco> buscarTodos() {
		return repo.consultarTodos();
	}

	public List<Endereco> consultarComSeletor(EnderecoSeletor seletor) {
		return repo.consultarComSeletor(seletor);
	}

	public List<Endereco> buscarPorAnfitriao(int idAnfitriao) {
		return repo.consultarPorAnfitriao(idAnfitriao);
	}

	public int contarRegistros(EnderecoSeletor seletor) {
		return repo.contarRegistros(seletor);
	}

	public int contarPaginas(EnderecoSeletor seletor) {
		return repo.contarPaginas(seletor);
	}

	public void verificarDuplicidade(Endereco endereco) throws RenttavelException {
		List<Endereco> enderecos = repo.consultarPorAnfitriao(endereco.getAnfitriao().getId());
		for (Endereco e : enderecos) {
			if (e.getNumero() == endereco.getNumero() && e.getCep().equals(endereco.getCep())) {
				throw new RenttavelException("Não foi possível salvar, pois o CEP e Número já estão cadastrados!");
			}
		}
	}

}
