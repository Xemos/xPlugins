package xBank.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xBank.Core;

public class oldHelp implements CommandExecutor{
			
	
		public oldHelp(Core core) {
		}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args){
		Player player = (Player) sender;
		player.sendMessage(ChatColor.YELLOW + "The commands have now been updated to /bank");
		return true;
	}
	

}
