package Backend.component;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    int id;
    private String username;
    private String password;
    private List<Note> notes = new ArrayList<>();

    //Segun la IA, el UserDTO es un POJO que se usa para transferir datos entre la aplicacion mientras que la clase
    //myPrincipal se usa para toodo lo relacionado con la sesion y demas, incluyendo ver si esta autenticado, los roles,etc
    //Se puede usar esta clase como principal si es que no necesitas esconder ningun dato de implementacion o alguna cosa del
    //modulo de seguridad. Pero me piden que tenga contrasenia y eso, pero el tema es que no es recomendado
    //Primero, pienso yo, por el hecho de que lo estas exponiendo al usarlo como objeto de transferencia, entonces es mejor
    //una class 'principal' que se ocupa de eso. Desafortunadamente, pasarle la contrasenia de otra forma es un bardo,
    // entonces le pongo la pw hasheada acá y se la paso. Pero para eso tuve q sobreescribir el setter.
}
