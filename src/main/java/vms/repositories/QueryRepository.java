package vms.repositories;


import org.springframework.data.repository.CrudRepository;
import vms.models.postenvironment.Query;

public interface QueryRepository extends CrudRepository<Query, Long> {
 }
