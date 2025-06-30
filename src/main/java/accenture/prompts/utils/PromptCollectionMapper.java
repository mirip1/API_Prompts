package accenture.prompts.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import accenture.prompts.beans.PromptCollectionRequest;
import accenture.prompts.beans.PromptCollectionResponse;
import accenture.prompts.beans.PromptRequest;
import accenture.prompts.beans.PromptResponse;
import accenture.prompts.beans.VariableRequest;
import accenture.prompts.beans.VariableResponse;
import accenture.prompts.model.Prompt;
import accenture.prompts.model.PromptCollection;
import accenture.prompts.model.Variable;

@Component
public class PromptCollectionMapper {

  /**
   * Metodo que pasa un objeto PromptCollectionRequest a entidad
   * 
   * @param request a convertir
   * @return entidad
   */
  public PromptCollection toEntity(PromptCollectionRequest request) {
    PromptCollection entity = PromptCollection.builder().nombre(request.getNombre()).objetivo(request.getObjetivo())
        .descripcion(request.getDescripcion()).build();

    if (request.getPrompts() != null) {
      List<Prompt> prompts = request.getPrompts().stream().map(this::toPromptEntity).collect(Collectors.toList());
      prompts.forEach(p -> p.setCollection(entity));
      entity.setPrompts(prompts);
    }

    if (request.getVariables() != null) {
      List<Variable> variables = request.getVariables().stream().map(this::toVariableEntity)
          .collect(Collectors.toList());
      variables.forEach(v -> v.setCollection(entity));
      entity.setVariables(variables);
    }

    return entity;
  }

  /**
   * Convierte una entidad PromptCollection a su response
   * 
   * @param entity la entidad a convertir
   * @return PromptCollectionResponse
   */
  public PromptCollectionResponse toResponse(PromptCollection entity) {
    PromptCollectionResponse response = PromptCollectionResponse.builder().id(entity.getId()).nombre(entity.getNombre())
        .objetivo(entity.getObjetivo()).descripcion(entity.getDescripcion()).build();

    if (entity.getPrompts() != null) {
      List<PromptResponse> prompts = entity.getPrompts().stream().map(this::toPromptResponse)
          .collect(Collectors.toList());
      response.setPrompts(prompts);
    }

    if (entity.getVariables() != null) {
      List<VariableResponse> variables = entity.getVariables().stream().map(this::toVariableResponse)
          .collect(Collectors.toList());
      response.setVariables(variables);
    }

    return response;
  }

  /**
   * Pasa un objeto PromptRequest a entidad
   * 
   * @param request a convertir
   * @return entidad
   */
  public Prompt toPromptEntity(PromptRequest request) {
    Prompt p = Prompt.builder().promptText(request.getPromptText()).instruccion(request.getInstruccion())
        .orderIndex(request.getOrder()).build();
    return p;
  }

  /**
   * Pasa un objeto VariableRequest a entidad
   * 
   * @param request a convertir
   * @return entidad
   */
  public Variable toVariableEntity(VariableRequest request) {
    Variable v = Variable.builder().valor(request.getValor()).clave(request.getClave()).build();
    return v;
  }

  /**
   * Convierte una entidad Prompt a su PromptResponse
   * 
   * @param entity la entidad a convertir
   * @return PromptResponse de respuesta
   */
  private PromptResponse toPromptResponse(Prompt entity) {
    return PromptResponse.builder().id(entity.getId()).instruccion(entity.getInstruccion())
        .promptText(entity.getPromptText()).order(entity.getOrderIndex()).build();
  }

  /**
   * Convierte una entidad Variable VariableResponse
   * 
   * @param entity la entidad a convertir
   * @return VariableResponse de respuesta
   */
  private VariableResponse toVariableResponse(Variable entity) {
    return VariableResponse.builder().clave(entity.getClave()).valor(entity.getValor()).build();
  }
}
