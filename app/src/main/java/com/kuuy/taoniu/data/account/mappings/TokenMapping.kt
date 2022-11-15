package com.kuuy.taoniu.data.account.mappings

import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.data.account.models.Token

fun TokenDto.transform(): Token {
  return Token(
    access,
    refresh,
  )
}
