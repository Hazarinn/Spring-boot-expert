package io.github.Hazarinn.domain.repository;

import io.github.Hazarinn.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
