package org.myorg.config
import org.yaml.snakeyaml.Yaml
class ConfigLoader {
    static Map load(String team) {
        def file = libraryResource("config/${team}-config.yaml")
        return new YamlSlurper().parseText(file)
    }
}
