package com.tpq.clinet.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kelas {
    private Integer id;
    private String name;
    private List<Jilid> jilid;
    private List<Pengajar> pengajar;
}
