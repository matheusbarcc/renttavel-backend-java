package filter;

import java.io.IOException;
import java.util.List;

import exception.ExceptionHandler;
import exception.RenttavelException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import services.LoginService;

@Provider
public class AuthFilter implements ContainerRequestFilter{

	private static final String BASE_URL_RESTRITA = "restrito";
	public static final String CHAVE_ID_SESSAO = "idSessao";
	
	private LoginService loginService = new LoginService();
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo url = requestContext.getUriInfo();
		if(url.getPath().contains(BASE_URL_RESTRITA)) {
			List<String> keysSessionId = requestContext.getHeaders().get(CHAVE_ID_SESSAO);
			
			if(keysSessionId != null && keysSessionId.size() == 1) {
				String sessionId = keysSessionId.get(0);
				validarApiKey(sessionId, requestContext);
			}else {
				montarResponseUnauthorized(requestContext);
			}
		}
	}

	private void validarApiKey(String idSessao, ContainerRequestContext requestContext) {
		if(!loginService.chaveValida(idSessao)) {
			montarResponseUnauthorized(requestContext);
		}
	}

	private void montarResponseUnauthorized(ContainerRequestContext requestContext) {
		RenttavelException exception = new RenttavelException("Usuário sem acesso");
		String json = ExceptionHandler.converterExceptionParaJson(exception);

		Response response = Response.status(Status.FORBIDDEN)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.entity(json)
				.build();
		
		requestContext.abortWith(response);
	}
}
