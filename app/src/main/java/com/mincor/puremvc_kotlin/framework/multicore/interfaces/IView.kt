package com.mincor.puremvc_kotlin.framework.multicore.interfaces

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IView {

    /**
     * Register an `IObserver` to be notified of
     * `INotifications` with a given name.
     *
     * @param noteName
     * the name of the `INotifications` to notify this
     * `IObserver` of
     * @param observer
     * the `IObserver` to register
     */
    fun registerObserver(noteName: String, observer: IObserver)

    /**
     * Notify the `IObservers` for a particular
     * `INotification`.
     *
     * <P>
     * All previously attached `IObservers` for this
     * `INotification`'s list are notified and are passed a
     * reference to the `INotification` in the order in which they
     * were registered.
    </P> *
     *
     * @param note
     * the `INotification` to notify
     * `IObservers` of.
     */
    fun notifyObservers(note: INotification)

    /**
     * Register an `IMediator` instance with the `View`.
     *
     * <P>
     * Registers the `IMediator` so that it can be retrieved by
     * name, and further interrogates the `IMediator` for its
     * `INotification` interests.
    </P> *
     * <P>
     * If the `IMediator` returns any `INotification`
     * names to be notified about, an `Observer` is created
     * encapsulating the `IMediator` instance's
     * `handleNotification` method and registering it as an
     * `Observer` for all `INotifications` the
     * `IMediator` is interested in.
    </P> *
     *
     * @param mediator
     * a reference to the `IMediator` instance
     */
    fun registerMediator(mediator: IMediator)

    /**
     * Retrieve an `IMediator` from the `View`.
     *
     * @param mediatorName
     * the name of the `IMediator` instance to retrieve.
     * @return the `IMediator` instance previously registered with
     * the given `mediatorName`.
     */
    fun retrieveMediator(mediatorName: String): IMediator

    /**
     * Remove an `IMediator` from the `View`.
     *
     * @param mediatorName
     * name of the `IMediator` instance to be removed.
     */
    fun removeMediator(mediatorName: String): IMediator?

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName
     * @return whether a Mediator is registered with the given `mediatorName`.
     */
    fun hasMediator(mediatorName: String): Boolean
}