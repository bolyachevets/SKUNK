package ca.van.abolyachevets.skunk.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
//import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ca.van.abolyachevets.skunk.game.Dice;
import ca.van.abolyachevets.skunk.game.Game;
import ca.van.abolyachevets.skunk.output.TextAreaOutputStream;
import ca.van.bolyachevets.skunk.sound.SoundEffect;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements Observer {

	public static boolean checkedSound = false;
	public static boolean checkedWrite = false;

	private Game game = Game.getInstance(this);
	
	//private Timer timer;

	private JPanel contentPane;
	private JTextField txtRound;
	private JTextField compTotalScore;
	private JTextField playerTotalScore;
	private JTextField txtCompSubtotal;
	private JTextField txtPlayerSubtotal;

	/**
	 * Create the frame.
	 * 
	 * @param customerDao
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 100, 500, 400);
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g); // fill with background color.
				drawDice(g);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		createMenu();
	}

	private void drawDice(Graphics g) {
		Dice.drawDie(g, game.getDice().getDieOne(), 280, 100); // Just draw the dice.
		Dice.drawDie(g, game.getDice().getDieTwo(), 390, 100);
	}
	private void createMenu() {
		// 1. Create a menu bar and add it to the frame
		JMenuBar mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);

		// 2. Add menus to the menu bar
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		mainMenuBar.add(fileMenu);
		JMenu options = new JMenu("Options");
		options.setMnemonic('O');
		mainMenuBar.add(options);
		JMenu helpMenu = new JMenu("Help");
		mainMenuBar.add(helpMenu);

		// 3. Add items to the menus

		// File menu items
		JMenuItem play = new JMenuItem("Play");
		fileMenu.add(play);
		JMenuItem exit = new JMenuItem("Quit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		fileMenu.addSeparator();
		fileMenu.add(exit);

		// Options menu items
		StayOpenCheckBoxMenuItem chckbxSound = new StayOpenCheckBoxMenuItem("Sound On");
		options.add(chckbxSound);
		options.addSeparator();
		JMenuItem topScores = new JMenuItem("Top Scores");
		options.add(topScores);

		JMenuItem about = new JMenuItem("About");
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		helpMenu.add(about);

		// Add TextField and Buttons

		// Create textField and redirect print stream
		JTextArea txtConsole = new JTextArea();
		txtConsole.setFont(txtConsole.getFont().deriveFont(16f));
		PrintStream out = new PrintStream(new TextAreaOutputStream(txtConsole));
		System.setOut(out);
		System.setErr(out);

		// Create game control buttons
		JButton stopBtn = new JButton("Enough");
		stopBtn.setBounds(242, 268, 230, 23);
		JButton rollBtn = new JButton("Roll Dice");
		rollBtn.setBounds(12, 268, 226, 23);

		stopBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(txtConsole);
		scrollPane.setBounds(12, 12, 226, 252);
		contentPane.add(scrollPane);
		rollBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		contentPane.add(rollBtn);
		contentPane.add(stopBtn);
		
		txtRound = new JTextField();
		txtRound.setText("Round: 1");
		txtRound.setBounds(316, 18, 86, 20);
		contentPane.add(txtRound);
		txtRound.setColumns(10);
		
		compTotalScore = new JTextField();
		compTotalScore.setText("CPU total:");
		compTotalScore.setBounds(248, 244, 86, 20);
		contentPane.add(compTotalScore);
		compTotalScore.setColumns(10);
		
		playerTotalScore = new JTextField();
		playerTotalScore.setText("Player total:");
		playerTotalScore.setBounds(372, 244, 86, 20);
		contentPane.add(playerTotalScore);
		playerTotalScore.setColumns(10);
		
		txtCompSubtotal = new JTextField();
		txtCompSubtotal.setText("This round:");
		txtCompSubtotal.setBounds(248, 213, 86, 20);
		contentPane.add(txtCompSubtotal);
		txtCompSubtotal.setColumns(10);
		
		txtPlayerSubtotal = new JTextField();
		txtPlayerSubtotal.setText("This round: ");
		txtPlayerSubtotal.setBounds(372, 213, 86, 20);
		contentPane.add(txtPlayerSubtotal);
		txtPlayerSubtotal.setColumns(10);

		rollBtn.setVisible(false);
		stopBtn.setVisible(false);

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					rollBtn.setVisible(true);
					stopBtn.setVisible(true);			  
				} catch (Exception e2) {
					e2.getMessage();
				}
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		chckbxSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkedSound == false) {
					checkedSound = true; 
				} else {
					checkedSound = false;
				}
			}
		});

		chckbxSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkedWrite == false) {
					checkedWrite = true; 
				} else {
					checkedWrite = false;
				}
			}
		});
		
		topScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TopScore  topS = new TopScore();
				topS.run();			
			}
		});

		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this,
						" There are five rounds (one for each letter of the word SKUNK)."
								+ "\n Human and Computer take turns at rolling a pair of dice."
								+ "\n Getting a single one results in a score of 0 for the current round."
								+ "\n Getting a pair of ones results in the automatic loss for the game."
								+ "\n Otherwise, the points are being added to the round subtotal."
								+ "\n Both players have the choice to stop or to continue rolling the dice between the rolls.",
						"About SKUNK", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		rollBtn.addActionListener(game.al);
		
		rollBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (checkedSound == true) {
			            SoundEffect.playSound();
					}
				} catch (Exception e2) {
					e2.getMessage();
				} 
			}
		});
		
		rollBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
						repaint();
			            txtRound.setText("Round: " + game.getRound());
			            compTotalScore.setText("CPU total: " + game.getTotalScoreComp());
			            playerTotalScore.setText("Player total: " + game.getTotalScorePlayer() );
			            txtCompSubtotal.setText("This round: " + game.getCompSubscore());
			            txtPlayerSubtotal.setText("This round: " + game.getPlayerSubscore());
			
				} catch (Exception e2) {
					e2.getMessage();
				} 
			}
		});
		
		stopBtn.addActionListener(game.al);
		
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
						repaint();
			            txtRound.setText("Round: " + game.getRound());
			            compTotalScore.setText("CPU total: " + game.getTotalScoreComp());
			            playerTotalScore.setText("Player total: " + game.getTotalScorePlayer() );
			            txtCompSubtotal.setText("This round: " + game.getCompSubscore());
			            txtPlayerSubtotal.setText("This round: " + game.getPlayerSubscore());
			
				} catch (Exception e2) {
					e2.getMessage();
				} 
			}
		});
		
	}

	@Override
	public void update(Observable arg0, Object obj) {
		drawDice(contentPane.getGraphics());
	}

}
