package com.codigo.msregister.aggregates.request;

import lombok.Getter;
import lombok.Setter;

//datos q enviara el usuario,para eso se crea esta clase
//ya q el usuario podra enviar solo estos datos no las q estan en la entidad.
//estos datos se obtienen de lo q manda el usuario en el body del postman,por eso se llama request(solicitud)
//"request" (solicitud en español) se refiere a la información que un cliente envía a un servidor para solicitar
// algún tipo de acción o recurso(endpoints). Esta solicitud puede ser un cuerpo de mensaje(json,el body-postman).
@Getter
@Setter
public class RequestEnterprises {

    private String numDocument;
    private String businessName;
    private String tradeName;
    private int enterprisesTypeEntity;
    private int documentsTypeEntity;

}