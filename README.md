# PureMVC-Kotlin
This is a vision on how android development can drive
There are 4 main blocks in this framework:
- VIEW (MEDIATORS) - simple layout handler class
- CONTROLLER (COMMANDS) - business logic holder
- MODEL (PROXY) - services and VO holders
- FACADE - unite all this 3 components in one core

#Initialize core
```kotlin
val NAME = "default_core"
val appFacade:Facade = Facade.getInstance(NAME)
appFacade.registerProxy(UserProxy())
appFacade.registerMediator(UserAuthMediator())
appFacade.attachActivity(this).showLastOrExistMediator(UserAuthMediator.NAME, LinearAnimator())
```

See the example project and java doc to know more about this framework
