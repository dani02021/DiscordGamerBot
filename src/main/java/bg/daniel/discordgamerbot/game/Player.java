package bg.daniel.discordgamerbot.game;

public class Player {
	private Game game;
	
	private long id;
	
	private String name;
	private int hp = 1000;
	
	public Player(String name, long id) {
		this.name = name;
		this.id = id;
	}
	
	public void die() {
		game.finish();
	}
	
	public int getHP() {
		return hp;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void damage(int hp) {
		this.hp -= hp;
		
		if(this.hp <= 0)
			die();
	}
	
	public String getName() {
		return name;
	}
	
	public long getID() {
		return id;
	}
	
	public void setGame(Game game) {
		this.game = game; 
	}
	
	public void removeFromGame() {
		this.game = null;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean isInGame() {
		return game != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Player other = (Player) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
