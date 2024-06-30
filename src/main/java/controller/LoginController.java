package controller;

import exception.RenttavelException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.UsuarioDTO;
import model.entity.Anfitriao;
import services.LoginService;

@Path("/login")
public class LoginController {

	private LoginService service = new LoginService();
	
	@POST
	@Path("/autenticar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Anfitriao autenticar(UsuarioDTO usuarioAutenticando) throws RenttavelException {
		return service.autenticar(usuarioAutenticando);
	}
}
