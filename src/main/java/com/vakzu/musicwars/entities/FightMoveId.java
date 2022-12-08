package com.vakzu.musicwars.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FightMoveId implements Serializable {
    private static final long serialVersionUID = 1587963462501296711L;
    @Column(name = "move_number", nullable = false)
    private Integer moveNumber;

    @Column(name = "fight_id", nullable = false)
    private Integer fightId;

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Integer getFightId() {
        return fightId;
    }

    public void setFightId(Integer fightId) {
        this.fightId = fightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FightMoveId entity = (FightMoveId) o;
        return Objects.equals(this.moveNumber, entity.moveNumber) &&
                Objects.equals(this.fightId, entity.fightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveNumber, fightId);
    }

}