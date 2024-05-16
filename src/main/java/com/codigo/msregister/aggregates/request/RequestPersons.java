package com.codigo.msregister.aggregates.request;

import lombok.Getter;
import lombok.Setter;

//En resumen, "request" en el nombre de la clase RequestPersons indica que la clase representa los datos enviados
//desde un cliente al servidor como parte de una solicitud relacionada con las empresas.
//Una solicitud puede ser utilizada para realizar una amplia gama de acciones, desde obtener información hasta crear,
//actualizar o eliminar recursos en el servidor. Cada tipo de solicitud tiene un propósito específico y puede ser
//dirigida a un endpoint específico en el servidor para llevar a cabo la operación deseada.
@Getter
@Setter
public class RequestPersons {

    private String numDocument;
    private String email;
    private String telephone;
    private int documents_type_id_documents_type;
    private int enterprises_id_enterprises;

}