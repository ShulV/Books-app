package org.app1.SpringBootJpaSecurity.repositories;

import org.app1.SpringBootJpaSecurity.models.PersonImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonImagesRepository extends JpaRepository<PersonImage, Integer> {
}
