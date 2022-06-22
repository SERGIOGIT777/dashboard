package com.example.trainroad.repository;

import com.example.trainroad.entities.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CabinetRepository extends JpaRepository<Cabinet,Long> {
    @Query(value = "select c from Cabinet c where c.id = :id")
    Cabinet getId(@Param(value = "id") Long id);

    @Modifying
    @Query(value = "update Cabinet c set c.position=:position, c.ranks=:ranks where c.id=:id")
    void updateBook(@Param(value = "id") long id,
                    @Param(value = "position") String position,
                    @Param(value = "ranks") Integer ranks);

    @Query(value = "select c from Cabinet c where c.position like %:position%")
    List<Cabinet> findPosition(@Param(value = "position") String position);

    @Query(value = "select c from Cabinet c where c.peoples.name like %:name%")
    List<Cabinet> findName(@Param(value = "name") String name);
}
