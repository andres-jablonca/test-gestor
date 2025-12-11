package com.gestor_disp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestor_disp.model.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, String> {

}
