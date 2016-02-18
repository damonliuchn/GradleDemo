package net.masonliu.helloplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

public class SayHelloTask extends DefaultTask {

    static final String NAME = "SayHelloTask"

    public static void create(Project project) {

        def publishTask = project.tasks.create(name: NAME) << {
            println 'SayHelloTask1'
        }

//        def publishTask = project.task(NAME) {
//
//        }

        publishTask.doFirst {
            println 'SayHelloTask-doFirst'
        }
    }

//    @TaskAction
//    void run() {
//        println 'SayHelloTask3'
//    }
}