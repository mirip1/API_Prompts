package accenture.prompts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accenture.prompts.model.Prompt;

public interface  PromptRepository extends JpaRepository<Prompt, Integer>{

}
