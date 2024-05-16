package com.codigo.msregister.util;

import com.codigo.msregister.aggregates.response.ResponseReniec;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Util {
    public static String convertToJson(ResponseReniec responseReniec) { // convertir a universal
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(responseReniec);
        } catch (Exception e) {
            // Manejar la excepción (puede ser JsonProcessingException)
            e.printStackTrace();
            return null;
        }
    }

    //Clase generica <T> : es un tipo genérico que indica que el método convertFromJson puede aceptar y devolver cualquier tipo de objeto
    // sin especificar un tipo concreto de antemano.Por ejemplo, si llamas a convertFromJson con String como el tipo de clase valueType,
    // se utilizará String como el tipo de retorno del método.Del mismo modo, si lo llamas con Integer como valueType, devolverá un Integer.
    // Esto proporciona flexibilidad al método para manejar diferentes tipos de objetos sin necesidad de escribir múltiples versiones del mismo método para diferentes tipos de objetos.
    public static <T> T convertFromJson(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            // Manejar la excepción (puede ser IOException o JsonProcessingException)
            e.printStackTrace();
            return null;
        }
    }
}