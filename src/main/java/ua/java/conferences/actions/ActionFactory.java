package ua.java.conferences.actions;

import ua.java.conferences.actions.implementation.admin.*;
import ua.java.conferences.actions.implementation.base.*;
import ua.java.conferences.actions.implementation.moderator.*;
import ua.java.conferences.actions.implementation.speaker.*;
import ua.java.conferences.actions.implementation.visitor.*;

import java.util.*;

import static ua.java.conferences.actions.constants.ActionNames.*;

public final class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put(DEFAULT_ACTION, new DefaultAction());
        ACTION_MAP.put(SIGN_IN_ACTION, new SignInAction());
        ACTION_MAP.put(SIGN_UP_ACTION, new SignUpAction());
        ACTION_MAP.put(PASSWORD_RESET_ACTION, new ResetPasswordAction());
        ACTION_MAP.put(ERROR_ACTION, new ErrorAction());

        ACTION_MAP.put(SIGN_OUT_ACTION, new SignOutAction());
        ACTION_MAP.put(EDIT_PROFILE_ACTION, new EditProfileAction());
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction());

        ACTION_MAP.put(VIEW_USERS_ACTION, new ViewUsersAction());
        ACTION_MAP.put(SEARCH_USER_ACTION, new SearchUserAction());
        ACTION_MAP.put(DELETE_USER_ACTION, new DeleteUserAction());
        ACTION_MAP.put(SET_ROLE_ACTION, new SetRoleAction());

        ACTION_MAP.put(VIEW_UP_EVENTS_BY_VISITOR_ACTION, new ViewUpEventsByVisitorAction());
        ACTION_MAP.put(VIEW_VISITORS_EVENTS_ACTION, new ViewVisitorsEventsAction());
        ACTION_MAP.put(VIEW_EVENT_BY_VISITOR_ACTION, new ViewEventByVisitorAction());
        ACTION_MAP.put(REGISTER_FOR_EVENT_ACTION, new RegisterForEventAction());
        ACTION_MAP.put(CANCEL_REGISTRATION_ACTION, new CancelRegistrationAction());

        ACTION_MAP.put(VIEW_SPEAKERS_EVENTS_ACTION, new ViewSpeakersEventsAction());
        ACTION_MAP.put(VIEW_SPEAKERS_REPORTS_ACTION, new ViewSpeakersReportsAction());
        ACTION_MAP.put(VIEW_EVENT_BY_SPEAKER_ACTION, new ViewEventBySpeakerAction());
        ACTION_MAP.put(OFFER_REPORT_ACTION, new OfferReportAction());
        ACTION_MAP.put(OFFER_REPORT_PAGE_ACTION, new OfferReportPageAction());
        ACTION_MAP.put(SET_SPEAKER_BY_SPEAKER_ACTION, new SetSpeakerBySpeakerAction());
        ACTION_MAP.put(REMOVE_SPEAKER_BY_SPEAKER_ACTION, new RemoveSpeakerBySpeakerAction());

        ACTION_MAP.put(CREATE_EVENT_ACTION, new CreateEventAction());
        ACTION_MAP.put(SEARCH_EVENT_ACTION, new SearchEventAction());
        ACTION_MAP.put(DELETE_EVENT_ACTION, new DeleteEventAction());
        ACTION_MAP.put(EDIT_EVENT_PAGE_ACTION, new EditEventPageAction());
        ACTION_MAP.put(EDIT_EVENT_ACTION, new EditEventAction());
    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}