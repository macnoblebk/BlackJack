import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Game game = new Game(keyboard);
        game.gamePlay();
    }
}
