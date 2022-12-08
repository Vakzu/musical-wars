package com.vakzu.musicwars.repos

import com.vakzu.musicwars.entities.FightMove
import com.vakzu.musicwars.entities.FightMoveId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FightMoveRepository : JpaRepository<FightMove, FightMoveId> {


    @Query(nativeQuery = true,
    value = "SELECT * FROM begin_fight(?1, ?2, ?3, ?4)")
    fun playFight(characterIds: List<Int>, effectIds: List<Int>, songIds: List<Int>, locationId: Int): List<FightMove>
}