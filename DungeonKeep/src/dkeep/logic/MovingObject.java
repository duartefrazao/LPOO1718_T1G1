package dkeep.logic;
public abstract class MovingObject {
	
	protected Pair position = new Pair(0,0);
	
	public enum MOVEMENT_TYPE {
	    UP, DOWN, LEFT, RIGHT, NONE
	}
	
	public void setX(int x){
		position.setX(x);
	}
	public void setY(int y){
		position.setY(y);
	}
	
	public int getX(){
		return position.getX();
	}
	
	public int getY(){
		return position.getY();
	}

	public Pair getPosition() { return position; }

	public void move(MovingObject.MOVEMENT_TYPE movement, char map[][])
	{
		int x=position.getX();
		int y= position.getY();
		
		switch (movement) {

	       case UP: {
	            if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
	                position.setX(--x); 
	            }
	            break;
	        }
	        case DOWN: {
	            if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
	                position.setX(++x);
	            }
	            break;
	        }
	        case LEFT: {
	            if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
	                position.setY(--y);
	            }
	            break;
	        }
	        case RIGHT: {
	            if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
	                position.setY(++y);
	            }
	            break;
	        }
	        case NONE:
	        	break;
	    }
	}
}
