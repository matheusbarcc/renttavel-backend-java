package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Anfitriao;
import services.AnfitriaoService;

@Path("/anfitriao")
public class AnfitriaoController {
    private final AnfitriaoService service = new AnfitriaoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Anfitriao salvar(Anfitriao anfitriao) {
        return service.salvar(anfitriao);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Anfitriao anfitriao){
        return service.alterar(anfitriao);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) {
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Anfitriao consultarPorId(@PathParam("id") int id){
        return service.buscarPorId(id);
    }

    @GET
    @Path("/todos")
    public List<Anfitriao> consultarTodas(){
        return service.buscarTodos();
    }
}
