package com.accountbook.backend

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@RestController
@RequestMapping("/api")
class ImageController {

    private val tempMap = hashMapOf<String, InputStream>()

    @RequestMapping("/get/{fileName}", method = [RequestMethod.GET])
    fun getImage(
        @PathVariable("fileName")
        fileName: String
    ): ResponseEntity<ByteArray> {
        val bytes = if (tempMap[fileName] != null) {
            StreamUtils.copyToByteArray(tempMap[fileName])
        } else {
            val file = ClassPathResource("images/$fileName")
            StreamUtils.copyToByteArray(file.inputStream)
        }
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes)
    }

    @RequestMapping("/upload", method = [RequestMethod.POST])
    fun upload(
        request: HttpServletRequest
    ): Int {
        val dirPath = "${getAbsolutePath()}/backend/src/main/resources/images"
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdir()
        }

        var count = 0
        for (item in request.parts) {
            if (item.contentType == "image/jpg; charset=utf-8") {
                val file = File("$dirPath/${item.submittedFileName}")
                val fout = FileOutputStream(file)
                tempMap[item.submittedFileName] = file.inputStream()
                item.inputStream.transferTo(fout)
                fout.close()
                count++
            }
        }
        return count
    }

    private fun getAbsolutePath(): String {
        return File("").absolutePath
    }
}