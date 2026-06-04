package com.utn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria extends Base {

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private Set<Producto> productos = new HashSet<>();
}