package com.upv.proyectounidad1equipo3;

import java.util.Date;

public class Tickets {

    private int id;
    private String nombre_ticket;
    private String Place;
    private Float Total;
    private String fecha_ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_ticket() {
        return nombre_ticket;
    }

    public void setNombre_ticket(String nombre_ticket) {
        this.nombre_ticket = nombre_ticket;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
    }

    public String getFecha_ticket() {
        return fecha_ticket;
    }

    public void setFecha_ticket(String fecha_ticket) {
        this.fecha_ticket = fecha_ticket;
    }
}
