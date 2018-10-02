package domains;

/*
 * @author Felipe Najson and Marco Fiorito
 */
public class Player {
    private String name;
    private String nickName;
    private int age;
    private int wonGames;
    private int color;

    public Player(String name, String nickName, int age) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.wonGames = 0;
        this.color = 0;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }
    
    @Override
    public boolean equals(Object o){
       return this.getNickName().equals(((Player)o).getNickName());
    }

    @Override
    public String toString() {
        return "\n•Nombre: " + this.getName() + "\n•NickName: " + this.getNickName() + "\n•Edad: " + this.getNickName() + "\n•Partidas ganadas " + this.getWonGames() + "\n";
    }
    
}