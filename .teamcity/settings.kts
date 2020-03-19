import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    vcsRoot(HttpsGithubComGopinathshivaAngularCliAppRefsHeadsMaster)

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_3"
            type = "project-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Build Duration (excluding Checkout Time)",
                    "sourceBuildTypeId": "AngularCliApp_Build",
                    "key": "BuildDurationNetTime"
                  }
                ]
            """.trimIndent())
            param("format", "text")
            param("hideFilters", "")
            param("title", "Build Duration")
            param("defaultFilters", "")
            param("seriesTitle", "Serie")
        }
    }
}

object Build : BuildType({
    name = "Build"

    artifactRules = "coverage/my-dream-app => coverage.zip"

    vcs {
        root(HttpsGithubComGopinathshivaAngularCliAppRefsHeadsMaster)
    }

    steps {
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

    triggers {
        vcs {
            enabled = false
        }
    }
})

object HttpsGithubComGopinathshivaAngularCliAppRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/gopinathshiva/angular-cli-app#refs/heads/master"
    url = "https://github.com/gopinathshiva/angular-cli-app"
})
