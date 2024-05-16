package com.codigo.msregister.entity;

import com.codigo.msregister.aggregates.model.Audit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "persons")
public class PersonsEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persons")
    private int idPersons;

    @Column(name = "num_document",length = 15, nullable = false)
    private String numDocument;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "lastname",length = 100, nullable = false)
    private String lastName;

    @Column(name = "email",length = 100, nullable = false)
    private String email;
    @Column(name = "telephone",length = 15, nullable = false)
    private String telephone;

    @Column(name = "status", nullable = false)
    private int status;

    //varias personas pueden tener un documento.Osea en una fila una persona puede tener dni,en la otra fila igual dni
    //y asi podria seguir.
    @ManyToOne
    @JoinColumn(name = "document_type_id_document_type",nullable = false)
    private DocumentsTypeEntity documentsTypeEntity;
    //igual aca una persona puede estar en una empresa x en la otra fila quizas sea empresa y
    // en la otra fila la persona perteneza a la empresa x.Entonces podria repetirse
    //q varias personas pueden estar en una empresa q en este ejemplo es empresa x o podria ser y tambien.
    @ManyToOne
    @JoinColumn(name = "enterprises_id_enterprises",nullable = false)
    private EnterprisesEntity enterprisesEntity;

}
