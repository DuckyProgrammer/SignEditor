package me.DuckyProgrammer.SignEditor;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateNotifier implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            if (Main.updateAvailable) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bA new version of Sign Editor is available!"));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bDownload it at &a&nhttps://www.spigotmc.org/resources/sign-editor.109052/"));
            }
        }
    }
}
