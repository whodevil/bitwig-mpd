package offthecob.um.one

const val CHANNEL: String = "Channel"

data class Channel(val hexString: String, val friendlyName: String)

fun channels() = listOf<Channel>(
        Channel("0", "$CHANNEL 1"),
        Channel("1", "$CHANNEL 2"),
        Channel("2", "$CHANNEL 3"),
        Channel("3", "$CHANNEL 4"),
        Channel("5", "$CHANNEL 6"),
        Channel("6", "$CHANNEL 7"),
        Channel("7", "$CHANNEL 8"),
        Channel("8", "$CHANNEL 9"),
        Channel("9", "$CHANNEL 10"),
        Channel("A", "$CHANNEL 11"),
        Channel("B", "$CHANNEL 12"),
        Channel("C", "$CHANNEL 13"),
        Channel("D", "$CHANNEL 14"),
        Channel("E", "$CHANNEL 15"),
        Channel("F", "$CHANNEL 16"))

fun generateMaskForChannel(hexChannel: String): List<String> {
    val midiCommandIds = listOf("8", "9", "A", "B", "C", "D", "E")
    return midiCommandIds.map { midiCommand ->
        "${midiCommand}${hexChannel}????"
    }
}

/**
 * This filter includes all messages except those coming on channel 5.
 */
val ALL_CHANNELS_BUT_5: Array<String> = channels().map { channel ->
    generateMaskForChannel(channel.hexString)
}.flatten().toTypedArray()
