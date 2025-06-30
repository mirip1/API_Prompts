package accenture.prompts.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import accenture.prompts.beans.PromptCollectionRequest;
import accenture.prompts.beans.PromptCollectionResponse;
import accenture.prompts.beans.PromptRequest;
import accenture.prompts.beans.VariableRequest;
import accenture.prompts.model.PromptCollection;
import accenture.prompts.model.Variable;
import accenture.prompts.repository.PromptCollectionRepository;
import accenture.prompts.utils.PromptCollectionMapper;

@Service
public class PromptCollectionService {
  private final PromptCollectionRepository collectionRepository;

  private final PromptCollectionMapper mapper;

  /**
   * Constructor
   * 
   * @param collectionRepository
   * @param promptRepository
   * @param variableRepo
   * @param mapper
   */
  public PromptCollectionService(PromptCollectionRepository collectionRepository, PromptCollectionMapper mapper) {
    this.collectionRepository = collectionRepository;
    this.mapper = mapper;
  }

  /**
   * Crea una coleccion
   * 
   * @param request
   * @return la colección creada
   */
  public PromptCollectionResponse createCollection(PromptCollectionRequest request) {
    if (collectionRepository.existsByNombre(request.getNombre())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Ya existe una collecion con el nombre: " + request.getNombre());
    }
    PromptCollection collection = mapper.toEntity(request);
    PromptCollection entity = collectionRepository.save(collection);
    return mapper.toResponse(entity);
  }

  /**
   * Borra una coleccion por nombre
   * 
   * @param nombre
   */
  public void deleteByNombre(String nombre) {
    PromptCollection collection = collectionRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe"));
    collectionRepository.delete(collection);
  }

  /**
   * Lista todas las colecciones
   * 
   * @return
   */
  public List<PromptCollectionResponse> listAll() {
    return collectionRepository.findAll().stream().map(mapper::toResponse).toList();
  }

  /**
   * Actualiza una collecion por el nombre
   * 
   * @param nombre
   * @param request
   * @return la coleccion creada
   */
  public PromptCollectionResponse updateByNombre(String nombre, PromptCollectionRequest request) {
    PromptCollection collection = collectionRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe"));
    collection.setObjetivo(request.getObjetivo());
    collection.getPrompts().clear();
    collection.getVariables().clear();
    createCollection(request);
    PromptCollection entity = collectionRepository.save(collection);
    return mapper.toResponse(entity);
  }

  /**
   * Método para añadir prompts a una collecion
   * 
   * @param nombre  de la colección a la que se le quiere añadir
   * @param request lista de prompt a añadir
   * @return PromptCollectionResponse con los datos añadidos
   */
  public PromptCollectionResponse addPrompts(String nombre, List<PromptRequest> request) {
    var collection = collectionRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la colección"));
    request.stream().map(mapper::toPromptEntity).forEach(p -> {
      p.setCollection(collection);
      collection.getPrompts().add(p);
    });
    var saved = collectionRepository.save(collection);
    return mapper.toResponse(saved);
  }

  /**
   * Método para añadir variables a una collecion
   * 
   * @param nombre  de la colección a la que se le quiere añadir
   * @param request lista de variables a añadir
   * @return PromptCollectionResponse con los datos añadidos
   */
  public PromptCollectionResponse addVariables(String nombre, List<VariableRequest> request) {
    var collection = collectionRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la colección"));
    request.stream().map(mapper::toVariableEntity).forEach(v -> {
      v.setCollection(collection);
      collection.getVariables().add(v);
    });
    var saved = collectionRepository.save(collection);
    return mapper.toResponse(saved);
  }

  /**
   * Método que muestra los datos de una colección concreta según el nombre
   * 
   * @param nombre de la colección a mostrar
   * @return PromptCollectionResponse de la colección
   */
  public PromptCollectionResponse getCollectionByNombre(String nombre) {
    PromptCollection collection = collectionRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe"));
    return mapper.toResponse(collection);
  }
  
  /**
   * Genera el prompt concatenado para una colección
   *
   * @param collectionName nombre de la colección
   * @return texto procesado
   */
  public String generatePrompt(String collectionName) {
    PromptCollection collection = collectionRepository.findByNombre(collectionName).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la colección: " + collectionName));
    List<String> prompts = collection.getPrompts().stream()
        .sorted(Comparator.comparing(p -> Optional.ofNullable(p.getOrderIndex()).orElse(0))).map(p -> p.getPromptText())
        .toList();

    Map<String, String> variables = collection.getVariables().stream()
        .collect(Collectors.toMap(Variable::getClave, Variable::getValor));
    
    String textoBruto = String.join("\n\n", prompts);
    String textoProcesado = replaceVariables(textoBruto, variables);
    return textoProcesado;
  }
  
  /**
   * Sustituyo todas las variables por su valor
   * @param textoBruto
   * @param variables
   * @return texto procesado
   */
  private String replaceVariables(String textoBruto, Map<String, String> variables) {
    String textoProcesado = textoBruto;

    for (Map.Entry<String, String> entry : variables.entrySet()) {
      String variableARemplazar = "${" + entry.getKey() + "}";
      textoProcesado = textoProcesado.replace(variableARemplazar, entry.getValue());
    }
    return textoProcesado;

  }
}
