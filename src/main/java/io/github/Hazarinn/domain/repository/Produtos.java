package io.github.Hazarinn.domain.repository;

import io.github.Hazarinn.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
