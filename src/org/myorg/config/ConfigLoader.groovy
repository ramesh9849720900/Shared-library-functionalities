package org.myorg.config

import org.yaml.snakeyaml.Yaml

class ConfigLoader implements Serializable {
    def steps

    ConfigLoader(steps) {
        this.steps = steps
    }

    def load(String filePath) {
        def yamlText = steps.libraryResource(filePath)
        def yaml = new Yaml()
        return yaml.load(yamlText)
    }
}
