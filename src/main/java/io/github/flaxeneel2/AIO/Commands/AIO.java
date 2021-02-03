package io.github.flaxeneel2.AIO.Commands;

import io.github.flaxeneel2.AIO.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class AIO implements CommandExecutor {
    public AIO(Main plugin) {
        plugin.getCommand("aio").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            Main.sendToPlayer((Player) commandSender, "placeholder");
            return true;
        }
        if(strings[0].equalsIgnoreCase("help")) {
            Main.sendToPlayer((Player) commandSender,"-=-=-=-=-=-=-=-=-=-=[AIO]-=-=-=-=-=-=-=-=-=-=");
            if(commandSender.hasPermission("aio.admin")) {
                Main.sendToPlayer((Player) commandSender, "you are an admin!");
            }
            Main.sendToPlayer((Player) commandSender, "placeholder");
            return true;
        }
        return false;
    }
}
