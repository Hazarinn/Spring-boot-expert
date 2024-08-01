package io.github.Hazarinn.domain.repository;

import io.github.Hazarinn.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
