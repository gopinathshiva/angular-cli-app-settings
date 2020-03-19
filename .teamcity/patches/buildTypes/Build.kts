package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    expectSteps {
        script {
            name = "Install Dependencies"
            scriptContent = "npm install"
        }
        script {
            name = "Run Build"
            scriptContent = "npm run build"
        }
        script {
            name = "Run Test"
            scriptContent = """
                npm run test-phantomjs
                npm run test-chrome
            """.trimIndent()
        }
        script {
            name = "Run Lint"
            scriptContent = "npm run lint"
        }
    }
    steps {
        update<ScriptBuildStep>(1) {
            name = "Run Lint"
            scriptContent = "npm run lint"
        }
        update<ScriptBuildStep>(3) {
            name = "Run Build"
            scriptContent = "npm run build"
        }
        insert(4) {
            script {
                name = "Deploy"
                scriptContent = "cp -rf %system.teamcity.build.workingDir%/dist/my-dream-app/ /Users/gosivasa/appdy/teamcity-study/my-dream-app/deploy/"
            }
        }
    }

    triggers {
        val trigger1 = find<VcsTrigger> {
            vcs {
                enabled = false
            }
        }
        trigger1.apply {
            enabled = true
        }
    }
}
