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
import model.entity.Inquilino;
import model.entity.InquilinoSeletor;
import model.entity.PerfilAcesso;
import services.AnfitriaoService;
import services.InquilinoService;

@Path("/restrito/inquilino")
public class InquilinoController {

    @Context
    private HttpServletRequest request;

    private final InquilinoService service = new InquilinoService();
    private final AnfitriaoService anfService = new AnfitriaoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Inquilino salvar(Inquilino inquilino) throws RenttavelException {
        this.validarAnfAutenticado(inquilino.getAnfitriao().getId());
        return service.salvar(inquilino);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean alterar(Inquilino inquilino) throws RenttavelException {
        this.validarAnfAutenticado(inquilino.getAnfitriao().getId());
        return service.alterar(inquilino);
    }

    @DELETE
    @Path("/{id}")
    public boolean excluir(@PathParam("id") int id) throws RenttavelException {
        this.validarAnfAutenticado(service.buscarPorId(id).getAnfitriao().getId());
        return service.excluir(id);
    }

    @GET
    @Path("/{id}")
    public Inquilino consultarPorId(@PathParam("id") int id) throws RenttavelException {
        this.validarAnfAutenticado(service.buscarPorId(id).getAnfitriao().getId());
        return service.buscarPorId(id);
    }

    @GET
    @Path("/anfitriao/{idAnfitriao}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inquilino> consultarPorAnfitriao(@PathParam("idAnfitriao") int idAnfitriao) throws RenttavelException {
        this.validarAnfAutenticado(idAnfitriao);
        return this.service.buscarPorAnfitriao(idAnfitriao);
    }

    @POST
    @Path("/filtro")
    public List<Inquilino> consultarComSeletor(InquilinoSeletor seletor) throws RenttavelException {
        this.validarAnfAutenticado(seletor.getIdAnfitriao());
        return service.consultarComSeletor(seletor);
    }

    @POST
    @Path("/total-registros")
    public int contarRegistros(InquilinoSeletor seletor) throws RenttavelException {
        this.validarAnfAutenticado(seletor.getIdAnfitriao());
        return service.contarRegistros(seletor);
    }

    @POST
    @Path("/total-paginas")
    public int contarPaginas(InquilinoSeletor seletor) throws RenttavelException {
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
