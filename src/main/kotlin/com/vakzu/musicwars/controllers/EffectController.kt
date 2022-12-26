package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.AllEffectResponse
import com.vakzu.musicwars.dto.EffectDto
import com.vakzu.musicwars.repos.EffectRepository
import com.vakzu.musicwars.security.MyUserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/effect")
class EffectController(val effectRepository: EffectRepository) {

    @GetMapping("/all")
    fun getAllEffects(principal: Principal): AllEffectResponse {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val effectShop = effectRepository.findEffectShopInfoByUserId(user.id)
        val res = effectShop.map {
            EffectDto(
                it.getId(),
                it.getName(),
                it.getPrice(),
                it.getStamina(),
                it.getStrength(),
                it.getLuck(),
                it.getConstitution()
            )
        }
        return AllEffectResponse(res)
    }

    @PostMapping("/buy")
    @ResponseBody
    fun buyEffect(@RequestParam("effectId") effectId: Int, principal: Principal): ResponseEntity<*> {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val result = effectRepository.buyEffect(user.id, effectId)
        return ResponseEntity<Void>(if (result) HttpStatus.OK else HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/user")
    fun getUserInventory(principal: Principal): AllEffectResponse {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val inventory = effectRepository.findEffectsByUserId(user.id)
        val result = inventory.map {
            EffectDto(
                it.id,
                it.name,
                it.price,
                it.stamina,
                it.strength,
                it.luck,
                it.constitution
            )
        }
        return AllEffectResponse(result)
    }

}