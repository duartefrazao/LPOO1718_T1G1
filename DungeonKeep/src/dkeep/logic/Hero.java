package dkeep.logic;

public class Hero extends MovingObject {

    private char Symbol = 'H';

    private boolean hasKey = false;
    
    private Weapon weapon; 
    
    public boolean hasKey(){ 
    	return hasKey;
    	}


    public char getSymbol(){ return this.Symbol;}

    public void setSymbol(char symbol){
    	if(symbol == 'K')
    		hasKey = true;

    	this.Symbol = symbol;
    }
	
	public Hero()
	{
		super();
	}


	public Weapon getWeapon() {
		return weapon;
	}


	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
