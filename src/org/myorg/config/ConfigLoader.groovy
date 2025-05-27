package org.myorg.config

import groovy.yaml.YamlSlurper

class ConfigLoader {
    static Map load(String team) {
        def file = libraryResource("config/${team}-config.yaml")
        return new YamlSlurper().parseText(file)
    }
}
