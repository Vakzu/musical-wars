package com.vakzu.musicwars.entities;

import javax.persistence.*;

@Entity
@Table(name = "fight_moves")
public class FightMove {
    @EmbeddedId
    private FightMoveId id;

    private Integer attackerId;

    private Integer victimId;

    @Column(name = "damage", nullable = false)
    private Integer damage;

    public FightMoveId getId() {
        return id;
    }

    public void setId(FightMoveId id) {
        this.id = id;
    }

    public Integer getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(Integer attackerId) {
        this.attackerId = attackerId;
    }

    public Integer getVictimId() {
        return victimId;
    }

    public void setVictimId(Integer victimId) {
        this.victimId = victimId;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

}