package accenture.prompts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import accenture.prompts.model.PromptCollection;

public interface PromptCollectionRepository extends JpaRepository<PromptCollection, Integer>{
  Optional<PromptCollection> findByNombre(String nombre);
  boolean existsByNombre(String nombre);

}
