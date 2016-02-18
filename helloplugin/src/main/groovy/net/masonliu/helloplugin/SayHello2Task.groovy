package net.masonliu.helloplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class SayHello2Task extends DefaultTask {

    @TaskAction
    void run() {
        println 'SayHello2Task'
    }
}