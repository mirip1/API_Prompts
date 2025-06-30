package accenture.prompts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "prompt")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prompt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "prompt_collection_id", nullable = false)
  private PromptCollection collection;

  @Column(name = "instruccion", nullable = false)
  private String instruccion;

  @Column(name = "prompt_text", columnDefinition = "TEXT", nullable = false)
  private String promptText;
  
  @Column(name = "order_index")
  private Integer orderIndex;

}

