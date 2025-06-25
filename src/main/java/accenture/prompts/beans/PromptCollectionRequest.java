package accenture.prompts.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromptCollectionRequest {
  private String nombre;
  private String objetivo;
  private String descripcion;
  private List<PromptRequest> prompts;
  private List<VariableRequest> variables;
}
