package com.kuuy.taoniu.data.images.mappings

import com.kuuy.taoniu.data.images.dto.ImageInfoDto
import com.kuuy.taoniu.data.images.models.ImageInfo

fun ImageInfoDto.transform(): ImageInfo {
  return ImageInfo(
    filename,
  )
}
