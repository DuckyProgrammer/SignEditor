package me.DuckyProgrammer.SignEditor;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    @Override
    public void onEnable() {




        getCommand("editsign").setExecutor(new SignEditorCommand());
        getLogger().info("SignEditor has been enabled!");
    }
}
