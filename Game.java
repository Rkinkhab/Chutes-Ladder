import java.util.HashMap;
import java.util.Random;

public class Game {

    private HashMap<Integer, Integer> ladders;
    private HashMap<Integer, Integer> chutes;
    private int spinner;
    private Player[] players;
    private Random random;
    private boolean isInitial;
    private boolean isFinished;

    public Game(String[] listOfPlayers) {
        players = new Player[listOfPlayers.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i + 1, listOfPlayers[i]);
        }
        random = new Random();
        ladders = new HashMap<>();
        chutes = new HashMap<>();
        populateLaddersAndChutes(ladders, chutes);
        isInitial = true;
        isFinished = false;
    }

    private void populateLaddersAndChutes(HashMap<Integer, Integer> ladders, HashMap<Integer, Integer> chutes) {
        ladders.put(1, 38);
        ladders.put(4, 14);
        ladders.put(9, 31);
        ladders.put(21, 42);
        ladders.put(28, 84);
        ladders.put(36, 43);
        ladders.put(51, 68);
        ladders.put(71, 91);
        ladders.put(80, 100);

        chutes.put(98, 78);
        chutes.put(95, 75);
        chutes.put(93, 73);
        chutes.put(87, 24);
        chutes.put(64, 60);
        chutes.put(62, 19);
        chutes.put(56, 53);
        chutes.put(49, 11);
        chutes.put(47, 26);
        chutes.put(16, 6);
    }

    private int spin() {
        return random.nextInt(6) + 1;
    }

    private void initializeOrder(){

        int max = 0;
        int firstIndex = 0;
        HashMap<Integer, Integer> generatedNumbers = new HashMap<>();
        for (int i = 0; i < players.length; i++) {
            int spinResult = spin();
            if(spinResult > max) {
                max = spinResult;
                firstIndex = i;
            }
            if(generatedNumbers.containsKey(spinResult))
                generatedNumbers.put(spinResult, generatedNumbers.get(spinResult) + 1);
            else
                generatedNumbers.put(spinResult, 1);
            players[i].setCurrentPosition(spinResult);
        }

        System.out.println(players[0].toString() + " " + players[0].getCurrentPosition());
        System.out.println(players[1].toString() + " " + players[1].getCurrentPosition());
        System.out.println(players[2].toString() + " " + players[2].getCurrentPosition());
        System.out.println(players[3].toString() + " " + players[3].getCurrentPosition());
        if(firstIndex == 0)
            return;
        Player[] temp = new Player[firstIndex];
        for(int i = 0; i < firstIndex; i++)
            temp[i] = players[i];
        int currentIndex = 0;
        for(int i = firstIndex; i < players.length; i++){
            players[currentIndex] = players[i];
            currentIndex ++;
        }
        for(int i = 0; i < temp.length; i++) {
            players[currentIndex] = temp[i];
            currentIndex++;
        }
        for(Player player: players)
            player.setCurrentPosition(0);
    }

    public void playGame() {
      if(isInitial){
            initializeOrder();
            isInitial = false;
        }

        int noOfTurn = 0;
        while (!isFinished) {
            for (Player currentPlayer : players) {
                noOfTurn ++;
                int spinResult = spin();
                int newPosition = currentPlayer.getCurrentPosition() + spinResult;
                String result = noOfTurn + ": " + currentPlayer.toString() + ": " +
                        currentPlayer.getCurrentPosition() + " --> " + newPosition;
                if (newPosition > 100) {
                    System.out.println(result + "\nExact 100 is required to win. Please try next time.");
                }
                else {
                    if (chutes.containsKey(newPosition)) {
                        int resultPosition = chutes.get(newPosition);
                        result += " -- CHUTE --> " + resultPosition;
                        newPosition = resultPosition;
                    }
                    if(ladders.containsKey(newPosition)){
                        int resultPosition = ladders.get(newPosition);
                        result += " -- LADDER --> " + resultPosition;
                        newPosition = resultPosition;
                    }
                    System.out.println(result);
                    currentPlayer.setCurrentPosition(newPosition);
                }
                if(newPosition == 100){
                    System.out.println("The winner is " + currentPlayer.toString() + "!");
                    isFinished = true;
                    break;
                }
            }
        }
    }

}
