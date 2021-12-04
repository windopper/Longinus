package userdata;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UserStatManager {

	private final static HashMap<Player, UserStatManager> instance = new HashMap<>();
	
	
	private Player p;
	private int Str = 0;
	private int Dex = 0;
	private int Def = 0;
	private int Agi = 0;
	private int lvl = 0;
	private int exp = 0;
	private int remainstat = 0;
	
	public UserStatManager(Player p) {
		this.p = p;
	}
	
	public static UserStatManager getinstance(Player p) {
		if(!instance.containsKey(p)) {
			p.sendMessage("Statmanager succesfully initialized");
			instance.put(p, new UserStatManager(p));
		}
		return instance.get(p);
	}
	
	public void removeinstance(Player p) {
		instance.remove(p);
	}
	
	public int getStr() {
		return Str;
	}
	public int getDex() {
		return Dex;
	}
	public int getDef() {
		return Def;
	}
	public int getAgi() {
		return Agi;
	}
	public int getremainstat() {
		return remainstat = lvl * 2 - Str - Dex - Def - Agi;
	}
	public int getlvl() {
		return lvl;
	}
	public int getexp()	{
		return exp;
	}
	public void setStr(int Str) {
		this.Str = Str;
	}
	public void setDex(int Dex) {
		this.Dex = Dex;
	}
	public void setDef(int Def) {
		this.Def = Def;
	}
	public void setAgi(int Agi) {
		this.Agi = Agi;
	}
	public void setlvl(int lvl) {
		this.lvl = lvl;
	}
	public void setexp(int exp) {
		this.exp = exp;
	}
	
	
	public void statadd(Player p, String stat, int amount) {
		
		remainstat = lvl * 2 - Str - Dex - Def - Agi;
		
		if(remainstat - amount < 0) {
			statadd(p, stat, remainstat);
		}
		
		if(remainstat <= 0) {
			p.sendMessage("§c남은 스탯이 없습니다");
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.5f, 2f);
			return;
		}
		
		if(stat.equals("str")) {
			
			remainstat-=amount;
			Str+=amount;
			return;
		}
		if(stat.equals("dex")) {
			
			remainstat-=amount;
			Dex+=amount;
			return;
		}
		if(stat.equals("def")) {
			
			remainstat-=amount;
			Def+=amount;
			return;
		}
		if(stat.equals("agi")) {
			
			remainstat-=amount;
			Agi+=amount;
			return;
		}
		
		
	}
	
	public void statreset() {
		
		Str = 0;
		Dex = 0;
		Def = 0;
		Agi = 0;
		
	}
	
	
	
}
