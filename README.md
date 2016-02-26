#Gradle入门教程

#一、简介

Gradle是一种构建工具，它抛弃了基于XML的构建脚本，取而代之的是采用一种基于Groovy的内部领域特定语言。Gradle 通过提供可以随意集成的声明式语言元素将声明性构建推到了一个新的高度。你可以添加自己的语言元素或加强现有的语言元素。

#二、基本概念-Project、Task

在Gradle中，有两个基本概念：项目和任务。请看以下详解：

1. 项目是指我们的构建产物（比如Jar包）或实施产物（将应用程序部署到生产环境）。一个项目包含一个或多个任务。

2. 任务是指不可分的最小工作单元，执行构建工作（比如编译项目或执行测试）。

3. 每一次Gradle的构建都包含一个或多个项目。




4. Gradle本身的领域对象主要有Project和Task。Project为Task提供了执行上下文，所有的Plugin要么向Project中添加用于配置的Property，要么向Project中添加不同的Task。一个Task表示一个逻辑上较为独立的执行过程，比如编译Java源代码，拷贝文件，打包Jar文件，甚至可以是执行一个系统命令或者调用Ant。另外，一个Task可以读取和设置Project的Property以完成特定的操作。

#三、执行过程

Gradle并不会一开始便顺序执行build.gradle文件中的内容，而是分为两个阶段，第一个阶段是配置阶段，然后才是实际的执行阶段。在配置阶段，Gradle将读取所有build.gradle文件的所有内容来配置Project和Task等，比如设置Project和Task的Property，处理Task之间的依赖关系等 还有就是执行 plugin。这个地方要注意 要执行 的plugin 和 自定义 task 的顺序。

#四、配置文件



1. 我们能够使用以下配置文件对Gradle的构建进行配置：

Gradle构建脚本（build.gradle）指定了一个项目和它的任务。

Gradle属性文件（gradle.properties）用来配置构建属性。

Gradle设置文件（settings.gradle）对于只有一个项目的构建而言是可选的，如果我们的构建中包含多于一个项目，那么它就是必须的，因为它描述了哪一个项目参与构建。每一个多项目的构建都必须在项目结构的根目录中加入一个设置文件。

2. settings file 的查找过程

It looks in a directory called master which has the same nesting level as the current dir.

If not found yet, it searches parent directories.

If not found yet, the build is executed as a single project build.

If a settings.gradle file is found, Gradle checks if the current project is part of the multiproject hierarchy defined in the found settings.gradle file. If not, the build is executed as a single project build. Otherwise a multiproject build is executed.

3. 演示(见 Demo)：

gradle project 、gradle tasks

#五、自定义 Property(见 Demo)

1. gradle.properties 增加配置 

2. local.properties 配置

3. ext 的含义

#六、创建Task的多种方法

演示(见 Demo)

#七、Gradle Plugin

Gradle Plugin就是 Groovy 库，本质上就是 Groovy 代码

1. 在项目中添加新任务
2. 为新加入的任务提供默认配置，这个默认配置会在项目中注入新的约定（如源文件位置）。
3. 加入新的属性，可以覆盖插件的默认配置属性。
4. 为项目加入新的依赖。
#八、Android Plugin（见 Demo）

1. buildTypes

2. productFlavors

3. sourceSets

4. 与ios对比

target 相当于 gradle 里的 flavor

scheme 相当于 gradle 里 buildtype + flavor = buildVariant

5. andorid-apt

 原理：javaCompile 添加 compilerArgs

```java

android.applicationVariants.all { variant ->

    def aptOutputDir = project.file("build/source/apt")

    def aptOutput = new File(aptOutputDir, variant.dirName)

    println "****************************"

    println "variant: ${variant.name}"

    println "manifest:  ${variant.processResources.manifestFile}"

    println "aptOutput:  ${aptOutput}"

    println "****************************"

    android.sourceSets[getSourceSetName(variant)].java.srcDirs+= aptOutput.getPath()

    variant.javaCompile.options.compilerArgs += [

            '-processorpath', configurations.apt.getAsPath(),

            '-AandroidManifestFile=' + variant.processResources.manifestFile,

            '-s', aptOutput    ]

    variant.javaCompile.source = variant.javaCompile.source.filter { p ->

        return !p.getPath().startsWith(aptOutputDir.getPath())

    }

    variant.javaCompile.doFirst {

        aptOutput.mkdirs()

    }}

```

#九、依赖管理


1. maven库

mavenCentral()别名，表示依赖是从Central Maven 2 仓库中获取的。 
jcenter()别名，表示依赖是从Bintary’s JCenter Maven 仓库中获取的。 
jcenter > mavencenter

2. 引用方式
plugin 依赖和 library 依赖

本地引用 
Flat Directory

jar 的引用

@aar 的使用

exclude

transitive

3. 依赖分类
compile - this is the default scope, used if none is specified. Compile dependencies are available in all classpaths. Furthermore, those dependencies are propagated to dependent projects. 
provided - this is much like compile, but indicates you expect the JDK or a container to provide it at runtime. It is only available on the compilation and test classpath, and is not transitive. 
apt 
http://maven.apache.org/pom.html#Dependencies

4. 依赖名
最普遍的依赖称为外部依赖，这些依赖存放在外部仓库中。一个外部依赖可以由以下属性指定： 
group属性指定依赖的分组（在Maven中，就是groupId）。 
name属性指定依赖的名称（在Maven中，就是artifactId）。 
version属性指定外部依赖的版本（在Maven中，就是version）。 
我们假设我们需要指定以下依赖： 
依赖的分组是foo。 
依赖的名称是foo。 
依赖的版本是0.1。 
在项目编译时需要这些依赖。 
我们可以将以下代码片段加入到build.gradle中，进行依赖声明：

dependencies {
    compile group: 'foo', name: 'foo', version: '0.1'
}
5. 发布到 maven
https://gist.github.com/MasonLiuChn/98d849073750f3656cf4 
https://gist.github.com/MasonLiuChn/76fc85265e8bf138d70b




#十、自己写一个 Plugin


1. 创建 Task
2. 读取 Extension



#十一、相关链接：


https://docs.gradle.org/current/userguide/userguide.html

http://tools.android.com/tech-docs/new-build-system/user-guide 
http://google.github.io/android-gradle-dsl/current/index.html

http://www.apkbus.com/forum.php?mod=viewthread&tid=141929

https://dongchuan.gitbooks.io/gradle-user-guide-/content/index.html

http://blog.jobbole.com/71999/

http://blog.waynell.com/2015/04/03/gradle-use-01/

http://www.cnblogs.com/CloudTeng/p/3417762.html



