package ca.van.abolyachevets.skunk.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JButton;

/**
 * class Game implements the game of SKUNK
 *
 * @author Andrew Bolyachevets
 * @version 22/06/2016
 */
public class Game extends Observable {
	private Random randomNumberGenerator = new Random();
	private Dice dice = Dice.getInstance();

	private static int subscoreComp = 0;
	private static int subscorePlayer = 0;
	private static int totalScoreComp = 0;
	private static int totalScorePlayer = 0;
	private int stateComp = STAND;
	private int statePlayer = STAND;
	private static int round = 1;

	private final static int MIN_DIE = 1;
	// private final static int MAX_DIE = 6;
	private final static int STAND = 1;
	private final static int SIT = 0;
	private final static int SKUNK = 5;
	private final static int STATE = 2;
	
	public static boolean over = false;
	
    private static final Game instance = null;
    
    //private constructor to avoid client applications to use constructor
    private Game(Observer o){
    	addObserver(o);
    }

    public static Game getInstance(Observer o){
    	if (instance != null) return instance;
    	else return new Game(o);
    }

	public ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {

			compDecision();

			playerDecision(ae);

			rollTheDice();

			updateScores();

			checkEndRound();

			checkEndGame();

		}

		private void rollTheDice() {
			if (stateComp == STAND || statePlayer == STAND) {
				for (int i = 80; i > 0; i--) {
					dice.roll();
					setChanged();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.getMessage();
					}
					notifyObservers();

				}
				System.out.println("The dice show: " + dice.getDieOne() + " and " + dice.getDieTwo());
				if (stateComp == STAND && statePlayer == STAND) {
					if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
						System.out.println("Getting ready to \n reroll...");
						rollTheDice();
					}
				}
			}
		}

		private void updateScores() {

			if (stateComp == STAND && statePlayer == STAND) {
				subscoreComp += (dice.getDieOne() + dice.getDieTwo());
				subscorePlayer += (dice.getDieOne() + dice.getDieTwo());
				// System.out.println("Your score for \n this round is: " +
				// subscorePlayer);
				// System.out.println("Computer's \n score for \n this round is:
				// " + subscoreComp);
			}
			if (stateComp == STAND && statePlayer == SIT) {
				if (dice.getDieOne() == MIN_DIE && dice.getDieTwo() == MIN_DIE) {
					subscoreComp = 0;
					totalScoreComp = 0;
					// dice.setDice(0, 0);
					exitRound();
					System.out.println("Computer lost the game");
					// System.out.println("Computers total score is " +
					// totalScoreComp);
					System.out.println("You won the game");
					// System.out.println("Your total score is " +
					// totalScorePlayer);
					stateComp = SIT;
					statePlayer = SIT;
					round = SKUNK;
				}
				if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
					subscoreComp = 0;
					// dice.setDice(0, 0);
					exitRound();
					System.out.println("Computer lost this round");
					System.out.println("You won this round");
					stateComp = SIT;
					statePlayer = SIT;
				}
				if (!(dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE)) {
					subscoreComp += (dice.getDieOne() + dice.getDieTwo());
				}
				// System.out.println("Your score for \n this round is: " +
				// subscorePlayer);
				// System.out.println("Computer's score \n for this round is: "
				// + subscoreComp);
			}
			if (stateComp == SIT && statePlayer == STAND) {
				if (dice.getDieOne() == MIN_DIE && dice.getDieTwo() == MIN_DIE) {
					subscorePlayer = 0;
					totalScorePlayer = 0;
					// dice.setDice(0, 0);
					exitRound();
					System.out.println("You lost the game");
					// System.out.println("Your total score is " +
					// totalScorePlayer);
					System.out.println("Computer won the game");
					// System.out.println("Computer's total score is " +
					// totalScoreComp);
					stateComp = SIT;
					statePlayer = SIT;
					round = SKUNK;
				}
				if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
					subscorePlayer = 0;
					// dice.setDice(0, 0);
					exitRound();
					System.out.println("You lost this round");
					System.out.println("Computer won this round");
					stateComp = SIT;
					statePlayer = SIT;
				}
				if (!(dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE)) {
					subscorePlayer += (dice.getDieOne() + dice.getDieTwo());
				}
				// System.out.println("Your score for \n this round is: " +
				// subscorePlayer);
				// System.out.println("Computer's score \n for this round is: "
				// + subscoreComp);
			}
		}

		private void compDecision() {
			if (subscoreComp == 0 && stateComp == STAND) {
				stateComp = STAND;
			}
			if (subscoreComp != 0 && stateComp == STAND) {
				stateComp = randomNumberGenerator.nextInt(STATE);
				if (stateComp == STAND && statePlayer == STAND) {
					System.out.println("Computer chooses to \n keep playing. \n Your call...");
				}
				if (stateComp == SIT && statePlayer == STAND) {
					System.out.println("Computer chooses to stop. \n Your call...");
				}
				if (stateComp == STAND && statePlayer == SIT) {
					System.out.println("Computer chooses to \n continue. \n The dice are rolled \n again.");
				}
				if (stateComp == SIT && statePlayer == SIT) {
					System.out.println("Computer chooses to stop.");
				}
			}
		}

		private void playerDecision(ActionEvent ae) {
			if (statePlayer == STAND) {
				JButton block = (JButton) ae.getSource();
				if (block.getText().equals("Enough")) {
					statePlayer = SIT;
					System.out.println("You chose to stop.");
				}
				block = null;
			}
			if (statePlayer == SIT) {
				statePlayer = SIT;
			}
		}

		private void checkEndRound() {
			if (stateComp == SIT && statePlayer == SIT) {
				totalScorePlayer += subscorePlayer;
				totalScoreComp += subscoreComp;

				System.out.println("End of round: " + round);
				// System.out.println("Your total score is: " +
				// totalScorePlayer);
				// System.out.println("Computer's total score is: " +
				// totalScoreComp + "\n");

				stateComp = STAND;
				statePlayer = STAND;

				subscorePlayer = 0;
				subscoreComp = 0;

				round++;

			}
		}

		private void checkEndGame() {
			if (round > SKUNK) {
				finalMessage();
				// need to restart the thread
				stateComp = STAND;
				statePlayer = STAND;
				totalScorePlayer = 0;
				totalScoreComp = 0;
				over = true;
				round = 1;
				return;
			}
		}

	};

	private void exitRound() {
		stateComp = SIT;
		statePlayer = SIT;
	}

	private void finalMessage() {
		if (totalScoreComp > totalScorePlayer) {
			System.out.println("");
			System.out.println("Hail the computer! \n Another human bites \n the dust!");
			return;
		}
		if (totalScoreComp < totalScorePlayer) {
			System.out.println("");
			System.out.println("You win now, human, \n but we will meet \n again.");
			return;
		}
		if (totalScoreComp == totalScorePlayer) {
			System.out.println("");
			System.out.println("Match of the equals.");
		}
	}

	public Dice getDice() {
		return dice;
	}

	public int getRound() {
		return round;
	}

	public int getTotalScoreComp() {
		return totalScoreComp;
	}

	public int getTotalScorePlayer() {
		return totalScorePlayer;
	}

	public int getCompSubscore() {
		return subscoreComp;
	}

	public int getPlayerSubscore() {
		return subscorePlayer;
	}

}
