package xBank.commands;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import xBank.Core;

import com.iConomy.iConomy;
import com.iConomy.system.Holdings;

public class BankAdmin implements CommandExecutor {
	ChatColor ec = ChatColor.RED;
	ChatColor m1c = ChatColor.YELLOW;
	ChatColor m2c = ChatColor.GOLD;
	ChatColor m3c = ChatColor.WHITE;
	private FileConfiguration config = null;
	private FileConfiguration data = null;
	private Core plugin;

	public BankAdmin(Core core) {
		this.plugin = core;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		this.config = plugin.getConfig();
		this.data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "data.yml"));
		
		if (Bukkit.getServer().getPluginManager()
				.isPluginEnabled("PermissionsEx")) {
			PermissionManager pex = PermissionsEx.getPermissionManager();

			Player player = (Player) sender;

			if (pex.has(player, "bank.admin")) {
				
				if(args.length == 0){
					player.sendMessage("Bad NEO!!  Y U no HAZ COMMANDS!!!");
					player.sendMessage("| Update | Delete | Under | Level | Total | Over |");
					return true;
				}
				switch (args[0]) {

				case "level":
						player.sendMessage("Bad NEO!!  You no do this yet!");
					return true;
				case "update":{ 
				
						doUpdate(player);
					
					return true;}
				case "delete":{ 
					doDelete(args[1]);
					return true;
				}
				case "rank":
					player.sendMessage("Bad NEO!!  You no do this yet!");
					return true;
				case "under":{
					player.sendMessage("The number of accounts under " + 
							iConomy.format(Double.valueOf(args[1])) + 
							" is:" + doCheck(false, args[1]));
					return true;
				}
				case "over":{
					
					player.sendMessage("The number of accounts over " + 
							iConomy.format(Double.valueOf(args[1])) + 
							" is:" + doCheck(true, args[1]));
					return true;
				}
				case "total":
					if( args.length == 2){
						if(args[1].toLowerCase().matches("save")){
							player.sendMessage("did it" + args[1]);
							doSave(player);
							return true;
						}
						player.sendMessage("didn't do it  ->" + args[1] + "<-");
						if(bankExists(args[1].toUpperCase())){
							doTotal(player, args[1].toUpperCase());
							return true;
						}else{
							player.sendMessage("Neo, " + args[1] + " Does not exist!");
							return true;
						}
					}else{
						doTotal(player);
						return true;
					}
				default:
					return true;

				}

			}

		}
		return true;
	}
	
	private void doSave(Player player) {

		//TODO Save addition
		List<String> banks = config.getStringList("Banks.List");
		int temp =0;
		long epoch = System.currentTimeMillis()/1000;
		String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (epoch*1000));
		
		for(String ID: banks){
			temp = config.getInt("Banks." + ID + ".Total");	
			data.set("Banks." + ID + "." + date,temp); 
			try {
				this.data.save(new File(plugin.getDataFolder(), "data.yml"));
			} catch (IOException e) {
				player.sendMessage("Xemos wrote poo code, MSG him if you got this.");
				e.printStackTrace();
			}
		}
		
	}

	// check true = over // false = under
	private int doCheck(boolean check, String number) { 
		List<String> banks = config.getStringList("Banks.List");
		List<String> accounts = new ArrayList<String>();
		
		for (String current : banks) {
			accounts.addAll(config.getStringList("Banks." + current + ".MemberAccounts"));
		}
				
		double amount = Double.valueOf(number);
		int count = 0;
		Holdings bankAcc = null;
		
		if(check){
			for(String player : accounts){
					bankAcc = getAccount(player);
					if(bankAcc.hasOver(amount)){
						count ++;
					}
				
			}
		}else{
			for (String player : accounts) {
					bankAcc = getAccount(player);
					if (bankAcc.hasUnder(amount)) {
						count++;
					}
				}
			
		}
		return count;
		
				
	}



	private void doTotal(Player player) {
		
		player.sendMessage("The total in all banks is " + iConomy.format(Double.valueOf(config.getString("Banks.Total"))));
	}

	private void doTotal(Player player, String ID) {
		player.sendMessage("The total in " + ID + " is " + iConomy.format(Double.valueOf(config.getString("Banks." + ID + ".Total"))));
		
	}

	@SuppressWarnings("static-access")
	private void doDelete(String ID) {
		List<String> mems = config.getStringList("Banks." + ID);
		
		double monies = 0;
		
		Holdings bankAcc = null;
		Holdings playerAcc = null;
		
		
		for(String stripper : mems){
			bankAcc = getAccount(getBankAccount(stripper));
			playerAcc = getAccount(stripper);
			monies = bankAcc.balance();
			playerAcc.add(monies);
			plugin.iConomy.Accounts.removeCompletely(getBankAccount(stripper));
			config.set("Players." + stripper, null);
		}
		
		config.set("Banks." + ID, null);
		plugin.saveConfig();
		
	}
	
	@SuppressWarnings("static-access")
	public Holdings getAccount(String account) {
		
		Holdings balance = plugin.iConomy.getAccount(account).getHoldings();
		return balance;
	}
	
	private String getBankAccount(String account) {
		String accountname = config.getString("Players." + account
				+ ".Account");
		return accountname;
	}



	private boolean doUpdate(Player player){
		
		List<String> banks = config.getStringList("Banks.List");
		List<String> accHolders = null;
		int total = 0;
		String member = "";
		ArrayList<String> region = new ArrayList<String>();
		String ID = "";
		int sum =0;
		
		
		
		
		for (int x = 0; x < banks.size(); x++){
			ID = banks.get(x);
			accHolders = config.getStringList("Banks." + ID + ".Members");
			for (int y = 0; y < accHolders.size(); y++){
				member = accHolders.get(y);
				sum += (getHoldings(ID + "-" + member));
				
				
			}
			
			if(!config.contains("Banks." + ID + ".Membercap")){
				config.set("Banks." + ID + ".Membercap",8);
				
			}
			
			region.add(x,config.getString("Banks." + ID + ".Region"));
			
			config.set("Location." + region.get(x), ID);
			
			config.set("Banks." + ID + ".Total", sum);
		
			total += sum;
			sum = 0;
			accHolders = null;
		}
		config.set("Region", region);
		config.set("Banks.Total", total);
		player.sendMessage("The bank totals have been recalculated.");
		plugin.saveConfig();
		
		
		
		return true;
	}
/*
	private boolean update(String ID, Player player){
		Holdings sum = getHoldings(ID + "-" + player.getName());
			
		return true;
	}
	*/
	
	//Gets info on bank accounts
	@SuppressWarnings("static-access")
	private double getHoldings(String name) {
		Holdings balance = plugin.iConomy.getAccount(name).getHoldings();
	
		return balance.balance();
		
	}
	
	// check to see if the specified bank exists
	private boolean bankExists(String ID) {
		if (config.contains("Banks." + ID)) {
			return true;
		}
		return false;
	}
	
	/*

	// same method as above with a string input instead of a player
	private boolean isInBank(String owner) {
		if (config.contains("Players." + owner)) {
			if (!(config.getString("Players." + owner + ".Bank") == null)) {
				return true;
			}
		}

		return false;
	}

	private double getInterest(int level) {
		switch(level){
			case 1: return .06;
			case 2: return .12;
			case 3: return .18;
			case 4: return .24;
			case 5: return .30;
			case 6: return .36;
			case 7: return .42;
			case 8: return .48;
			case 9: return .54;
			case 10:return .60;
			default: return 0;
		}
	}

	private double getFee(int level) {
		switch(level){
			case 1: return 50.00;
			case 2: return 100.00;
			case 3: return 150.00;
			case 4: return 200.00;
			case 5: return 250.00;
			case 6: return 300.00;
			case 7: return 350.00;
			case 8: return 400.00;
			case 9: return 450.00;
			case 10:return 500.00;
			default: return 0;
		}
	}

	// Method to add banks to the list of banks in the config...bad code i know,
	// it is redundant
	private void addToBankList(String iD) {
		List<String> banks = config.getStringList("Banks.List");
		banks.add(iD);
		config.set("Banks.List", banks);
		plugin.saveConfig();

	}



	
	private void setLevel(String ID, int level, Player player) {
		config.set("Banks." + ID + ".Fee", getFee(level));
		config.set("Banks." + ID + ".JoinFee", getFee(level));
		config.set("Banks." + ID + ".Interest", getInterest(level));
		plugin.saveConfig();
		player.sendMessage(ChatColor.YELLOW + "Bank Level changed to " + level);
	}

	// makes a new bank
	@SuppressWarnings("static-access")
	private void newBank(String ID, String name, String owner, String region) {
		addToBankList(ID);
		String ownerName = plugin.getServer().getOfflinePlayer(owner).getName();
		int level = 1;

		config.set("Banks." + ID + ".Fee", getFee(level));
		config.set("Banks." + ID + ".Region", region);
		config.set("Banks." + ID + ".JoinFee", getFee(level));

		if (isInBank(ownerName)) {
			String ID1 = config.getString("Players." + ownerName + ".Bank");
			List<String> members = config.getStringList("Banks." + ID1
					+ ".Members");
			members.remove(ownerName);
			config.set("Banks." + ID1 + ".Members", members);
			config.set("Players." + ownerName + ".Bank", null);
			String accountname = config.getString("Players." + ownerName
					+ ".Account");
			if (!plugin.iConomy.Accounts.exists(accountname)) {
				Holdings oldact = plugin.iConomy.getAccount(accountname)
						.getHoldings();
				double bal = oldact.balance();
				Holdings useract = plugin.iConomy.getAccount(ownerName)
						.getHoldings();
				useract.add(bal);
				plugin.iConomy.Accounts.remove(accountname);
			}
			config.set("Players." + ownerName + ".Account", null);
			List<String> memberaccounts = config.getStringList("Banks." + ID1
					+ ".MemberAccounts");
			memberaccounts.remove(accountname);
			config.set("Banks." + ID1 + ".MemberAccounts", memberaccounts);
		}

		String own = ID + "-" + ownerName;
		List<String> ownerlst = new ArrayList<String>();
		List<String> owneract = new ArrayList<String>();
		ownerlst.add(ownerName);
		owneract.add(own);
		config.set("Banks." + ID + ".Members", ownerlst);
		config.set("Banks." + ID + ".MemberAccounts", owneract);
		config.set("Banks." + ID + ".OwnerAccount", own);
		config.set("Banks." + ID + ".Name", name);
		config.set("Banks." + ID + ".Interest", getInterest(level));
		config.set("Banks." + ID + ".Owner", ownerName);

		plugin.saveConfig();

	}

	
	 * 
	 * } else if(args[0].equalsIgnoreCase(
	 * 
	 * 
	 * } else if((args[0].equalsIgnoreCase("create"))&&(pex.has(player,
	 * "hbank.admin"))){
	 * 
	 * if(args.length == 5){ String ID = args[1]; String Name = args[2]; String
	 * Owner = args[3]; String Region = args[4];
	 * 
	 * 
	 * if(!bankExists(ID)){
	 * 
	 * newBank(ID,Name,Owner, Region); } else{
	 * player.sendMessage(ChatColor.YELLOW +
	 * "Bank with that ID already exists"); } } else {
	 * player.sendMessage(ChatColor.YELLOW +
	 * "/bank create ID name owner regionName"); }
	 * 
	 * 
	 * 
	 * } else if((args[0] == "setLevel")&&(pex.has(player,
	 * "hbank.admin"))){//This is broken!!! if(args.length == 3){ String ID =
	 * args[1]; int Level = Integer.parseInt(args[2]);
	 * 
	 * setLevel(ID,Level,player);
	 * 
	 * return true; } else { player.sendMessage(ChatColor.YELLOW +
	 * "/bank setlevel ID level"); }
	 * 
	 * } else { sendHelp(player); } } return true; }
	 */

}
