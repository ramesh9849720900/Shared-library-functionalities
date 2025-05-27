package org.myorg.config

import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths

class ConfigLoader {
    static Map loadConfig(String filePath) {
        def yaml = new Yaml()
        def file = new File(filePath)
        return yaml.load(file.text) as Map
    }
}
