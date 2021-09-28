package com.mmtran.turtlesoccer.loaders

import android.content.res.Resources

import com.google.gson.Gson

import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.R

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.IOException
import java.util.*

class ResourceLoader(private val resources: Resources) {

    private var nationList: List<Nation>? = null

    val nations: List<Nation>?
        get() {
            val xmlFileInputStream = resources.openRawResource(R.raw.nations)
            val jsonString = readTextFile(xmlFileInputStream)
            val gson = Gson()
            val nations = gson.fromJson(jsonString, Array<Nation>::class.java)
            nationList = listOf(*nations)
            return nationList
        }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }
}