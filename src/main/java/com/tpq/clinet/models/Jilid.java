package com.tpq.clinet.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jilid {
    private Integer id;
    private String name;
    private List<Santri> santri;
    private Kelas kelas;

}