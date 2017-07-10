package <%= appPackage %>.di

interface HasComponent<C> {
    fun getComponent() : C?
}
