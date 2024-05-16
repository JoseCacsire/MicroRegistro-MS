package com.codigo.msregister.service.impl;

import com.codigo.msregister.aggregates.request.RequestEnterprises;
import com.codigo.msregister.aggregates.response.ResponseBase;
import com.codigo.msregister.constants.Constants;
import com.codigo.msregister.entity.DocumentsTypeEntity;
import com.codigo.msregister.entity.EnterprisesEntity;
import com.codigo.msregister.entity.EnterprisesTypeEntity;
import com.codigo.msregister.repository.EnterprisesRepository;
import com.codigo.msregister.service.EnterprisesService;
import com.codigo.msregister.util.EnterprisesValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnterprisesServiceImpl implements EnterprisesService {

    //intectando depedencias.Uso constructor y no el autowired,ya q toy
    //definiendolo como private final.
    private final EnterprisesRepository enterprisesRepository;
    private final EnterprisesValidations enterprisesValidations;

    public EnterprisesServiceImpl(EnterprisesRepository enterprisesRepository, EnterprisesValidations enterprisesValidations) {
        this.enterprisesRepository = enterprisesRepository;
        this.enterprisesValidations = enterprisesValidations;
    }

    @Override
    public ResponseBase createEnterprise(RequestEnterprises requestEnterprises) {
        boolean validate = enterprisesValidations.validateInput(requestEnterprises);
        if(validate){
            EnterprisesEntity enterprises = getEntity(requestEnterprises);
            enterprisesRepository.save(enterprises);
            //Se está pasando un objeto enterprises al constructor de ResponseBase, y se envuelve en un
            // Optional. Esto indica que se está pasando un objeto que no es nulo como parte de la respuesta.
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(enterprises));
        }else{
            return new ResponseBase(Constants.CODE_ERROR,Constants.MESS_ERROR_DATA_NOT_VALID,null);
        }
    }

    @Override
    public ResponseBase findOneEnterprise(Integer id) {
        //uso optional ya q puede tener valores nulos si es q no me trae nada el findOneEnterprise
        Optional enterprises = enterprisesRepository.findById(id);
        //Se verifica si el Optional enterprises contiene un valor (es decir, si la empresa fue encontrada)
        if(enterprises.isPresent()){
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,enterprises);
        }
        return new ResponseBase(Constants.CODE_ERROR,Constants.MESS_NON_DATA,Optional.empty());
    }

    @Override
    public ResponseBase findAllEnterprises() {
        //obligatorio usar Optional.of(),para q no te de error.Ya q el valor esperado debe ser
        // un optional y no un list
        Optional allEnterprises = Optional.of(enterprisesRepository.findAll());
        if(allEnterprises.isPresent()){
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,allEnterprises);
        }
        return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_ZERO_ROWS,Optional.empty());
    }

    @Override
    public ResponseBase updateEnterprise(Integer id, RequestEnterprises requestEnterprises) {
        //verifico si existe
        boolean existEnterprise = enterprisesRepository.existsById(id);
        //si existe entra al if
        if(existEnterprise){
            //lo busco y asigno a enterprises
            Optional<EnterprisesEntity> enterprises = enterprisesRepository.findById(id);
            //validando los datos q nos llega del postman
            boolean validationEntity = enterprisesValidations.validateInput(requestEnterprises);
            //si llegan los datos validos entro al if
            if(validationEntity){
                //Paso el requestEnterprises para que me actualize el enterprises y me devuelva ya actualizado.
                // utiliza enterprises.get() porque enterprises es un Optional, y enterprises.get() se utiliza para obtener el valor almacenado dentro de ese Optional.
                EnterprisesEntity enterprisesUpdate = getEntityUpdate(requestEnterprises,enterprises.get());
                //lo guardo en la BD
                enterprisesRepository.save(enterprisesUpdate);
                return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(enterprisesUpdate));
            }else {
                return new ResponseBase(Constants.CODE_ERROR,Constants.MESS_ERROR_DATA_NOT_VALID,Optional.empty());
            }
        }else {
            return new ResponseBase(Constants.CODE_ERROR,Constants.MESS_ERROR_NOT_UPDATE,Optional.empty());
        }
    }

    //metodos de apoyo.Es private ya q no se va a usar en otra clase solo en este clase
    //Seteando toda la clase entidad♥
    private EnterprisesEntity getEntity(RequestEnterprises requestEnterprises){
        EnterprisesEntity entity = new EnterprisesEntity();
        entity.setNumDocument(requestEnterprises.getNumDocument());
        entity.setBusinessName(requestEnterprises.getBusinessName());
      //Si el nombre comercial es nulo o vacío, se establece el valor del nombre comercial (tradeName) en el nombre comercial (businessName) de la solicitud (requestEnterprises), utilizando requestEnterprises.getBusinessName().
      //Si el nombre comercial no es nulo ni vacío, se establece el valor del nombre comercial (tradeName) en el nombre comercial de la solicitud (requestEnterprises.getTradeName()).
        entity.setTradeName(enterprisesValidations.isNullOrEmpty(requestEnterprises.getTradeName()) ? requestEnterprises.getBusinessName() : requestEnterprises.getTradeName());
        entity.setStatus(Constants.STATUS_ACTIVE);
        //Añadiendo FK
        entity.setEnterprisesTypeEntity(getEnterprisesType(requestEnterprises));
        entity.setDocumentsTypeEntity(getDocumentsType(requestEnterprises));
        //Auditoria
        entity.setUserCreate(Constants.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());

        return entity;
    }


    private EnterprisesEntity getEntityUpdate(RequestEnterprises requestEnterprises, EnterprisesEntity enterprisesEntity){
        enterprisesEntity.setNumDocument(requestEnterprises.getNumDocument());
        enterprisesEntity.setBusinessName(requestEnterprises.getBusinessName());
        enterprisesEntity.setTradeName(enterprisesValidations.isNullOrEmpty(requestEnterprises.getTradeName()) ? requestEnterprises.getBusinessName() : requestEnterprises.getTradeName());
        enterprisesEntity.setEnterprisesTypeEntity(getEnterprisesType(requestEnterprises));
        enterprisesEntity.setDocumentsTypeEntity(getDocumentsType(requestEnterprises));
        enterprisesEntity.setUserModif(Constants.AUDIT_ADMIN);
        enterprisesEntity.setDateModif(getTimestamp());
        return enterprisesEntity;
    }

    private EnterprisesTypeEntity getEnterprisesType(RequestEnterprises requestEnterprises){
        EnterprisesTypeEntity typeEntity = new EnterprisesTypeEntity();
        typeEntity.setIdEnterprisesType(requestEnterprises.getEnterprisesTypeEntity());
        return typeEntity;
    }

    private DocumentsTypeEntity getDocumentsType(RequestEnterprises requestEnterprises){
        DocumentsTypeEntity typeEntity = new DocumentsTypeEntity();
        typeEntity.setIdDocumentsType(requestEnterprises.getDocumentsTypeEntity());
        return  typeEntity;
    }

    /**
     * Este método devuelve un objeto Timestamp que representa el tiempo actual.
     * @return Objeto Timestamp que representa el tiempo actual.
     */
    private Timestamp getTimestamp(){
        // Obtener el tiempo actual en milisegundos desde la época Unix.
        long currentTime = System.currentTimeMillis();
        //esta línea crea un objeto Timestamp que representa el momento exacto en que
        //se ejecuta esta instrucción. Este objeto puede ser utilizado para realizar operaciones relacionadas con fechas y horas en Java.
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }

}