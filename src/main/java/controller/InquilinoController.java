package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import services.InquilinoService;
import model.entity.Imovel;
import model.entity.ImovelSeletor;
import model.entity.Inquilino;
import model.entity.InquilinoSeletor;

import java.util.List;

import exception.RenttavelException;

@Path("/inquilino")
public class InquilinoController {
	private final InquilinoService service = new InquilinoService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Inquilino salvar(Inquilino inquilino) {
		return service.salvar(inquilino);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Inquilino inquilino) {
		return service.alterar(inquilino);
	}

	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws RenttavelException {
		return service.excluir(id);
	}

	@GET
	@Path("/{id}")
	public Inquilino consultarPorId(@PathParam("id") int id) {
		return service.buscarPorId(id);
	}

	@GET
	@Path("/todos")
	public List<Inquilino> consultarTodas() {
		return service.buscarTodos();
	}
	
	@POST
    @Path("/filtro")
    public List<Inquilino> consultarComSeletor(InquilinoSeletor seletor){
        return service.consultarComSeletor(seletor);
    }

	@POST
	@Path("/total-registros")
	public int contarRegistros(InquilinoSeletor seletor){
		return service.contarRegistros(seletor);
	}

	@POST
	@Path("/total-paginas")
	public int contarPaginas(InquilinoSeletor seletor){
		return service.contarPaginas(seletor);
	}
}
