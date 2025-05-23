package org.myorg.config

import org.yaml.snakeyaml.Yaml

class ConfigLoader {
    static Map load(String team) {
        def yamlContent = libraryResource("config/${team}-config.yaml")
        return new Yaml().load(yamlContent)
    }
}
