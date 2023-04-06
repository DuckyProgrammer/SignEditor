package me.DuckyProgrammer.SignEditor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignColoring implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getPlayer().hasPermission(Main.colorPermission)) {
            for (int i = 0; i < event.getLines().length; i++) {
                event.setLine(i, org.bukkit.ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
            }
        }
    }
}
