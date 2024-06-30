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
import model.entity.Aluguel;
import model.entity.AluguelSeletor;
import services.AluguelService;

@Path("/restrito/aluguel")
public class AluguelController {
    private final AluguelService service = new AluguelService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Aluguel salvar(Aluguel aluguel) throws RenttavelException {
        return service.salvar(aluguel);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Aluguel aluguel) throws RenttavelException {
        return service.alterar(aluguel);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) {
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Aluguel consultarPorId(@PathParam("id") int id){
        return service.consultarPorId(id);
    }

    @GET
    @Path("/todos")
    public List<Aluguel> consultarTodas(){
        return service.consultarTodos();
    }

    @GET
    @Path("/imovel/{idImovel}")
    public List<Aluguel> consultarPorImovel(@PathParam("idImovel") int idImovel){
        return service.consultarPorImovel(idImovel);
    }

    @GET
    @Path("/inquilino/{idInquilino}")
    public List<Aluguel> consultarPorInquilino(@PathParam("idInquilino") int idInquilino){
        return service.consultarPorInquilino(idInquilino);
    }

    @POST
    @Path("/filtro")
    public List<Aluguel> consultarComSeletor(AluguelSeletor seletor) throws RenttavelException{
        return service.consultarComSeletor(seletor);
    }

    @POST
    @Path("/total-registros")
    public int contarRegistros(AluguelSeletor seletor) {
        return service.contarRegistros(seletor);
    }

    @POST
    @Path("/total-paginas")
    public int contarPaginas(AluguelSeletor seletor) {
        return service.contarPaginas(seletor);
    }
}
