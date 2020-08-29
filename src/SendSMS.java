//sends a sms to a specific number upon admin's choice

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SendSMS extends SmsVotingScreen implements ActionListener
{
	private JLabel l_mobnum,l_textmes;
    private JTextField tf_mobnum;
    private JTextArea ta_textmes;
    private JScrollPane sp_textmes;
    private JButton b_send,b_cancel;
	private JFrame screen;
	
	public SendSMS()
	{
		super(0);

		screen = new JFrame("Send a SMS");
		screen.setLayout(new sendSMSLayout());
		
		l_mobnum = new JLabel("Mobile number :");
        screen.add(l_mobnum);

        l_textmes = new JLabel("Text message :");
        screen.add(l_textmes);
        
        tf_mobnum = new JTextField("");
        screen.add(tf_mobnum);

        ta_textmes = new JTextArea("");
        ta_textmes.setLineWrap(true);
        sp_textmes = new JScrollPane(ta_textmes);
        screen.add(sp_textmes);

		b_send = new JButton("   Send   ");
		b_send.setActionCommand("send");
		b_send.addActionListener(this);
        screen.add(b_send);

		b_cancel = new JButton("  Cancel  ");
		b_cancel.setActionCommand("cancel");
		b_cancel.addActionListener(this);
        screen.add(b_cancel);

        screen.setIconImage((new ImageIcon("images/icon_sendsms.png")).getImage());
		screen.setSize(362,200);
		screen.setResizable(false);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("send"))
		{
			boolean isValid=true;
			
			for(int i=0;i<tf_mobnum.getText().length();i++)
			{
				char c = tf_mobnum.getText().charAt(i);
				
				if ( (c<'0') || (c>'9') )
				{
					isValid=false;
					break;	
				} 	
			}
			
			if (tf_mobnum.getText().length()==0)
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"You must type phone number !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else if ( (!isValid) || (tf_mobnum .getText().length()!=11) )
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"     Invalid phone number !     ",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else if (ta_textmes.getText().length()==0)
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"You can not send zero length message !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else if (ta_textmes.getText().length()>160)
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"Message length should be within 160 characters !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else
			{
				try
				{
				    /*SmsDriver SMSdriver = new SmsDriver("localhost",9500);
					SMSdriver.sendMessage(tf_mobnum.getText(), ta_textmes.getText());
					SMSdriver.logout();
					*/
					smsDriver.sendMessage(tf_mobnum.getText(), ta_textmes.getText());
					screen.setVisible(false);
				
					JOptionPane.showMessageDialog(
						new JFrame(),
						"Your message has been sent",
						"SMS Based Voting System",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("images/icon_ok.png") );
				}
				catch(Exception e)
				{
				    e.printStackTrace(); 
				}
			}
		}
		else if(ae.getActionCommand().equals("cancel"))
		{
			screen.setVisible(false);
		}
	}
}

class sendSMSLayout implements LayoutManager 
{
    public sendSMSLayout()
    {
    }
    
	@Override
    public void addLayoutComponent(String name, Component comp) 
    {
    }

	@Override
    public void removeLayoutComponent(Component comp) 
    {
    }

	@Override
    public Dimension preferredLayoutSize(Container parent) 
    {
        Dimension dim = new Dimension(0,0);

        Insets insets = parent.getInsets();
        dim.width = 358 + insets.left + insets.right;
        dim.height = 179 + insets.top + insets.bottom;

        return dim;
    }

	@Override
    public Dimension minimumLayoutSize(Container parent) 
    {
        Dimension dim = new Dimension(0,0);
        return dim;
    }

	@Override
    public void layoutContainer(Container parent) 
    {
        Insets insets = parent.getInsets();

        parent.getComponent(0).setBounds(insets.left+24,insets.top+16,104,24);
        parent.getComponent(1).setBounds(insets.left+24,insets.top+48,104,24);
        parent.getComponent(2).setBounds(insets.left+136,insets.top+16,192,24);
        parent.getComponent(3).setBounds(insets.left+136,insets.top+48,192,72);
        parent.getComponent(4).setBounds(insets.left+76,insets.top+132,100,24);
		parent.getComponent(5).setBounds(insets.left+186,insets.top+132,100,24);
    }
}
