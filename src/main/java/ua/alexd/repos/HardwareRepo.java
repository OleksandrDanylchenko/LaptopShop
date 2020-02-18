package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Hardware;

public interface HardwareRepo extends CrudRepository<Hardware, Integer> { }