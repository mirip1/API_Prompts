package accenture.prompts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "variable")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Variable {

  @ManyToOne
  @JoinColumn(name = "prompt_collection_id", nullable = false)
  private PromptCollection collection;

  @Id
  private String clave;

  private String valor;

}

