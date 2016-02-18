package net.masonliu.helloplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project> {

    void apply(Project project) {
        SayHelloTask.create(project)
        project.task("SayHello2Task", type: SayHello2Task)
    }

}