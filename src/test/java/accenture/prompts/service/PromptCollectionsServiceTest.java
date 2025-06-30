package accenture.prompts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accenture.prompts.beans.PromptCollectionRequest;
import accenture.prompts.beans.PromptCollectionResponse;
import accenture.prompts.beans.PromptRequest;
import accenture.prompts.beans.VariableRequest;
import accenture.prompts.model.Prompt;
import accenture.prompts.model.PromptCollection;
import accenture.prompts.model.Variable;
import accenture.prompts.repository.PromptCollectionRepository;
import accenture.prompts.utils.PromptCollectionMapper;


@ExtendWith(MockitoExtension.class)
public class PromptCollectionsServiceTest {

  @Mock
  private PromptCollectionRepository repository;

  @Mock
  private PromptCollectionMapper mapper;

  @InjectMocks
  private PromptCollectionService service;

  private PromptCollectionRequest sampleRequest;
  private PromptCollection entity;
  private PromptCollectionResponse response;

  @BeforeEach
  public void setUp() {
    sampleRequest = new PromptCollectionRequest();
    sampleRequest.setNombre("test");
    sampleRequest.setObjetivo("objetivo");
    sampleRequest.setPrompts(List.of(new PromptRequest("instruccion", "prompt", 1)));
    sampleRequest.setVariables(List.of(new VariableRequest("clave", "valor")));
    entity = new PromptCollection();
    entity.setId(1);
    entity.setNombre("test");
    entity.setObjetivo("objetivo");
    entity.setPrompts(List.of(new Prompt()));
    entity.setVariables(List.of(new Variable()));
    response = new PromptCollectionResponse();
    response.setId(1);
    response.setNombre("test");
    response.setObjetivo("objetivo");
  }

  @Test
  public void createCollectionTest() {
    when(repository.existsByNombre("test")).thenReturn(false);
    when(mapper.toEntity(sampleRequest)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    PromptCollectionResponse result = service.createCollection(sampleRequest);
    assertEquals(response, result);

  }

  @Test
  public void deleteByNombreTest() {
    PromptCollection collection = new PromptCollection();
    when(repository.findByNombre("test")).thenReturn(Optional.of(collection));
    service.deleteByNombre("test");
  }

  @Test
  public void listAllTest() {
    when(repository.findAll()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    List<PromptCollectionResponse> list = service.listAll();
    assertEquals(1, list.size());
    assertEquals(response, list.get(0));
  }

}
