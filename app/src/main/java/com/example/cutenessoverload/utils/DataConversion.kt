package com.example.cutenessoverload.utils

import com.example.cutenessoverload.api.APIServiceCat
import com.example.cutenessoverload.database.CuteEntity
import com.example.cutenessoverload.model.*

fun Dog.toCuteGeneric(): CuteGeneric =
    CuteGeneric(
        image_url = this.message,
        cute_type = "dog",
        is_gif = this.message.contains(".gif")
    )

fun Cat.toCuteGeneric(): CuteGeneric =
    CuteGeneric(
        image_url =  APIServiceCat.BASE_URL + this.url,
        cute_type = "cat",
        is_gif = this.url.contains(".gif")
    )

fun Duck.toCuteGeneric(): CuteGeneric =
    CuteGeneric(
        image_url =  this.url,
        cute_type = "duck",
        is_gif = this.url.contains(".gif")
    )

fun Fox.toCuteGeneric(): CuteGeneric =
    CuteGeneric(
        image_url = this.image.replace("\\",""),
        cute_type = "fox",
        is_gif = false
    )

fun CuteEntity.toCuteGeneric(): CuteGeneric =
    CuteGeneric(
        image_url = this.image_url,
        cute_type = this.cute_type,
        is_gif = this.is_gif
    )

fun CuteGeneric.toCuteEntity(): CuteEntity =
    CuteEntity(
        image_url = this.image_url,
        cute_type = this.cute_type,
        is_gif = this.is_gif
    )