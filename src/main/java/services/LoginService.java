package services;

import java.util.UUID;

import exception.RenttavelException;
import model.dto.UsuarioDTO;
import model.entity.Anfitriao;
import model.repository.AnfitriaoRepository;

public class LoginService {

	private AnfitriaoRepository repo = new AnfitriaoRepository();
	
	public Anfitriao autenticar(UsuarioDTO dto) throws RenttavelException{
		
		if(dto == null || (dto.getEmail() == null || dto.getEmail().trim().isEmpty())) {
			throw new RenttavelException("Email não informado");
		}
		
		if(dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
			throw new RenttavelException("Senha não informada");
		}
		
		Anfitriao anfAutenticado = repo.consultarPorEmailSenha(dto);
		
		if(anfAutenticado.getId() < 1) {
			throw new RenttavelException("Email ou senha inválidos, tente novamente");
		}
		
		String idSessao = UUID.randomUUID().toString();
		anfAutenticado.setIdSessao(idSessao);
		repo.alterarIdSessao(anfAutenticado);
		
		return anfAutenticado;
	}
	
	public boolean chaveValida(String idSessao) {
		Anfitriao anf = this.repo.consultarPorIdSessao(idSessao);
		
		return anf != null && anf.getIdSessao() != null && anf.getIdSessao().equals(idSessao);
	}
}
