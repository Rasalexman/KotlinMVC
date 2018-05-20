# KotlinMVC
This is a vision on how android development can drive without fragment. It use ANKO Layout to generate UI.  
There are main blocks in this framework:
- MODEL (PROXY) - services and VO holders
- VIEW (MEDIATORS) - simple layout handler class
- CONTROLLER (COMMANDS) - business logic holder
- FACADE - unite all this 3 components in one core
- ANIMATIONS - Simple transitions class

it contains powerful event system, lifecycle handler, menu creations, save bundle

#Initialize core
```kotlin
val NAME = "default_core"
val appFacade:Facade = Facade.getInstance(NAME)
appFacade.registerProxy(UserProxy())
appFacade.registerMediator(UserAuthMediator())
appFacade.attachActivity(this).showLastOrExistMediator(UserAuthMediator.NAME, LinearAnimator())
```

See the example project and java doc to know more about this framework

Gradle:
```
compile 'com.rasalexman.kotlinmvc:kotlinmvc:1.0.2'
```

Maven:
```
<dependency>
  <groupId>com.rasalexman.kotlinmvc</groupId>
  <artifactId>kotlinmvc</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```
