package com.example.k_ovid_map

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-09-15
 * @desc
 */
class JsonUrl(addr: String){

    var total : Int = 0
    var addr : String

    init{
        this.addr = addr
    }

    var adapter : ListViewAdapter = ListViewAdapter()

//    val storesURL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json"
//    val salesURL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/sales/json"
    val JsonURL = "https://api.odcloud.kr/api/apnmOrg/v1/list?page=1&perPage=10&serviceKey=9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D"

    fun main(): ListViewAdapter{

        var text: String? = null
        try {
            text = URLEncoder.encode(addr, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val url = JsonURL
        val responseBody = get(url)
        parseData(responseBody)

        return adapter
    }

    private operator fun get(apiUrl: String): String {
        var responseBody: String = ""
        try {
            val url = URL(apiUrl)
            val `in` = url.openStream()
            responseBody = readBody(`in`)

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return responseBody
    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use({ lineReader ->
                val responseBody = StringBuilder()

                var line: String? = lineReader.readLine()
                while ( line != null) {
                    responseBody.append(line)
                    line = lineReader.readLine()
                }
                return responseBody.toString()
            })
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }

    }

    private fun parseData(responseBody: String) {
        var center: String
        var centeraddr : String
        var jsonObject = JSONObject()
        try {
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject.getJSONArray("data")
            total = jsonObject.getInt("totalCount")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                center = item.getString("orgnm")
                centeraddr = item.getString("orgZipaddr")
//                println("storeName : $center")
//                println("remain_stat : $centeraddr")
//                println(total)

                adapter.addItem(center, centeraddr)

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}