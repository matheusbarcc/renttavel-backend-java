package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.entity.Aluguel;
import services.AluguelService;

import java.util.List;

@Path("/aluguel")
public class AluguelController {
    private final AluguelService service = new AluguelService();
    //TODO Apagar
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Aluguel salvar(Aluguel aluguel) {
        return service.salvar(aluguel);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Aluguel aluguel){
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
        return service.buscarPorId(id);
    }

    @GET
    @Path("/todos")
    public List<Aluguel> consultarTodas(){
        return service.buscarTodos();
    }
}
