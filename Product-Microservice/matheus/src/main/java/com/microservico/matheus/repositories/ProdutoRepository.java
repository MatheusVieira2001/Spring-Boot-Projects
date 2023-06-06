package com.microservico.matheus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservico.matheus.model.*;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	  boolean existsByNome(String nome);
}
