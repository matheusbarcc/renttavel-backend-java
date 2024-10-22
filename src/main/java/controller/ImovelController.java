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
import model.entity.Anfitriao;
import model.entity.Imovel;
import model.entity.ImovelSeletor;
import model.entity.PerfilAcesso;
import services.AnfitriaoService;
import services.ImovelService;

@Path("/restrito/imovel")
public class ImovelController {

    @Context
    private HttpServletRequest request;

    private final ImovelService service = new ImovelService();
    private final AnfitriaoService anfService = new AnfitriaoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Imovel salvar(Imovel imovel) throws RenttavelException {
        this.validarAnfAutenticado(imovel.getAnfitriao().getId());
        return service.salvar(imovel);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Imovel imovel) throws RenttavelException {
        this.validarAnfAutenticado(imovel.getAnfitriao().getId());
        return service.alterar(imovel);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) throws RenttavelException {
        this.validarAnfAutenticado(service.consultarPorId(id).getAnfitriao().getId());
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Imovel consultarPorId(@PathParam("id") int id) throws RenttavelException {
        this.validarAnfAutenticado(service.consultarPorId(id).getAnfitriao().getId());
        return service.consultarPorId(id);
    }

    @GET
    @Path("/anfitriao/{idAnfitriao}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Imovel> consultarPorAnfitriao(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException {
        this.validarAnfAutenticado(idAnfitriao);
        return this.service.consultarPorAnfitriao(idAnfitriao);
    }

    @POST
    @Path("/filtro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Imovel> consultarComSeletor(ImovelSeletor seletor) throws RenttavelException {
        this.validarAnfAutenticado(seletor.getIdAnfitriao());
        return service.consultarComSeletor(seletor);
    }

    @POST
    @Path("/total-registros")
    public int contarRegistros(ImovelSeletor seletor) throws RenttavelException {
        this.validarAnfAutenticado(seletor.getIdAnfitriao());
        return service.contarRegistros(seletor);
    }

    @POST
    @Path("/total-paginas")
    public int contarPaginas(ImovelSeletor seletor) throws RenttavelException {
        this.validarAnfAutenticado(seletor.getIdAnfitriao());
        return service.contarPaginas(seletor);
    }

    public void validarAnfAutenticado(int idAnfitriao) throws RenttavelException {
        String idSessaoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
        Anfitriao anfAutenticado = this.anfService.buscarPorIdSessao(idSessaoHeader);

        if (anfAutenticado == null) {
            throw new RenttavelException("Usuário não encontrado");
        }

        if (anfAutenticado.getPerfilAcesso() == PerfilAcesso.ANFITRIAO && anfAutenticado.getId() != idAnfitriao) {
            throw new RenttavelException("Usuário sem permissão de acesso");
        }
    }
}
