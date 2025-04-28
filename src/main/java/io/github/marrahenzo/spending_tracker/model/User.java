package io.github.marrahenzo.spending_tracker.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@Entity
public class User extends DatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    @Column(unique = true)
    private String username;

    private String password;
}
