package com.example.trainroad.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cabinet")
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_peoples")
    private Peoples peoples;

    private String position;

    @NotNull(message = "Поле должно быть заполнено")
    @Max(value = 3, message = "Не более 3")
    private Integer ranks;

    public Long getId() {
        return id;
    }

    @Column(name = "id_peoples")
    public Peoples getPeoples() {
        return peoples;
    }

    public void setPeoples(Peoples peoples) {
        this.peoples = peoples;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "ranks")
    public Integer getRanks() {
        return ranks;
    }

    public void setRanks(Integer ranks) {
        this.ranks = ranks;
    }
}
