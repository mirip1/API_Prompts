package accenture.prompts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accenture.prompts.model.Variable;

public interface VariableRepository extends JpaRepository<Variable, String>{
  boolean existsByClave(String nombre);
}
