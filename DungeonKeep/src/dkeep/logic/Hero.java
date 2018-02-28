package dkeep.logic;

public class Hero extends MovingObject {

    private char Symbol = 'H';

    private boolean hasKey = false;

    public boolean hasKey(){ return hasKey;}


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


}
