package me.DuckyProgrammer.SignEditor;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignEditorCommand implements CommandExecutor, Listener {
    private static ArrayList<Player> editing = new ArrayList<Player>();
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("signeditor.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        if (args.length == 0) {
            Block lookingAt = player.getTargetBlockExact(5);
            if (lookingAt == null) {
                player.sendMessage(ChatColor.RED + "You are not looking at a sign.");
                return false;
            }
            if (!lookingAt.getType().toString().contains("SIGN")) {
                player.sendMessage(ChatColor.RED + "You are not looking at a sign.");
                return true;
            } else {
                editing.add(player);
                player.openSign((Sign) lookingAt.getState());
                return false;
            }
        }
        return false;
    }
    @EventHandler
    public void signEdit(SignChangeEvent event) {
        if (!editing.contains(event.getPlayer())) {
            return;
        }
        List<String> lines = new ArrayList<>();
        Collections.addAll(lines, event.getLines());
        for (int i = 0; i < lines.size(); i++) {
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', lines.get(i)));
        }
        editing.remove(event.getPlayer());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Sign edited successfully.");
    }
}