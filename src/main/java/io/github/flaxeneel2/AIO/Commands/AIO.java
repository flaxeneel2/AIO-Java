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
            Main.inst.sendToPlayer((Player) commandSender, "placeholder");
            return true;
        }
        if(strings[0].equalsIgnoreCase("help")) {
            Main.inst.sendToPlayer((Player) commandSender,"-=-=-=-=-=-=-=-=-=-=[AIO]-=-=-=-=-=-=-=-=-=-=");
            if(commandSender.hasPermission("aio.admin")) {
                Main.inst.sendToPlayer((Player) commandSender, "/setbal <player> <amount>: sets a player's balance");
            }
            Main.inst.sendToPlayer((Player) commandSender, "/bal [player]: shows a player's balance (or yours if player not specified)");
            return true;
        }
        return false;
    }
}
