package com.example.trainroad.repository;

import com.example.trainroad.entities.Peoples;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Peoples, Long> {

    @Query(value = "select c from Peoples c where c.login = :login")
    List<Peoples> findByLogin(@Param("login") String login);

    @Query(value = "select c from Peoples c where c.id = :id")
    Peoples getId(@Param(value = "id") Long id);

    @Modifying
    @Query(value = "update Peoples c set c.password=:password where c.id=:id")
    void updatePassword(@Param(value = "id") long id,
                        @Param(value = "password") String password);

    @Modifying
    @Query(value = "update Peoples c set c.login=:login, c.password=:password, c.authority=:authority," +
            "c.name=:name, c.email=:email, c.age=:age where c.id=:id")
    void updateUsers(@Param(value = "id") long id, @Param(value = "login") String login,
                     @Param(value = "password") String password,
                     @Param(value = "authority") String authority,
                     @Param(value = "name") String name,
                     @Param(value = "email") String email,
                     @Param(value = "age") Integer age);

    @Query(value = "select c from Peoples c where c.name=:name")
    List<Peoples> findName(@Param(value = "name") String name);

    @Query(value = "select c from Peoples c where c.name like %:name%")
    List<Peoples> findNameAll(@Param(value = "name") String name);
}
