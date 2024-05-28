package exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Vilmar César Pereira Júnior
 *
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    protected final Logger log = Logger.getLogger(getClass().getName());

    /**
     * Mapeia uma exceção para {@link javax.ws.rs.core.Response}. <br>
     * Para mostrar um json do erro ao invés da tela padrão do Tomcat <br>
     *
     * @param exception a exceção que foi lançada
     * @return um objeto Response contendo um JSON com 2 atributos:
     *
     * {
     *   "mensagem": "Mensagem de erro que será apresentada ao usuário",
     *   "stacktrace": "Log de erros completo..."
     * }
     *
     */
    @Override
    public Response toResponse(Exception exception) {
        Map<String, String> mapAtributos = new HashMap<String, String>();
        mapAtributos.put("mensagem", exception.getMessage());

        //Fonte: https://www.baeldung.com/java-stacktrace-to-string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        mapAtributos.put("stacktrace", sw.toString());

        //Define o JSON que será retornado
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(mapAtributos);

        return Response.status(Status.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity(json)
                .build();
    }

}