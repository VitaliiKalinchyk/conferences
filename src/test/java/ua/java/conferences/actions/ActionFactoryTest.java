package ua.java.conferences.actions;

import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.controller.actions.ActionFactory;
import ua.java.conferences.controller.actions.implementation.base.DefaultAction;
import ua.java.conferences.controller.actions.implementation.base.SignInAction;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.controller.actions.ActionFactory.getActionFactory;
import static ua.java.conferences.controller.actions.constants.ActionNames.SIGN_IN_ACTION;

class ActionFactoryTest {

    @Test
    void testCreateAction() {
        ActionFactory actionFactory = getActionFactory();
        Action action = actionFactory.createAction(SIGN_IN_ACTION);
        assertInstanceOf(SignInAction.class, action);
    }

    @Test
    void testDefaultAction() {
        ActionFactory actionFactory = getActionFactory();
        Action action = actionFactory.createAction("wrongName");
        assertInstanceOf(DefaultAction.class, action);
    }
}