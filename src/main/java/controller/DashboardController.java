package controller;

import java.util.List;

import exception.RenttavelException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.dto.BarraDTO;
import model.dto.DiferencaDTO;
import model.entity.Aluguel;
import model.entity.Anfitriao;
import model.entity.PerfilAcesso;
import services.AnfitriaoService;
import services.DashboardService;

@Path("/restrito/dashboard")
public class DashboardController {

	@Context
	private HttpServletRequest request;
	
	private final DashboardService service = new DashboardService();
	private final AnfitriaoService anfService = new AnfitriaoService();
	
	@GET
	@Path("/rendimento-anual/{idAnfitriao}")
	public double calcularRendimentoAnual(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.calcularRendimentoAnual(idAnfitriao);
	}
	
	@GET
	@Path("/rendimento-mensal/{idAnfitriao}")
	public double calcularRendimentoMensal(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.calcularRendimentoMensal(idAnfitriao);
	}
	
	@GET
	@Path("/diferenca/{idAnfitriao}")
	public DiferencaDTO calcularDiferenca(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.calcularDiferenca(idAnfitriao);
	}
	
	@GET
	@Path("/ocupacao/{idAnfitriao}")
	public double calcularOcupacao(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.calcularOcupacao(idAnfitriao);
	}
	
	@GET
	@Path("/proximos-alugueis/{idAnfitriao}")
	public List<Aluguel> getProximosAlugueis(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.getProximosAlugueis(idAnfitriao);
	}
	
	@GET
	@Path("/barra-labels/{idAnfitriao}")
	public List<String> getLabelsGraficoBarra(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.getLabelsGraficoBarra(idAnfitriao);
	}
	
	@GET
	@Path("/barra-datasets/{idAnfitriao}")
	public List<BarraDTO> getDatasetGraficoBarra(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException{
		
		this.validarAnfAutenticado(idAnfitriao);
		
		return service.getDatasetGraficoBarra(idAnfitriao);
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
