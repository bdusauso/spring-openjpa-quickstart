package be.codinsanity.quickstart.domain;

import javax.persistence.*;

@Entity
@Table(name = "dummy")
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dummyId")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
