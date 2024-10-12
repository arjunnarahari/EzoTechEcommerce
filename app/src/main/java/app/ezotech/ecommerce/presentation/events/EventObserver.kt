package app.ezotech.ecommerce.presentation.events

import androidx.lifecycle.Observer
import org.jetbrains.annotations.NotNull

class EventObserver<T>(@NotNull onEventUnhandledContent: EventHandler<T>) :
    Observer<Event<T>?> {
    private val onEventUnhandledContent: EventHandler<T>
    override fun onChanged(value: Event<T>?) {
        if (value != null) {
            value.getContentIfNotHandled()?.let { onEventUnhandledContent.onEventUnHandled(it) }
        }
    }

    init {
        this.onEventUnhandledContent = onEventUnhandledContent
    }
}