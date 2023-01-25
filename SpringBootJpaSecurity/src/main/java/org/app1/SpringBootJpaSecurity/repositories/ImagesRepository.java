package org.app1.SpringBootJpaSecurity.repositories;

import org.app1.SpringBootJpaSecurity.models.PersonImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<PersonImageInfo, Long> {
}
