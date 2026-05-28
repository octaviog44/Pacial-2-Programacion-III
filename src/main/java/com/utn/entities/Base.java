package com.utn.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Base {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
protected Long id;

protected Boolean eliminado = false;

protected LocalDateTime createdAt;

@PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.eliminado = false;
}


}
