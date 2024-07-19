// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    dependencies{
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
}
