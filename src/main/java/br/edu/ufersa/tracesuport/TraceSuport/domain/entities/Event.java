package br.edu.ufersa.tracesuport.TraceSuport.domain.entities;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Setter
@Getter
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
