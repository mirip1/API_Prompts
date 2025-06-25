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
public class PromptCollectionResponse {
  private Integer id;
  private String nombre;
  private String objetivo;
  private String descripcion;
  private List<PromptResponse> prompts;
  private List<VariableResponse> variables;
}
