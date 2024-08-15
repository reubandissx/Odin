@file:Suppress("UNUSED")

package me.odinmain.features.impl.render

import com.github.stivais.ui.constraints.percent
import com.github.stivais.ui.constraints.px
import me.odinmain.features.Module
import me.odinmain.utils.ServerUtils
import me.odinmain.utils.skyblock.modMessage
import me.odinmain.utils.ui.and
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent

object ServerHud : Module(
    name = "Server Hud",
    description = "Displays your current ping, FPS and server's TPS."
) {
    init {
        onPacket { packet: C06PacketPlayerPosLook ->
            modMessage("pos y: ${packet.isMoving}")
        }
        onEvent<ClientTickEvent> {
            modMessage("Tick ran")
        }
    }
    private val fps by HUD(2.percent, 2.percent) {
        text("FPS ", color = ClickGUI.color, size = 30.px) and text({ ServerUtils.fps })
    }.setting("FPS HUD", "HUD, which displays your frames per second.")

    private val ping by HUD(8.percent, 2.percent) {
        text("Ping ", color = ClickGUI.color, size = 30.px) and text({ ServerUtils.averagePing.toInt() })
    }.setting("Ping HUD", "HUD, which displays your ping.")

    private val tps by HUD(14.percent, 2.percent) {
        text("TPS ", color = ClickGUI.color, size = 30.px) and text({ ServerUtils.averageTps.toInt() })
    }.setting("TPS HUD", "HUD, which displays the server's TPS.")
}