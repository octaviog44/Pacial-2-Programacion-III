package com.utn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria extends Base {

    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private Set<Producto> productos = new HashSet<>();
}