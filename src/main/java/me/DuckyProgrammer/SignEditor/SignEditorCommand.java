package me.DuckyProgrammer.SignEditor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

public class SignEditorCommand implements CommandExecutor {
    private final Main plugin = Main.getPlugin(Main.class);
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }
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
                openCreation(player, lookingAt);
                return false;
            }
        }
        return false;
    }
    public void openCreation(Player player, Block sign) {
        String[] lines = ((org.bukkit.block.Sign) sign.getState()).getLines();
        SignGUIAPI.builder()
                .action(event -> processSign(player, event.getLines(), sign))
                .withLines(List.of(lines))
                .uuid(player.getUniqueId())
                .plugin(plugin)
                .build()
                .open();
    }
    void processSign(Player player, List<String> lines, Block lookingAt){
        Sign sign = (Sign) lookingAt.getState();
        for (int i = 0; i < lines.size(); i++) {
            sign.setLine(i, lines.get(i));
        }
        sign.update();
        player.sendMessage(ChatColor.GREEN + "Sign edited successfully.");
    }
}