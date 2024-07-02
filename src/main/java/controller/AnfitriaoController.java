package controller;

import java.io.IOException;
import java.util.List;

import exception.ExceptionHandler;
import exception.RenttavelException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.entity.Anfitriao;
import model.entity.PerfilAcesso;
import services.AnfitriaoService;
import services.LoginService;

@Path("/anfitriao")
public class AnfitriaoController {
    
    @Context
    private HttpServletRequest request;
    
    private final AnfitriaoService service = new AnfitriaoService();
    private final AnfitriaoService anfService = new AnfitriaoService();    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Anfitriao salvar(Anfitriao anfitriao) {
        return service.salvar(anfitriao);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Anfitriao anfitriao) throws RenttavelException {
        validarAutenticacao();
        validarAnfAutenticado(anfitriao.getId());
        return service.alterar(anfitriao);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) throws RenttavelException {
        validarAutenticacao();
        validarAnfAutenticado(id);
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Anfitriao consultarPorId(@PathParam("id") int id) throws RenttavelException {
        validarAutenticacao();
        validarAnfAutenticado(id);
        return service.buscarPorId(id);
    }

    public void validarAutenticacao() throws RenttavelException {
        String idSessaoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
        if (idSessaoHeader == null || idSessaoHeader.isEmpty()) {
            throw new RenttavelException("Usuário sem acesso");
        }

        Anfitriao anfAutenticado = this.anfService.buscarPorIdSessao(idSessaoHeader);
        if (anfAutenticado == null) {
            throw new RenttavelException("Usuário sem acesso");
        }
    }

    public void validarAnfAutenticado(int idAnfitriao) throws RenttavelException {
    	String idSessaoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
        Anfitriao anfAutenticado = this.anfService.buscarPorIdSessao(idSessaoHeader);
        
        if(anfAutenticado == null) {
        	throw new RenttavelException("Usuário não encontrado");
        }
        
        if(anfAutenticado.getPerfilAcesso() == PerfilAcesso.ANFITRIAO && anfAutenticado.getId() != idAnfitriao) {
        	throw new RenttavelException("Usuário sem permissão de acesso");
        }
    }
}
