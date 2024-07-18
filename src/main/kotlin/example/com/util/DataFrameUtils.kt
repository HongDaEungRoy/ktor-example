package example.com.util


import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.io.readExcel
import org.jetbrains.kotlinx.dataframe.io.toJson
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File
import java.io.FileInputStream
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.all
import kotlin.collections.any
import kotlin.collections.associateWith
import kotlin.collections.filterIsInstance
import kotlin.collections.first
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.reduce
import kotlin.collections.set

object DataFrameUtils {

    /**
     * Excel 파일을 DataFrame으로 변환
     */
    fun excelToDataFrame(filePath: String): DataFrame<*> {
        val sheetNames = getExcelSheetNames(filePath)

        return sheetNames.map { sheetName ->
            DataFrame.readExcel(filePath, sheetName)
                .filter { row -> row.values().any { it != null && it.toString().isNotBlank() } }
        }.reduce { acc, df -> acc.concat(df) }
    }

    private fun getExcelSheetNames(filePath: String): List<String> {
        FileInputStream(filePath).use { fis ->
            XSSFWorkbook(fis).use { workbook ->
                return (0 until workbook.numberOfSheets).map { workbook.getSheetName(it) }
            }
        }
    }

    /**
     * XML 파일을 DataFrame으로 변환 - test 필요
     */
    fun xmlToDataFrame(filePath: String): DataFrame<*> {
        val xmlContent = File(filePath).readText()
        val xml = parse(xmlContent)
        val dataList = parseXmlNode(xml)
        return dataFrameOf(dataList)
    }

    private fun parseXmlNode(node: Node): List<Map<String, String>> {
        val result = mutableListOf<Map<String, String>>()
        val data = mutableMapOf<String, String>()

        for (child in node.children) {
            when (child) {
                is TextElement -> {
                }
                is Node -> {
                    if (child.children.isEmpty() || child.children.all { it is TextElement }) {
                        // 자식이 없거나 모든 자식이 TextElement인 경우, 노드의 내용을 가져옵니다.
                        data[child.nodeName] =
                            child.children.filterIsInstance<TextElement>().joinToString("") { it.text }
                    } else {
                        // 자식이 있는 경우, 재귀적으로 처리합니다.
                        result.addAll(parseXmlNode(child))
                    }
                }
            }
        }

        if (data.isNotEmpty()) {
            result.add(data)
        }

        return result
    }

    private fun dataFrameOf(data: List<Map<String, String>>): DataFrame<*> {
        if (data.isEmpty()) return DataFrame.empty()

        val columns = data.first().keys
        return columns.associateWith { columnName ->
            data.map { it[columnName] ?: "" }
        }.toDataFrame()
    }

    /**
     * DataFrame을 JSON 문자열로 변환
     */
    fun dataFrameToJson(df: DataFrame<*>, prettyPrint: Boolean = false): String {
        return df.toJson(prettyPrint = prettyPrint)
    }
}