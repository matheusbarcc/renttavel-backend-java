package controller;

import java.util.List;

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
import model.entity.Aluguel;
import model.entity.AluguelSeletor;
import model.entity.Anfitriao;
import model.entity.Imovel;
import model.entity.PerfilAcesso;
import services.AluguelService;
import services.AnfitriaoService;

@Path("/restrito/aluguel")
public class AluguelController {
	
	@Context
	private HttpServletRequest request;
	
	private final AluguelService service = new AluguelService();
	private final AnfitriaoService anfService = new AnfitriaoService();	

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Aluguel salvar(Aluguel aluguel) throws RenttavelException {
    	
		this.validarAnfAutenticado(aluguel.getAnfitriao().getId());
    	
        return service.salvar(aluguel);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Aluguel aluguel) throws RenttavelException {
    	
    	this.validarAnfAutenticado(aluguel.getAnfitriao().getId());
        
        return service.alterar(aluguel);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) throws RenttavelException {
    	
    	this.validarAnfAutenticado(service.consultarPorId(id).getAnfitriao().getId());
    	
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Aluguel consultarPorId(@PathParam("id") int id) throws RenttavelException{
    	
    	this.validarAnfAutenticado(service.consultarPorId(id).getAnfitriao().getId());
    	
        return service.consultarPorId(id);
    }
    
    @GET
    @Path("/anfitriao/{idAnfitriao}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aluguel> consultarPorAnfitriao(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
        
    	this.validarAnfAutenticado(idAnfitriao);
        
        return this.service.consultarPorAnfitriao(idAnfitriao);
    }

    @POST
    @Path("/filtro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aluguel> consultarComSeletor(AluguelSeletor seletor) throws RenttavelException{
    	
    	this.validarAnfAutenticado(seletor.getIdAnfitriao());
    	
    	return service.consultarComSeletor(seletor);
    }

    @POST
    @Path("/total-registros")
    public int contarRegistros(AluguelSeletor seletor) throws RenttavelException {
    	
    	this.validarAnfAutenticado(seletor.getIdAnfitriao());
    	
        return service.contarRegistros(seletor);
    }

    @POST
    @Path("/total-paginas")
    public int contarPaginas(AluguelSeletor seletor) throws RenttavelException {
    	
    	this.validarAnfAutenticado(seletor.getIdAnfitriao());
    	
        return service.contarPaginas(seletor);
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
