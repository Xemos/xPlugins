package xBank.commands;

import java.util.List;
import xBank.Core;
//import xBank.conversations.Teller;  TODO

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.iConomy.system.Holdings;
import com.sk89q.worldedit.Vector;



public class bankCommand implements CommandExecutor {
	ChatColor ec = ChatColor.RED;
	ChatColor m1c = ChatColor.YELLOW;
	ChatColor m2c = ChatColor.GOLD;
	ChatColor m3c = ChatColor.WHITE;
	private FileConfiguration config = null;
	private Core plugin;

	public bankCommand(Core core) {
		this.plugin = core;
		//this.factory = new ConversationFactory(this); TODO
	}
	
	
	
	// commands
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		
		
				
		this.config = plugin.getConfig();
		
		if ((sender instanceof ConsoleCommandSender)) {
			// interest is charged by entering this command into the console.
			// this is done to save server resources.
			if ((args[0].equalsIgnoreCase("interest"))) {
				addInterest();
				sender.sendMessage("Interest added");
				return true;
			}
			
		}
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			sendHelp(player);
			return true;
		}
		
		String CB = config.getString("Location." + currentBankRegion(player));
	//	boolean member = true; TODO
		
		switch (args[0].toLowerCase()) {
		case "teller":
		//	if(CB != getBankAccount(player)){
		//		member = false;
		//	}
		//	this.factory.withFirstPrompt(new Teller(player.getName(), getBankAccount(player),  member)).buildConversation(player).begin();
			return true;
		case "members":		

			if (isInBank(player)){
				listMembers(player,CB);
			}else{
				sendHelp(player);
			}
			return true;
		case "list":
			listBanks(player);
			return true;
		case "leave": {
			if (isInBank(player)) {
				if (isInOwnBankRegion(player)) {
					leaveBank(player);
				} else {
					player.sendMessage(ChatColor.YELLOW
							+ "You must be in your bank to use this command");
				}
			} else {
				player.sendMessage(ChatColor.YELLOW + "You are not in a bank");
			}
			return true;
		}
		case "info": {
			if (args.length == 2) {
					bankInfo(player, args[1]);
				 
			} else if (CB != null) {
				bankInfo(player,  CB);
			} 
			else if (isInBank(player)) {
				player.sendMessage(ChatColor.YELLOW	+ "You are a member of "
						+ config.getString("Players." + player.getName() + ".Bank"));
			} else {
				player.sendMessage(ChatColor.YELLOW	+ "You must be a member of a bank, or standing in one to use this command.");
			}
			return true;
		}
		case "join": {
			if (isInBank(player)) {
				player.sendMessage(ChatColor.YELLOW
						+ "You are already a member of "
						+ config.getString("Players." + player.getName()
								+ ".Bank"));
			} else if (CB != null) {

				joinBank(player, CB);
			} else {
				player.sendMessage(ChatColor.YELLOW
						+ "You must be in a bank to use this command");
			}

			return true;
		}
		case "balance": {
			if (isInBank(player)) {
					getBalance(player);
			} else {
				player.sendMessage(ChatColor.YELLOW + "You are not a member of a bank");
			}
			return true;
		}
		case "withdraw": {
			if (args.length == 2) {
				if (isInBank(player)) {
					if (isInOwnBankRegion(player)) {
						withdraw(player, Double.parseDouble(args[1]));
					} else {
						player.sendMessage(ChatColor.YELLOW
								+ "You must be at your bank to use this command");
					}
				} else {
					player.sendMessage(ChatColor.YELLOW
							+ "You are not in a bank");
				}
			} else {
				sendHelp(player);
			}
			return true;
		}
		case "deposit": {
			if (args.length == 2) {
				if (isInBank(player)) {
					if (isInOwnBankRegion(player)) {
						deposit(player, Double.parseDouble(args[1]));
					} else {
						player.sendMessage(ChatColor.YELLOW
								+ "You must be at your bank to use this command");
					}
				} else {
					player.sendMessage(ChatColor.YELLOW
							+ "You are not in a bank");
				}
			} else {
				sendHelp(player);
			}
			return true;
		}
		
		default:
			sendHelp(player);
			return true;
		}
		
	}

	// Method to add interest to all bank accounts
	@SuppressWarnings("static-access")
	private void addInterest() {

		List<String> banks = config.getStringList("Banks.List");
		for (String bank : banks) {

			double interest = config.getDouble("Banks." + bank + ".Interest");
			List<String> acts = config.getStringList("Banks." + bank
					+ ".MemberAccounts");
			if (acts != null) {
				for (String act : acts) {

					if (plugin.iConomy.hasAccount(act)) {
						plugin.iConomy.getAccount(act).getHoldings()
								.multiply(1 + (interest / 100));
					}

				}
			}
		}

	}

	// gets bank info for a specific player when they use the /bank info command
	private void bankInfo(Player player, String ID) {
		if (config.contains("Banks." + ID)) {
			String name = config.getString("Banks." + ID + ".Name");
			double interest = config.getDouble("Banks." + ID + ".Interest");
			String owner = config.getString("Banks." + ID + ".Owner");
			double joinfee = config.getDouble("Banks." + ID + ".JoinFee");
			double transfee = config.getDouble("Banks." + ID + ".Fee");
			player.sendMessage(this.m2c + "--------------------[ " + this.m1c
					+ "Bank - Info" + this.m2c + " ]-------------------");
			player.sendMessage(this.m2c + name + this.m1c + " ID: " + this.m3c
					+ ID);
			player.sendMessage(this.m1c + "Owner" + this.m2c + " | " + this.m3c
					+ owner);
			player.sendMessage(this.m1c + "Interest" + this.m2c + " | "
					+ this.m3c + interest + "%");
			player.sendMessage(this.m1c + "Join Fee" + this.m2c + " | "
					+ this.m3c + joinfee);
			player.sendMessage(this.m1c + "Transaction Fee" + this.m2c + " | "
					+ this.m3c + transfee);

		} else {
			player.sendMessage(ChatColor.YELLOW + "This bank does not exist");
		}

	}

	// deposit money from a user's money to their bank account
	private void deposit(Player player, double amount) {
		Holdings bank = getBankHoldings(player);
		Holdings balance = getHoldings(player.getName());
		double fee = getBankFee(player);
		if (balance.hasEnough(amount)) {
			bank.add(amount - fee);
			payBank(player, fee);
			balance.subtract(amount);
			player.sendMessage(ChatColor.YELLOW + "Deposited " + amount
					+ " cn into your bank account");
		} else {
			player.sendMessage(ChatColor.YELLOW
					+ "You do not have sufficient funds");
		}

	}

	// get a player's bank account balance
	public void getBalance(Player player) {
		String ID = getBankID(player);
		Holdings balance = getHoldings(ID + "-" + player.getName());
		double amount = balance.balance();
		player.sendMessage(this.m2c + "------------------[ " + this.m1c
				+ "Bank - Balance" + this.m2c + " ]------------------");
		player.sendMessage(this.m1c + "Balance with " + getBankName(ID) + ": "
				+ this.m3c + amount);

	}

	// get their bank account name from the config...again kinda
	// redundant...schema BankID-Playername eg TCU-firetire100...the dash is
	// needed because it uses a standard iconomy account, so it wont be affected
	// by a player with the same name joining because you cant have a - in your
	// user name
	private String getBankAccount(Player player) {
		String accountname = config.getString("Players." + player.getName()
				+ ".Account");
		return accountname;
	}

	// gets the bank transaction fee
	private double getBankFee(Player player) {
		String ID = getBankID(player);
		double fee = config.getDouble("Banks." + ID + ".Fee");
		return fee;
	}

	// gets the player's bank account iconomy holdings
	@SuppressWarnings("static-access")
	public Holdings getBankHoldings(Player player) {
		String accountname = getBankAccount(player);
		Holdings balance = plugin.iConomy.getAccount(accountname).getHoldings();
		return balance;
	}

	// get's the player's bank id from the config
	private String getBankID(Player player) {
		String ID = config.getString("Players." + player.getName() + ".Bank");
		return ID;
	}

	// get's the bank's name using the bank id
	private String getBankName(String s) {
		String name = config.getString("Banks." + s + ".Name");
		return name;
	}

	// shorter method to get an iconomy holdings
	@SuppressWarnings("static-access")
	private Holdings getHoldings(String name) {

		Holdings balance = plugin.iConomy.getAccount(name).getHoldings();
		return balance;
	}

	// code to automate the bank leveling up...doesnt work though the issue isnt
	// in this method

	// check if a user is in a bank
	private boolean isInBank(Player player) {
		if (config.contains("Players." + player.getName())) {
			if (!(config.getString("Players." + player.getName() + ".Bank") == null)) {
				return true;
			}
		}
		return false;
	}

	// check to see if a player is in the specified bank's wg region
	private boolean isInBankRegion(Player player, String ID) {
		String regID = config.getString("Banks." + ID + ".Region");

		World world = plugin.getServer().getWorld("world");
		List<String> regs = plugin.wg.getRegionManager(world)
				.getApplicableRegionsIDs(
						new Vector(player.getLocation().getX(), player
								.getLocation().getY(), player.getLocation()
								.getZ()));
		for (String id : regs) {
			if (id.equalsIgnoreCase(regID)) {
				return true;
			}
		}

		return false;
	}
	
	private String currentBankRegion(Player player) {
		World world = plugin.getServer().getWorld("world");

		List<String> banks = config.getStringList("Region");
		List<String> regs = plugin.wg.getRegionManager(world)
				.getApplicableRegionsIDs(
						new Vector(player.getLocation().getX(), player
								.getLocation().getY(), player.getLocation()
								.getZ()));

		for (int x = 0; x < banks.size(); x++) {
			for (String id : regs) {

				if (id.equalsIgnoreCase(banks.get(x))) {

					return id;
				}
			}
		}

		return null;
	}

	// check to see if a player is in their bank's wg region
	private boolean isInOwnBankRegion(Player player) {
		String ID = config.getString("Players." + player.getName() + ".Bank");
		if (isInBankRegion(player, ID)) {
			return true;
		}
		return false;
	}

	// code to join a bank
	@SuppressWarnings("static-access")
	private void joinBank(Player player, String ID) {
			
		
		if (ID != null) {

			double joinfee = config.getDouble("Banks." + ID + ".JoinFee");
			Holdings balance = plugin.iConomy.getAccount(player.getName())
					.getHoldings();
			if (balance.hasEnough(joinfee)) {
				balance.subtract(joinfee);
				payBankOwner(ID, joinfee);
				List<String> members = config.getStringList("Banks." + ID
						+ ".Members");
				members.add(player.getName());
				config.set("Banks." + ID + ".Members", members);
				config.set("Players." + player.getName() + ".Bank", ID);
				String accountname = ID + "-" + player.getName();
				if (!plugin.iConomy.Accounts.exists(accountname)) {
					plugin.iConomy.Accounts.create(accountname);
				}
				config.set("Players." + player.getName() + ".Account",
						accountname);
				List<String> memberaccounts = config.getStringList("Banks."
						+ ID + ".MemberAccounts");
				memberaccounts.add(accountname);
				config.set("Banks." + ID + ".MemberAccounts", memberaccounts);
				plugin.saveConfig();
				player.sendMessage(ChatColor.YELLOW + "Welcome to " + ID + ", " + player.getName() + "!");
			}

		} else {
			player.sendMessage(ChatColor.YELLOW + "You are not standing in a registered bank.");
		}

	}

	// code to leave a bank
	@SuppressWarnings("static-access")
	private void leaveBank(Player player) {

		String ID = config.getString("Players." + player.getName() + ".Bank");

		List<String> members = config.getStringList("Banks." + ID + ".Members");
		members.remove(player.getName());
		config.set("Banks." + ID + ".Members", members);
		config.set("Players." + player.getName() + ".Bank", null);
		String accountname = config.getString("Players." + player.getName()
				+ ".Account");
		if (!plugin.iConomy.Accounts.exists(accountname)) {
			plugin.iConomy.Accounts.remove(accountname);
		}
		config.set("Players." + player.getName() + ".Account", null);
		List<String> memberaccounts = config.getStringList("Banks." + ID
				+ ".MemberAccounts");
		memberaccounts.remove(accountname);
		config.set("Banks." + ID + ".MemberAccounts", memberaccounts);
		plugin.saveConfig();
		player.sendMessage(ChatColor.YELLOW + "You have successfully left your bank");

	}

	// returns a list of all banks
	private void listBanks(Player player) {
		List<String> bankList = config.getStringList("Banks.List");
		player.sendMessage(this.m2c + "-----------------[ " + this.m1c
				+ "Bank - List" + this.m2c + " ]-----------------");
		for (String s : bankList) {
			player.sendMessage(this.m1c + getBankName(s) + " (" + this.m3c + s
					+ this.m1c + ")");
		}

	}

	// TODO returns a list of all members of a specific bank

	private void listMembers(Player player, String ID) {
		List<String> mems = config.getStringList("Banks." + ID + ".Members");
		String msg = mems.get(0);

		for (int x = 1; x < mems.size(); x++) {
			msg = msg + ", " + mems.get(x);
		}

		player.sendMessage(this.m2c + "------------------[ " + this.m1c
				+ "Bank - Members" + this.m2c + " ]-----------------");
		player.sendMessage(this.m1c + getBankName(ID) + " Members: ");
		player.sendMessage(msg);

	}



	// code to pay the bank owner's account the transaction fees
	private void payBank(Player player, double fee) {
		String ID = getBankID(player);
		payBankOwner(ID, (fee / 2));

	}

	// code to pay the bank owner's account the transaction fees
	@SuppressWarnings("static-access")
	private void payBankOwner(String ID, double amount) {
		String account = config.getString("Banks." + ID + ".OwnerAccount");
		Holdings balance = plugin.iConomy.getAccount(account).getHoldings();
		balance.add(amount);

	}

	// TODO
	// /bank help screen...basically copied from the old plugin
	private void sendHelp(Player player) {
		player.sendMessage(this.m2c + "--------------------[ " + this.m1c
				+ "Bank - Help" + this.m2c + " ]-------------------");
		player.sendMessage(this.m1c + "/bank info <bank id>" + this.m2c + " | "
				+ this.m3c + "Display bank information");
		player.sendMessage(this.m1c + "/bank balance" + this.m2c + " | "
				+ this.m3c + "Check your bank balance.");
		player.sendMessage(this.m1c + "/bank list" + this.m2c + " | "
				+ this.m3c + "List all available banks");
		player.sendMessage(this.m2c
				+ "The following commands are restricted to inside banks:");
		player.sendMessage(this.m1c + "/bank leave" + this.m2c + " | "
				+ this.m3c + "Leave your current bank");
		player.sendMessage(this.m1c + "/bank join" + this.m2c + " | "
				+ this.m3c + "Join a non-private bank");
		player.sendMessage(this.m1c + "/bank withdraw <amount>" + this.m2c
				+ " | " + this.m3c + "Take funds from your account");
		player.sendMessage(this.m1c + "/bank deposit <amount>" + this.m2c
				+ " | " + this.m3c + "Add funds to your account");

	}

	// broken

	// withdraw money from a bank
	private void withdraw(Player player, double amount) {

		Holdings bank = getBankHoldings(player);
		Holdings balance = getHoldings(player.getName());
		double fee = getBankFee(player);
		if (bank.hasEnough(amount)) {
			bank.subtract(amount + fee);
			payBank(player, fee);
			balance.add(amount);

			player.sendMessage(ChatColor.YELLOW + "Withdrawn " + amount
					+ " cn from your bank account");
		} else {
			player.sendMessage(ChatColor.YELLOW
					+ "You do not have sufficient funds");
		}

	}

}
