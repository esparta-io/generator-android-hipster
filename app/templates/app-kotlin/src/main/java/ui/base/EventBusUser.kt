package <%= appPackage %>.ui.base

import org.greenrobot.eventbus.EventBus

/**
 * Created by gmribas on 29/11/16.
 */
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