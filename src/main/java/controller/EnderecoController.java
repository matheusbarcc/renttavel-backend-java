package controller;

import java.util.List;

import exception.RenttavelException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import services.EnderecoService;


@Path("/endereco")
public class EnderecoController {
	private final EnderecoService service = new EnderecoService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Endereco salvar(Endereco endereco) {
		return service.salvar(endereco);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Endereco endereco) {
		return service.alterar(endereco);
	}

	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws RenttavelException {
		return service.excluir(id);
	}

	@GET
	@Path("/{id}")
	public Endereco consultarPorId(@PathParam("id") int id) {
		return service.buscarPorId(id);
	}

	@GET
	@Path("/todos")
	public List<Endereco> consultarTodas() {
		return service.buscarTodos();
	}
}
