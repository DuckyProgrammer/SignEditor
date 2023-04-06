package me.DuckyProgrammer.SignEditor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends JavaPlugin {

    public static String noPermission;
    public static String permission;
    public static String signEdited;
    public static String noSign;
    public static boolean updateAvailable;
    public static String colorPermission;
    @Override
    public void onEnable() {
        getCommand("editsign").setExecutor(new SignEditorCommand());
        getServer().getPluginManager().registerEvents(new SignEditorCommand(), this);
        getServer().getPluginManager().registerEvents(new UpdateNotifier(), this);
        getServer().getPluginManager().registerEvents(new SignColoring(), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        updateAvailable = checkUpdate();
        noPermission = ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission-message"));
        permission = ChatColor.translateAlternateColorCodes('&', getConfig().getString("permission"));
        signEdited = ChatColor.translateAlternateColorCodes('&', getConfig().getString("sign-edited-message"));
        noSign = ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-sign-detected-message"));
        colorPermission = ChatColor.translateAlternateColorCodes('&', getConfig().getString("color-sign-permission"));
        if (updateAvailable) {
            getLogger().warning("A new version of SignEditor is available!");
            getLogger().warning("Download it at https://www.spigotmc.org/resources/sign-editor.109052/");
            for (Player ops : Bukkit.getOnlinePlayers()) {
                if (ops.isOp()) {
                    ops.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bA new version of Sign Editor is available!"));
                    ops.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bDownload it at &a&nhttps://www.spigotmc.org/resources/sign-editor.109052/"));
                }
            }
        }
        getLogger().info("SignEditor has been enabled!");
    }

    public boolean checkUpdate() {
        try {
            URL url = new URL("https://api.spiget.org/v2/resources/109052/versions/latest");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONObject obj = (JSONObject) JSONValue.parse(content.toString());
            String version = (String) obj.get("name");
            if (version.equals(getDescription().getVersion())) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
