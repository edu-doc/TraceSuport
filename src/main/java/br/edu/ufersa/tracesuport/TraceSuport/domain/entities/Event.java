package br.edu.ufersa.tracesuport.TraceSuport.domain.entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
@Table(name="tb_events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    @NotNull
    String description;

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }


}
