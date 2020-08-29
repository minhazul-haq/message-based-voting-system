//smsVoting main Screen that contains all others

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class SmsVotingScreen extends JFrame implements ActionListener,Runnable
{
	public static SmsDriver smsDriver;
	private Thread thread;
	private JTabbedPane tab,tab2;
	//private JPanel panel1,panel2,panel3;
	private Ongoing p1;
	private Schedule p2;
	private Archive p3;
	private JMenuBar bar;
	public static boolean reload=false;

	public SmsVotingScreen(int unused)
	{
		// this is for SendSms.java which uses SmsDriver
		// that is created in SmsVotingScreen.java
	}
	
	public SmsVotingScreen() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		super("SMS based voting system");
		setLayout(new FlowLayout());

		tab = new JTabbedPane(2);
     
        JPanel panel1 = new JPanel();
        tab.addTab(" Ongoing vote ",new ImageIcon("images/tab_ongoing.png"),panel1,"Running voting system");
        p1 = new Ongoing(panel1);
        
        JPanel panel2 = new JPanel();
        tab.addTab("Voting schedule",new ImageIcon("images/tab_schedule.png"),panel2,"Schedule of the upcoming events");       	
       	p2 = new Schedule(panel2);
       	
        JPanel panel3 = new JPanel();
        tab.addTab(" Result archives ",new ImageIcon("images/tab_archive.png"),panel3,"Archive of the past voting results");
		p3 = new Archive(panel3);
       
		tab.setFont(new Font("Georgia",Font.BOLD,12));
		tab.setBackground(new Color(72,61,139));
		tab.setForeground(Color.white);

		add(tab);

		JMenu m_file = new JMenu("   File   ");
		m_file.setMnemonic('f');

		JMenuItem mi_changepw = new JMenuItem("Change password",new ImageIcon("images/menu_change.png"));
        m_file.add(mi_changepw);
		mi_changepw.setActionCommand("change");
        mi_changepw.addActionListener(this);
              
        JMenuItem mi_sendsms = new JMenuItem("Send sms",new ImageIcon("images/menu_sendsms.png"));
        m_file.add(mi_sendsms);
		mi_sendsms.setActionCommand("send");
        mi_sendsms.addActionListener(this);
       
		JMenuItem mi_exit = new JMenuItem("Exit",new ImageIcon("images/menu_exit.png"));
        m_file.add(mi_exit);
		mi_exit.setActionCommand("exit");
        mi_exit.addActionListener(this);
       
        JMenu m_help = new JMenu("   Help   ");
		m_help.setMnemonic('h');

        JMenuItem mi_help = new JMenuItem("Help",new ImageIcon("images/menu_help.png"));
        m_help.add(mi_help);
		mi_help.setActionCommand("help");
        mi_help.addActionListener(this);
                
        JMenuItem mi_about = new JMenuItem("About",new ImageIcon("images/menu_about.png"));
        m_help.add(mi_about);
		mi_about.setActionCommand("about");
        mi_about.addActionListener(this);
        
        bar = new JMenuBar();
        setJMenuBar(bar);
        
        bar.add(m_file);
        bar.add(m_help);

		try
		{
			//Connect to Ozeki NG SMS Gateway and logging in.
			smsDriver = new SmsDriver("localhost",9500);
			System.out.println("connected to Ozeki");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		thread = new Thread(this);
		thread.start();
 	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("change"))
		{
			JLabel l_newPw = new JLabel("Type your new password :");
			JTextField tf_newPw = new JPasswordField();
			Object[] obNew={l_newPw,tf_newPw};

			JOptionPane.showMessageDialog(
				new JFrame(),
				obNew,
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_pwchange.png") );

			String newPassword = tf_newPw.getText();

			try
			{
				if (newPassword.length()!=0)
				{
					char newPsw[] = new char[newPassword.length()];
				    newPassword.getChars(0, newPassword.length(), newPsw, 0);

					FileWriter fileWriter = new FileWriter("data/password.dat");

					int i,l;
					char temp;
					l = newPsw.length;

					for(i=0;i<l/2;i++)
					{
					   	if (i%2==0)
					   	{
					   		temp=newPsw[i];
					   		newPsw[i]=newPsw[l-i-1];
					   		newPsw[l-i-1] = temp;
					   	}
					}

					for(i=0;i<l;i++)
					{
					    fileWriter.write(newPsw[i]);
					}

					fileWriter.close();

					JOptionPane.showMessageDialog(
						new JFrame(),
						"Your new password has been set.",
						"SMS Based Voting System",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("images/icon_ok.png") );
				}
			}
			catch(NullPointerException npe)
			{
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"Error occured while setting new password !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
		}
		else if(ae.getActionCommand().equals("send"))
		{
			SendSMS SendSMS = new SendSMS();
		}
		else if(ae.getActionCommand().equals("exit"))
		{
			int reply = JOptionPane.showConfirmDialog(
				new JFrame(),
				"  Do you want to exit now? ",
				"SMS Based Voting System",
				0,
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_exit.png") );

			if (reply==0) System.exit(0);
		}
		else if(ae.getActionCommand().equals("help"))
		{
			Help help = new Help();
		}
		else if(ae.getActionCommand().equals("about"))
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Developer : Mohammad Minhazul Haq\n" +
				"Student ID : 0805051\n" +
				"Level: 2, Term: 1\n" +
				"Department: CSE, BUET",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_about.png") );
		}
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				if(reload)
				{
					System.out.println("thread...");

					tab.remove(0);
					tab.remove(0);

					JPanel panel4 = new JPanel();
					Ongoing p4 = new Ongoing(panel4);

					JPanel panel5 = new JPanel();
					Schedule p5 = new Schedule(panel5);

					JPanel panel6 = new JPanel();
					Archive p6 = new Archive(panel6);

					tab.addTab(" Ongoing vote ",new ImageIcon("images/tab_ongoing.png"),panel4,"Running voting system");
					tab.addTab("Voting schedule",new ImageIcon("images/tab_schedule.png"),panel5,"Schedule of the upcoming events");
				 	tab.addTab(" Result archives ",new ImageIcon("images/tab_archive.png"),panel6,"Archive of the past voting results");

					tab.remove(0);

					reload=false;
					Thread.sleep(1000);
				}				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
