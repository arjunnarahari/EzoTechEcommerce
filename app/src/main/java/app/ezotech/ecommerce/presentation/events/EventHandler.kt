package app.ezotech.ecommerce.presentation.events

interface EventHandler<V> {
    fun onEventUnHandled(`object`: V)
}