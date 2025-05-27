package org.myorg.config

import org.yaml.snakeyaml.Yaml

class ConfigLoader {
    static Map load(String configName) {
        def yaml = new Yaml()
        def text = libraryResource("config/${configName}.yaml")
        return yaml.load(text) as Map
    }
}
