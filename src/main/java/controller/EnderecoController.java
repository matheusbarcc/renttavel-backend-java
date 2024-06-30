package controller;

import java.util.List;

import exception.RenttavelException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import model.entity.EnderecoSeletor;
import services.EnderecoService;

@Path("/restrito/endereco")
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
	@POST
    @Path("/filtro")
    public List<Endereco> consultarComSeletor(EnderecoSeletor seletor){
        return service.consultarComSeletor(seletor);
    }
	@POST
    @Path("/total-registros")
    public int contarRegistros(EnderecoSeletor seletor) {
        return service.contarRegistros(seletor);
    }

    @POST
    @Path("/total-paginas")
    public int contarPaginas(EnderecoSeletor seletor) {
        return service.contarPaginas(seletor);
    }
}
