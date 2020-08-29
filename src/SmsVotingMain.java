//main java file

import javax.swing.*;
import java.io.*;

public class SmsVotingMain extends JFrame
{
    public static String getPassword() throws Exception
	{
		char password[],temp;

        File file = new File("password.dat");
        FileReader fileReader = new FileReader(file);

	    int l = (int)(file.length());

	   	password = new char[l];
	   	fileReader.read(password);

	    for(int i=0;i<l/2;i+=2)
	    {
	    	temp = password[i];
	    	password[i] = password[l-i-1];
	    	password[l-i-1] = temp;
	    }

	    fileReader.close();

		return (new String(password));
	}

	public static void main(String args[])
	{
		try
		{
			JLabel l_pw = new JLabel("Enter password to login");
			JTextField tf_pw = new JPasswordField();
			Object[] ob={l_pw,tf_pw};
			
			JOptionPane.showMessageDialog(
				new JFrame(),
				ob,
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_password.png") );

			String strPassword = tf_pw.getText();

			if (strPassword.equals(getPassword()))
			{
				SmsVotingScreen screen = new SmsVotingScreen();
				screen.setIconImage((new ImageIcon("images/icon_main.png")).getImage());
				screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				screen.setSize(986,585);
				screen.setResizable(false);
				screen.setLocationRelativeTo(null);
				screen.setVisible(true);			
			}
			else
			{
				if (strPassword.isEmpty()) System.exit(0);

				JOptionPane.showMessageDialog(
					new JFrame(),
					"         Wrong password !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );

				System.exit(0);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error occured while reading password from file !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );

			System.exit(0);
		}
	}
}
