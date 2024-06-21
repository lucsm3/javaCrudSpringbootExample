package br.com.lucas.services;

import br.com.lucas.PersonController;
import br.com.lucas.data.vo.v1.PersonVO;
import br.com.lucas.data.vo.v2.PersonVOV2;
import br.com.lucas.exceptions.RequiredObjectIsNullException;
import br.com.lucas.exceptions.ResourceNotFoundException;
import br.com.lucas.mapper.DozerMapper;
import br.com.lucas.mapper.custom.PersonMapper;
import br.com.lucas.model.Person;
import br.com.lucas.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;
@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
//        return repository.findAll();
        var persons =  DozerMapper.parseListsObjects(repository.findAll(), PersonVO.class);
        persons.stream().forEach(p -> {
            try {
                p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return persons;
    }

    public PersonVO findById(Long id) throws Exception {
        logger.info("finding one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no record"));
        PersonVO vo = DozerMapper.parseObject(entity,PersonVO.class);
       return vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    }

    public PersonVO create(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createv2(PersonVOV2 person) {
        logger.info("Creating one person with v2!");
        var entity = mapper.convertVoToEntity(person);
        var vo =  mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) throws Exception {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");
        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
