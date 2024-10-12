package app.ezotech.ecommerce.presentation.events


class Event<T>() {
    private var mContent: T? = null
    private var hasBeenHandled = false

    constructor(content: T) : this() {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }
    }

    fun peekContent(): T {
        return mContent!!
    }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }
}