package br.com.lucas.services;

import br.com.lucas.data.vo.v1.PersonVO;
import br.com.lucas.exceptions.RequiredObjectIsNullException;
import br.com.lucas.model.Person;
import br.com.lucas.repositories.PersonRepository;
import mapper.mocks.MockPerson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() throws Exception {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());
        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getLinks());
        assertNotNull(personOne.getKey());
        assertTrue(personOne.toString().contains("links"));
        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());
        var personFour = people.get(4);
        assertNotNull(personFour);
        assertNotNull(personFour.getLinks());
        assertNotNull(personFour.getKey());
        assertTrue(personFour.toString().contains("links"));
        assertEquals("Addres Test1", personFour.getAddress());
        assertEquals("First Name Test1", personFour.getFirstName());
        assertEquals("Last Name Test1", personFour.getLastName());
        assertEquals("Male", personFour.getGender());
        var personSeven = people.get(7);
        assertNotNull(personSeven);
        assertNotNull(personSeven.getLinks());
        assertNotNull(personSeven.getKey());
        assertTrue(personSeven.toString().contains("links"));
        assertEquals("Addres Test1", personSeven.getAddress());
        assertEquals("First Name Test1", personSeven.getFirstName());
        assertEquals("Last Name Test1", personSeven.getLastName());
        assertEquals("Male", personSeven.getGender());

    }

    @Test
    void create() throws Exception {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(repository.save(entity)).thenReturn(persisted);
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }
    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "nao e possivel atualizar um objeto nulo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "nao e possivel atualizar um objeto nulo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createv2() {
    }

    @Test
    void update() throws Exception {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);
        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() throws Exception {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);
       service.delete(1L);
    }
}