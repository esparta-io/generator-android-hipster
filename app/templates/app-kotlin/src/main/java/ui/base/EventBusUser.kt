package <%= appPackage %>.ui.base

import org.greenrobot.eventbus.EventBus

interface EventBusUser {

    var busRegistered: Boolean

    fun registerBus(eventBus: EventBus) {
        if (busRegistered) return

        eventBus.register(this)
        busRegistered = true
    }

    fun unRegisterBus(eventBus: EventBus) {
        eventBus.unregister(this)
        busRegistered = false
    }
}