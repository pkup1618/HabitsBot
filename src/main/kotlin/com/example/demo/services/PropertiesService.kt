package com.example.demo.services

import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*


@Service
class PropertiesService {
    private fun getPropsFromFile(propsDir: String): Properties {
        val properties = Properties()

        try {
            properties.load(FileInputStream(propsDir))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return properties
    }

    fun loadDatabaseProps(): Properties {
        return getPropsFromFile("db_props.properties")
    }

    fun loadTelegramBotProps(): Properties {
        return getPropsFromFile("telegram_bot_props.properties")
    }

    fun loadMessagesProps(): Properties {
        return getPropsFromFile("messages_props.properties")
    }
}
