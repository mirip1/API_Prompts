package accenture.prompts.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accenture.prompts.beans.PromptCollectionRequest;
import accenture.prompts.beans.PromptCollectionResponse;
import accenture.prompts.beans.PromptRequest;
import accenture.prompts.beans.VariableRequest;
import accenture.prompts.service.PromptCollectionService;

@RestController
@RequestMapping("/collections")
public class PromptCollectionController {

  private final PromptCollectionService service;

  /**
   * Constructor
   * 
   * @param service
   */
  public PromptCollectionController(PromptCollectionService service) {
    this.service = service;
  }

  /**
   * Metodo que crea una collecion
   * 
   * @param request
   * @return la collecion creada
   */
  @PostMapping
  public ResponseEntity<PromptCollectionResponse> create(@RequestBody PromptCollectionRequest request) {
    PromptCollectionResponse collection = service.createCollection(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(collection);
  }

  /**
   * Método que borra una coleccion por el nombre
   * 
   * @param nombre
   * @return
   */
  @DeleteMapping("/{nombre}")
  public ResponseEntity<Void> delete(@PathVariable String nombre) {
    service.deleteByNombre(nombre);
    return ResponseEntity.noContent().build();
  }

  /**
   * Método que devuelve el listado de todas las colleciones
   * 
   * @return
   */
  @GetMapping
  public List<PromptCollectionResponse> listAll() {
    return service.listAll();
  }

  /**
   * Actualiza la colección según el nombre
   * 
   * @param nombre
   * @param req
   * @return
   */
  @PutMapping("/{nombre}")
  public PromptCollectionResponse update(@PathVariable String nombre, @RequestBody PromptCollectionRequest req) {
    return service.updateByNombre(nombre, req);
  }

  /**
   * Añade una lista de prompts a la colección identificada por nombre.
   * 
   * @param nombre  nombre de la colección
   * @param prompts lista de nuevos PromptRequest
   * @return colección actualizada
   */
  @PostMapping("/{nombre}/prompts")
  public ResponseEntity<PromptCollectionResponse> addPrompts(@PathVariable String nombre,
      @RequestBody List<PromptRequest> prompts) {
    PromptCollectionResponse updated = service.addPrompts(nombre, prompts);
    return ResponseEntity.ok(updated);
  }

  /**
   * Añade una lista de variables a la colección identificada por nombre.
   * 
   * @param nombre    nombre de la colección
   * @param variables lista de nuevos VariableRequest
   * @return colección actualizada
   */
  @PostMapping("/{nombre}/variables")
  public ResponseEntity<PromptCollectionResponse> addVariables(@PathVariable String nombre,
      @RequestBody List<VariableRequest> variables) {
    PromptCollectionResponse updated = service.addVariables(nombre, variables);
    return ResponseEntity.ok(updated);
  }

  /**
   * Muestra la información de una colección en concreta
   * 
   * @param nombre de la colección a mostrar
   * @return datos de la colección
   */
  @GetMapping("/{nombre}")
  public PromptCollectionResponse getByNombre(@PathVariable String nombre) {
    return service.getCollectionByNombre(nombre);
  }
  
  /**
   * Genera y devuelve el prompt completo para la colección indicada.
   * 
   * @param nombre nombre de la colección
   * @return texto con todos los prompts concatenados y variables sustituidas
   */
  @GetMapping("/{nombre}/generatePrompt")
  public ResponseEntity<String> generatePrompt(@PathVariable String nombre) {
    String result = service.generatePrompt(nombre);
    return ResponseEntity.ok(result);
  }
  
}
