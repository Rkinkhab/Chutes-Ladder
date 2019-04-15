public class ChutesAndLadder {
    public static void main(String[] args) throws Exception {
        if(args == null || args.length < 2 || args.length > 4)
            throw new Exception("At least two and at most 4 players need to be provided");
        Game game = new Game(args);
        game.playGame();
    }
}
