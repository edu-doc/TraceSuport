package br.edu.ufersa.tracesuport.TraceSuport.entities;

import jakarta.persistence.Table;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name="tb_events")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

    @NotNull
    String city;

    @NotNull
    String district;

    @NotNull
    String address;

    @NotNull
    String number;

    @NotNull
    String phone;

    @NotNull
    String latitude;

    @NotNull
    String longitude;

    @Column(unique=true)
    @NotNull
    String description;
}
