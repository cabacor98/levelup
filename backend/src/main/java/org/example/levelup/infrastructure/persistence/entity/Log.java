package org.example.levelup.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private LocalDateTime fecha;

    public Log() {}

    public Log(String mensaje, LocalDateTime fecha) {
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFecha() { return fecha; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}