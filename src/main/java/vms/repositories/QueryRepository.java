package vms.repositories;


import org.springframework.data.repository.CrudRepository;
import vms.models.postenvironment.Query;

import java.util.List;

public interface QueryRepository extends CrudRepository<Query, Long> {
      Query getQueryByWordAndFromwhere(String query, String fromwhere);
      Query getQueryById(Long id);
      List<Query> findAllByFromwhere(String from);
 }
