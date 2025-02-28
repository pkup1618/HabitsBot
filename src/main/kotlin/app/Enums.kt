package app

enum class BotCommands(val command: String) {
    START("/start"),
    HELP("/help"),
    MENU("/menu"),
    MY_HABITS("/my_habits"),
    ADD_HABIT("/add_habit"),
    REMOVE_HABIT("/remove_habit"),
    EDIT_HABIT("/edit_habit"),
    STATS_TODAY("/stats_today"),
    STATS_YESTERDAY("/stats_yesterday"),
    NOTIFICATIONS("/notifications"),
    NOTIFICATIONS_ENABLE("/notifications_enable"),
    NOTIFICATIONS_DISABLE("/notifications_disable"),
}

enum class UserState {
    UNNECESSARY,
    ADDING_HABIT_HEADER,
    ADDING_HABIT_BODY,
    ADDING_HABIT_NOTIFICATION_CRON,
    DELETING_HABIT
}
