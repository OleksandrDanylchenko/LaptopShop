package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Label;

public interface LabelRepo extends CrudRepository<Label, Integer>, JpaSpecificationExecutor<Label> { }