package com.kuuy.taoniu.data

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

class UriRequestBody(
  private val uri: Uri,
  private val contentResolver: ContentResolver
): RequestBody() {
  override fun contentType(): MediaType? =
      contentResolver.getType(uri)?.toMediaTypeOrNull()

  override fun contentLength(): Long = -1

  override fun writeTo(sink: BufferedSink) {
    contentResolver
        .openInputStream(uri)
        ?.source()
        ?.use { source ->
      sink.writeAll(source)
    }
  }
}
