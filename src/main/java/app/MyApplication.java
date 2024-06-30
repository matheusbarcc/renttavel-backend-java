package app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

//Classe necessária para utilizar as anotações do Jakarta
@ApplicationPath("/rest")
public class MyApplication extends Application {
	
}