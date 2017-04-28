package ca.van.abolyachevets.skunk.ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ca.van.abolyachevets.skunk.db.DBConnector;
import ca.van.abolyachevets.skunk.game.Player;

@SuppressWarnings("serial")
public class TopScore extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JList<Player> playerList;
	
	private DBConnector dbc = new DBConnector();
	
	public void run() {
		try {
			TopScore ts = new TopScore();
			if (dbc.getConnection() == null) {
				System.out.println("huy");
			}
			ts.setScores(dbc.getConnection());
			ts.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ts.setVisible(true);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void setScores(Connection con) throws Exception {

		setBounds(400, 400, 900, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		List<String> names = DBConnector.getPlayerNames(con);
		DefaultListModel<Player> listModel = new DefaultListModel<>();

		Iterator<String> it = names.iterator();

		while (it.hasNext()) {
			String name = it.next();
            System.out.println(name);
			Player player = DBConnector.getPlayer(con, name);
			listModel.addElement(player);
		}
		playerList = new JList<>(listModel);

		contentPanel.add(new JScrollPane(playerList));
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

}
