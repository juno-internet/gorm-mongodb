/*
* Copyright 2004-2005 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

/**
 * Build the jar for ast transformations, to be packaged with the plugin
 *
 * @author Juri Kuehn
 *
 * @since 0.7.7
 */

includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCompile")


target ('default': "Builds the ast jar to be bundled with the plugin") {
    def mongoAstSrcDir = new File("${mongodbMorphiaPluginDir}/src/ast/grails/plugins/mongodb/ast")
    def mongoAstBuildDir = new File(grailsSettings.pluginBuildClassesDir, "ast")
    def mongoAstDestDir = new File(grailsSettings.baseDir, "lib")

    // create work dir
    ant.mkdir(dir:mongoAstBuildDir)

    // compile ast classes
    ant.groovyc(destdir: mongoAstBuildDir, encoding: "UTF-8") {
        src(path: mongoAstSrcDir)
    }

    // add service descriptor
    ant.copy(todir:new File(mongoAstBuildDir, 'META-INF')) {
        fileset dir:"${mongoAstSrcDir}/META-INF"
    }

    // package jar
    ant.jar(destfile: new File(mongoAstDestDir, 'mongodb-morphia-ast.jar'), basedir: mongoAstBuildDir)

    // cleanup
    ant.delete(dir:mongoAstBuildDir)
}
