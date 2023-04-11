package at.spengergasse._2223_GenericUser.persistence;

import at.spengergasse._2223_GenericUser.domain.GenericUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<GenericUser,Long> {
    Optional<GenericUser> findByUsername(String Username);
    Optional<GenericUser> findByFirstnameAndLastname(String firstname,String lastname);
    void deleteByFirstnameAndLastname(String firstname,String lastname);
    void deleteByUsername(String username);
}
