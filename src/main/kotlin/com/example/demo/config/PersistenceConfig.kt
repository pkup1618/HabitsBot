package com.example.demo.config

import com.example.demo.services.PropertiesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import javax.sql.DataSource


@Configuration
open class PersistenceConfig @Autowired constructor(
    private val propertiesService: PropertiesService
) {


}