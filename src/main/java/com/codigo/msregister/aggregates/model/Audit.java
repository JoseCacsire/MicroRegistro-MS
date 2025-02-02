package com.codigo.msregister.aggregates.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@MappedSuperclass//mapear desde otra clase q seria las entidades
public class Audit {
    @Column(name = "user_create",length = 45,nullable = true)
    private String userCreate;
    @Column(name = "date_create",nullable = true)
    private Timestamp dateCreate;
    @Column(name = "user_modif",length = 45,nullable = true)
    private String userModif;
    @Column(name = "date_modif",nullable = true)
    private Timestamp dateModif;
}
