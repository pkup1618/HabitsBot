package app

data class ChatMember(
    val id: Long
)

data class Habit(
    val id: Long,
    val chatMemberId: Long,
    val name: String,
    val description: String,
    val notifictaionCron: String? = null
) {
    override fun toString(): String {
        return """
            Название: $name
            Описание: $description
        """
    }
}