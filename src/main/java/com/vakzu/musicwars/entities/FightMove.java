package com.vakzu.musicwars.entities;

import javax.persistence.*;

@Entity
@Table(name = "fight_moves")
public class FightMove {
    @EmbeddedId
    private FightMoveId id;

    @MapsId("fightId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fight_id", nullable = false)
    private Fight fight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attacker_id", nullable = false)
    private FightParticipant attacker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "victim_id")
    private FightParticipant victim;

    @Column(name = "damage", nullable = false)
    private Integer damage;

    public FightMoveId getId() {
        return id;
    }

    public void setId(FightMoveId id) {
        this.id = id;
    }

    public Fight getFight() {
        return fight;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    public FightParticipant getAttacker() {
        return attacker;
    }

    public void setAttacker(FightParticipant attacker) {
        this.attacker = attacker;
    }

    public FightParticipant getVictim() {
        return victim;
    }

    public void setVictim(FightParticipant victim) {
        this.victim = victim;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

}