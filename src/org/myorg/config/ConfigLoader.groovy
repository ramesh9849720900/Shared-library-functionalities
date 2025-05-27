package org.myorg.config

import org.yaml.snakeyaml.Yaml

class ConfigLoader {
    static Map load(String configName) {
        def yaml = new Yaml()
        def file = new File("config/${configName}.yaml")
        return yaml.load(file.text) as Map
    }
}
