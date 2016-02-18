package net.masonliu.helloplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project

public class SayHelloTask extends DefaultTask {

    static final String NAME = "SayHelloTask"

    public static void create(Project project) {
        project.extensions.create(SayHelloExtension.NAME, SayHelloExtension)

        def sayHelloExt = project.extensions.findByName(SayHelloExtension.NAME) as SayHelloExtension


        def publishTask = project.tasks.create(name: NAME) << {
            println 'SayHelloTask1'+ sayHelloExt.message
        }

        publishTask.doFirst {
            println 'SayHelloTask-doFirst'
        }
    }

}