package me.plynk.yellowphrases.di

import javax.inject.Qualifier
import javax.inject.Scope

@Qualifier
@Retention annotation class ActivityContext

@Qualifier
@Retention annotation class ApplicationContext

@Scope
@Retention annotation class ConfigPersistent

@Scope
@Retention annotation class PerActivity

@Scope
@Retention annotation class PerFragment