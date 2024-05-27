package controller;

import exception.RenttavelException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.entity.Imovel;
import services.ImovelService;

import java.util.List;

@Path("/imovel")
public class ImovelController {
    private final ImovelService service = new ImovelService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Imovel salvar(Imovel imovel) {
        return service.salvar(imovel);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Imovel imovel){
        return service.alterar(imovel);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) throws RenttavelException {
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Imovel consultarPorId(@PathParam("id") int id){
        return service.buscarPorId(id);
    }

    @GET
    @Path("/todos")
    public List<Imovel> consultarTodas(){
        return service.buscarTodos();
    }

    @GET
    @Path("/endereco/{idEndereco}")
    public List<Imovel> consultarPorEndereco(@PathParam("idEndereco") int idEndereco){
        return service.buscarPorEndereco(idEndereco);
    }

    @GET
    @Path("/anfitriao/{idAnfitriao}")
    public List<Imovel> consultarPorAnfitriao(@PathParam("idAnfitriao") int idAnfitriao){
        return service.buscarPorAnfitriao(idAnfitriao);
    }
}
