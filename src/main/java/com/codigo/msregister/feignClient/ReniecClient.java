package com.codigo.msregister.feignClient;

import com.codigo.msregister.aggregates.response.ResponseReniec;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//En resumen, esta interfaz de cliente Feign se utiliza para interactuar con el servicio web de Reniec
// para obtener información de identificación utilizando un número de documento nacional de identidad (DNI),
// proporcionado como parámetro en la solicitud.
//Consumiendo la api de reniec y me devuelve una respuesta
@FeignClient(name = "reniec-client", url = "https://api.apis.net.pe/v2/reniec/")//
public interface ReniecClient {

    @GetMapping("/dni")
    ResponseReniec getInfoReniec(@RequestParam("numero") String numero,
                                 @RequestHeader("Authorization") String authorizationHeader);
}