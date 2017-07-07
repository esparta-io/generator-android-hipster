package <%= appPackage %>.service.push

class PushExtras {
    companion object {
        val PUSH = "<%= appPackage %>.notifications.PUSH_MESSAGE" // same in AndroidManifest.xml for DefaultPushReceiver.
        val PUSH_TYPE = "type" // same in AndroidManifest.xml for DefaultPushReceiver.
        val BROADCAST_NOTIFICATION = "<%= appPackage %>.notifications.BROADCAST_NOTIFICATION" // same in AndroidManifest.xml for DefaultPushReceiver.
        val UNAUTHORIZED = "<%= appPackage %>.notifications.UNAUTHORIZED" // same in AndroidManifest.xml for DefaultPushReceiver.
    }
}
