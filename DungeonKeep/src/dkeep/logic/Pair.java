package dkeep.logic;
public class Pair {

    private int x;
    private int y;


    public Pair(int a, int b){
        this.x = a;
        this.y = b;
    }



    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int a){
        this.x = a;
    }

    public void setY(int b){
        this.y = b;
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


   
}
