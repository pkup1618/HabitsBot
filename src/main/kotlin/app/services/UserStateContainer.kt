package app.services

import app.UserState

class UserStateContainer {
    var userState: UserState = UserState.UNNECESSARY

    var habitName: String? = null
    var habitDescription: String? = null
    var notifictaionCron: String? = null

    fun changeState(userState: UserState) {
        this.userState = userState
    }
}