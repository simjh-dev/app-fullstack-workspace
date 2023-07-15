package com.jh.myownvocabularynotebook.util

import android.content.Context
import android.net.Uri
import com.jh.myownvocabularynotebook.room.entity.NoteItem
import com.jh.myownvocabularynotebook.util.Const.CACHE_FILE_NAME
import com.jh.myownvocabularynotebook.util.Const.TEXT_KEY
import com.jh.myownvocabularynotebook.util.Const.TEXT_RESULT
import com.jh.myownvocabularynotebook.util.Const.TEXT_VALUE
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*

class FileUtil {
    companion object {
        fun isXlsxType(uri: Uri): Boolean {
            val str = uri.toString()
            return str.substring(str.lastIndexOf(".") + 1) == Const.TEXT_XLSX
        }

        fun readExcel(fileUri: Uri, context: Context): Workbook? {
            val fin = context.contentResolver.openInputStream(fileUri)
            if (fin != null) {
                val fileName = CACHE_FILE_NAME
                val file = File(context.cacheDir, fileName)
                file.createNewFile()
                val fout = FileOutputStream(file)
                return try {
                    copy(fin, fout)
                    WorkbookFactory.create(file)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                } finally {
                    fin.close()
                    fout.flush()
                    fout.close()
                }
            } else return null
        }

        fun makeExcel(list: List<NoteItem>): XSSFWorkbook {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet(TEXT_RESULT)
            var rowIdx = 0
            val row = sheet.createRow(rowIdx++)
            val keyCellIdx = 0
            val valueCellIdx = 1
            val keyCell = row.createCell(keyCellIdx)
            val valueCell = row.createCell(valueCellIdx)
            keyCell.setCellValue(TEXT_KEY)
            valueCell.setCellValue(TEXT_VALUE)
            for (item in list) {
                val itemRow = sheet.createRow(rowIdx++)
                val itemKeyCell = itemRow.createCell(keyCellIdx)
                val itemValueCell = itemRow.createCell(valueCellIdx)
                itemKeyCell.setCellValue(item.key)
                itemValueCell.setCellValue(item.value)
            }
            return workbook
        }

        @Throws(IOException::class)
        private fun copy(source: InputStream, target: OutputStream) {
            val buf = ByteArray(8192)
            var length: Int
            while (source.read(buf).also { length = it } > 0) {
                target.write(buf, 0, length)
            }
        }
    }

}