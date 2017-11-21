package com.mincor.puremvc_kotlin.framework.multicore.patterns.command

import com.mincor.puremvc_kotlin.framework.multicore.interfaces.ICommand
import com.mincor.puremvc_kotlin.framework.multicore.interfaces.INotification
import com.mincor.puremvc_kotlin.framework.multicore.patterns.observer.Notifier


/**
 * Created by a.minkin on 21.11.2017.
 */
open class MacroCommand : Notifier(), ICommand {

    private val subCommands: MutableList<ICommand> = mutableListOf()

    /**
     * Constructor.
     *
     * <P>
     * You should not need to define a constructor, instead, override the
     * `initializeMacroCommand` method.
    </P> *
     *
     * <P>
     * If your subclass does define a constructor, be sure to call
     * `super()`.
    </P> *
     */
    init {
        initializeMacroCommand()
    }

    /**
     * Initialize the `MacroCommand`.
     *
     * <P>
     * In your subclass, override this method to initialize the
     * `MacroCommand`'s *SubCommand* list with
     * `ICommand` class references like this:
    </P> *
     *
     * <listing> // Initialize MyMacroCommand override protected function
     * initializeMacroCommand( ) : void { addSubCommand(
     * com.me.myapp.controller.FirstCommand ); addSubCommand(
     * com.me.myapp.controller.SecondCommand ); addSubCommand(
     * com.me.myapp.controller.ThirdCommand ); } </listing>
     *
     * <P>
     * Note that *SubCommand*s may be any `ICommand`
     * implementor, `MacroCommand`s or `SimpleCommands`
     * are both acceptable.
    </P> */
    protected fun initializeMacroCommand() {}

    /**
     * Add a *SubCommand*.
     *
     * <P>
     * The *SubCommands* will be called in First In/First Out (FIFO)
     * order.
    </P> *
     *
     * @param commandClassRef
     * a reference to the `Class` of the
     * `ICommand`.
     */
    protected fun addSubCommand(commandClassRef: ICommand) {
        this.subCommands.add(commandClassRef)
    }

    /**
     * Execute this `MacroCommand`'s *SubCommands*.
     *
     * <P>
     * The *SubCommands* will be called in First In/First Out (FIFO)
     * order.
     *
     * @param notification
     * the `INotification` object to be passsed to each
     * *SubCommand*.
    </P> */
    override fun execute(notification: INotification) {
        for (command in subCommands) {
            command.initializeNotifier(multitonKey!!)
            command.execute(notification)
        }
    }
}