package com.codigo.msregister.aggregates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

//Para responder a todos los metodos
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBase {

    private int code;
    private String message;
    //Optional es un global,puede almacenar una listo,objeto,etc.
    private Optional data;

}