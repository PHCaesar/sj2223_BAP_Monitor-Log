package at.spengergasse._2223_GenericUser.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class GenericUser extends AbstractPersistable<Long> {

    @Version
    public Integer version;

    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private LocalDateTime registerDate;
    private LocalDateTime birthDate;
}
