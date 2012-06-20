package xBank.conversations;

import org.bukkit.command.CommandExecutor;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import xBank.Core;



public class Teller extends JavaPlugin implements CommandExecutor, ConversationAbandonedListener {

	private ConversationFactory conversationFactory;
	private Core plugin;

    public Teller(String pName, String bName, boolean mem) {
        this.conversationFactory = new ConversationFactory(this)
                .withModality(true)
                .withFirstPrompt(new FirstPrompt(pName,bName,mem))
                .withEscapeSequence("quit")
                .withTimeout(15)
                .thatExcludesNonPlayersWithMessage("Go away evil console!")
                .addConversationAbandonedListener(this);
    }
    
    

    public Teller(Core core) {
    	this.plugin = core;
	}


	public void ConversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        if (abandonedEvent.gracefulExit()) {
            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation exited gracefully.");
        } else {
            abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" + abandonedEvent.getCanceller().getClass().getName());
        }
    }



@Override
public void conversationAbandoned(ConversationAbandonedEvent arg0) {
	// TODO Auto-generated method stub
	
}


private class FirstPrompt extends MessagePrompt{
	public FirstPrompt(String pName, String bName, boolean mem)
    {
		this.player = pName;
        this.bankName = bName;
        this.member = mem;
    }
	private String player;
	private String bankName;
	private boolean member;
	   

	@Override
	protected Prompt getNextPrompt(ConversationContext arg0) {
		if(member){
			return new MemberPrompt();
		}else{
			return new NonMemberPrompt();
		}
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		return "Welcome to " + bankName + ", " + player + "."; 
		
	}
}


private class MemberPrompt extends ValidatingPrompt
{	
	private boolean runonce = false;
    private String[] allowedInputs = new String[] { "info","leave", "balance", "withdraw", "deposit", "exit" };
 
 
    public String getPromptText(ConversationContext context)
    {
    	if(runonce){
    		context.getForWhom().sendRawMessage("So, what would you like to do today?");
    	}else{
    		runonce = true;
    		context.getForWhom().sendRawMessage("What can I do for you today? (add ? to end of response for help)");
    	}
    	return "| Balance | Withdraw | Deposit | Info | Leave | Exit |";
    }
 
    @Override
    protected Prompt acceptValidatedInput(ConversationContext arg0, String arg1)
    {
        String[] split = arg1.split(" ");
        if(split[0].equalsIgnoreCase("balance"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", use this reponse to see you current account's balance.");
                return this;
            }
            else
            {
              //TODO getBalance();
            }
        }
        if(split[0].equalsIgnoreCase("leave"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", use this response only if you want to leave this bank.");
                return this;
            }
            else
            {
               
            }
        }
        else if(split[0].equalsIgnoreCase("withdraw"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", use this response to withdraw money from your account.");
                return this;
            }
            else
            {
                // Do 'withdraw'
            }
        }
        else if(split[0].equalsIgnoreCase("deposit"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", use this response to deposit money into your account.");
                return this;
            }
            else
            {
             //   new 
            }
        }
        else
        {
            // Do quit
        }
        
        arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", I'm not sure I understand you.");
        return this;
    }
 
    @Override
    protected boolean isInputValid(ConversationContext arg0, String arg1)
    {
        String[] split = arg1.split(" ");
        if(split.length > 2)
        {
            arg0.getForWhom().sendRawMessage("Your asking too much of me! Say '?' after a request to get help.");
            return false;
        }
 
        boolean found = false;
        for(String allowed : this.allowedInputs)
        {
            if(allowed.equalsIgnoreCase(split[0]))
            {
                found = true;
                break;
            }
        }
 
        if(!found || split.length == 2 && !split[1].equals("?"))
        {
            arg0.getForWhom().sendRawMessage("Im confused by what your asking. Say '?' after a request to get help.");
            return false;
        }
  
        return true;
    }
}

private class NonMemberPrompt extends ValidatingPrompt
{
	private boolean runonce = false;
    private String[] allowedInputs = new String[] { "join", "info", "members", "exit" };
 
 
    public String getPromptText(ConversationContext context)
    {
    	if(runonce){
    		context.getForWhom().sendRawMessage("So, what would you like to do today?");
    	}else{
    		runonce = true;
    		context.getForWhom().sendRawMessage("What can I do for you today? (add ? to end of response for help)");
    	}
    	return "| Join | Info | Members | Exit |";
    }
 
    @Override
    protected Prompt acceptValidatedInput(ConversationContext arg0, String arg1)
    {
        String[] split = arg1.split(" ");
        if(split[0].equalsIgnoreCase("balance"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", to see your accounts balance simply tell me 'balance'.");
                return this;
            }
            else
            {
               
            }
        }
        else if(split[0].equalsIgnoreCase("withdraw"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", to withdraw money from your account, tell me 'withdraw'.");
                return this;
            }
            else
            {
                // Do 'withdraw'
            }
        }
        else if(split[0].equalsIgnoreCase("deposit"))
        {
            if(split.length == 2)
            {
                arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", to deposit money into your account, type 'deposit'.");
                return this;
            }
            else
            {
             //   new 
            }
        }
        else
        {
            // Do quit
        }
        
        arg0.getForWhom().sendRawMessage(((Player)arg0.getForWhom()).getName() + ", I'm not sure I understand you.");
        return this;
    }
 
    @Override
    protected boolean isInputValid(ConversationContext arg0, String arg1)
    {
        String[] split = arg1.split(" ");
        if(split.length > 2)
        {
            arg0.getForWhom().sendRawMessage("Your asking too much of me! Say '?' after a request to get help.");
            return false;
        }
 
        boolean found = false;
        for(String allowed : this.allowedInputs)
        {
            if(allowed.equalsIgnoreCase(split[0]))
            {
                found = true;
                break;
            }
        }
 
        if(!found)
        {
            arg0.getForWhom().sendRawMessage("Im confused by what your asking. Say '?' after a request to get help.");
            return false;
        }
 
        if(split.length == 2)
        {
            if(!split[1].equals("?"))
                return false;
        }
 
        return true;
    }
}

}