//class for making a new schedule

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateSchedule extends JFrame implements ActionListener
{	
	private JLabel l_votename,l_starting1,l_starting2,l_ending1,l_ending2,l_candfull[],l_candshort[],l_notice;
    private JButton b_createSed,b_cancel;
    private JTextField tf_s[] = new JTextField[21];
    private JComboBox c_time[] = new JComboBox[12];
	private DefaultListModel listModel;
    private QuerySchedule querySchedule;
	int i;
	
	public CreateSchedule(DefaultListModel list)
	{
		super("Create a schedule");		
		setLayout(new createScheduleLayout());

		querySchedule = new QuerySchedule();
		
		listModel = list;
		l_candfull = new JLabel[10];
		l_candshort = new JLabel[10];

        l_votename = new JLabel("Vote name :");
        add(l_votename);

        tf_s[0] = new JTextField("");
        add(tf_s[0]);

        l_starting1 = new JLabel("Starting date :");
        add(l_starting1);

		c_time[0] = new JComboBox();
		for(i=1;i<=31;i++)
		{
			if (i/10==0) c_time[0].addItem('0'+String.valueOf(i));
			else c_time[0].addItem(i);
		}
		add(c_time[0]);
	
		c_time[1] = new JComboBox();
		for(i=1;i<=12;i++)
		{
			if (i/10==0) c_time[1].addItem('0'+String.valueOf(i));
			else c_time[1].addItem(i);
		}
		add(c_time[1]);
	
		c_time[2] = new JComboBox();
		for(i=2010;i<=2050;i++)
		{
			c_time[2].addItem(i);
		}
		add(c_time[2]);

        l_starting2 = new JLabel("Starting time in 24 hour :");
        add(l_starting2);

		c_time[3] = new JComboBox();
		for(i=0;i<=23;i++)
		{
			if (i/10==0) c_time[3].addItem('0'+String.valueOf(i));
			else c_time[3].addItem(i);
		}
		add(c_time[3]);
		
		c_time[4] = new JComboBox();
		for(i=0;i<=59;i++)
		{
			if (i/10==0) c_time[4].addItem('0'+String.valueOf(i));
			else c_time[4].addItem(i);
		}
		add(c_time[4]);
		
		c_time[5] = new JComboBox();
		for(i=0;i<=59;i++)
		{
			if (i/10==0) c_time[5].addItem('0'+String.valueOf(i));
			else c_time[5].addItem(i);
		}
		add(c_time[5]);

        l_ending1 = new JLabel("Ending date :");
        add(l_ending1);

        c_time[6] = new JComboBox();
		for(i=1;i<=31;i++)
		{
			if (i/10==0) c_time[6].addItem('0'+String.valueOf(i));
			else c_time[6].addItem(i);
		}
		add(c_time[6]);
		
		c_time[7] = new JComboBox();
		for(i=1;i<=12;i++)
		{
			if (i/10==0) c_time[7].addItem('0'+String.valueOf(i));
			else c_time[7].addItem(i);
		}
		add(c_time[7]);
		
		c_time[8] = new JComboBox();
		for(i=2010;i<=2050;i++)
		{
			c_time[8].addItem(i);
		}
		add(c_time[8]);
		
        l_ending2 = new JLabel("Ending time in 24 hour :");
        add(l_ending2);

        c_time[9] = new JComboBox();
		for(i=0;i<=23;i++)
		{
			if (i/10==0) c_time[9].addItem('0'+String.valueOf(i));
			else c_time[9].addItem(i);
		}
		add(c_time[9]);
		
		c_time[10] = new JComboBox();
		for(i=0;i<=59;i++)
		{
			if (i/10==0) c_time[10].addItem('0'+String.valueOf(i));
			else c_time[10].addItem(i);
		}
		add(c_time[10]);
		
		c_time[11] = new JComboBox();
		for(i=0;i<=59;i++)
		{
			if (i/10==0) c_time[11].addItem('0'+String.valueOf(i));
			else c_time[11].addItem(i);
		}
		add(c_time[11]);

		for(i=0;i<=9;i++)
		{
			l_candfull[i] = new JLabel("Candidate " + (i+1) +" fullname :");
	        add(l_candfull[i]);
		}

        l_notice = new JLabel("Keep untouched if any candidate does not exist");
        add(l_notice);

		for(i=0;i<=9;i++)
		{
			l_candshort[i] = new JLabel("Shortname :");
	        add(l_candshort[i]);
		}

		for(i=1;i<=20;i++)
		{
			tf_s[i] = new JTextField("");
	        add(tf_s[i]);
		}

        b_createSed = new JButton("  Create  ",new ImageIcon("images/button_ok.png"));
        b_createSed.setActionCommand("create");
		b_createSed.addActionListener(this);
        add(b_createSed);
        
        b_cancel = new JButton("  Cancel  ",new ImageIcon("images/button_cancel.png"));
        b_cancel.setActionCommand("cancel");
		b_cancel.addActionListener(this);
        add(b_cancel);

        setIconImage((new ImageIcon("images/icon_schedule.png")).getImage());
		setSize(612,560);
		setLocationRelativeTo(null);
		setResizable(false);	
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("create"))
		{
			boolean legal=true;
			int totalCand=0;
			
			//error cheching
			if ( (tf_s[0].getText().equals("")) || (tf_s[1].getText().equals("")) || (tf_s[11].getText().equals("")))
			{
				legal=false;
			}
			
			if (legal)
			{
				String s_date,s_time,e_date,e_time;

				s_date = new String();
				s_date = c_time[2].getSelectedItem().toString() +
						 c_time[1].getSelectedItem().toString() +
						 c_time[0].getSelectedItem().toString();

				s_time = new String();
				s_time = c_time[3].getSelectedItem().toString() +
						 c_time[4].getSelectedItem().toString() +
						 c_time[5].getSelectedItem().toString();

				e_date = new String();
				e_date = c_time[8].getSelectedItem().toString() +
						 c_time[7].getSelectedItem().toString() +
						 c_time[6].getSelectedItem().toString();

				e_time = new String();
				e_time = c_time[9].getSelectedItem().toString() +
						 c_time[10].getSelectedItem().toString() +
						 c_time[11].getSelectedItem().toString();

				if (Integer.parseInt(s_date)>Integer.parseInt(e_date))
				{
					legal=false;
				}
				else if ( (Integer.parseInt(s_date)==Integer.parseInt(e_date)) &&
						  (Integer.parseInt(s_time)>=Integer.parseInt(e_time)) )
				{
					legal=false;
				}
				
				for(i=1;i<=10 && legal==true;i++)
				{
					if (!tf_s[i].getText().isEmpty())
					{
						totalCand++;
						
						if (tf_s[i+10].getText().isEmpty())
						{
							legal=false;
							break;
						}
					}
				}

				for(i=totalCand+1;i<=10;i++)
				{
					if ( (!tf_s[i].getText().isEmpty()) || (!tf_s[i+10].getText().isEmpty()) )
					{
						legal=false;
						break;
					}
				}
			}

			if (!legal)
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"               Try again !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else
			{
				setVisible(false);
				querySchedule.createNewSchedule(tf_s,c_time);
				listModel.addElement(tf_s[0].getText());
			}
		}
		else if(ae.getActionCommand().equals("cancel"))
		{
			setVisible(false);
		}	
	}
}

class createScheduleLayout implements LayoutManager 
{
	public createScheduleLayout() 
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
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 605 + insets.left + insets.right;
        dim.height = 528 + insets.top + insets.bottom;

        return dim;
    }

	@Override
    public Dimension minimumLayoutSize(Container parent) 
    {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

	@Override
    public void layoutContainer(Container parent) 
    {
        Insets insets = parent.getInsets();

        parent.getComponent(0).setBounds(insets.left+32,insets.top+24,72,24);
        parent.getComponent(1).setBounds(insets.left+112,insets.top+24,464,24);
        parent.getComponent(2).setBounds(insets.left+32,insets.top+56,88,24);        
        parent.getComponent(3).setBounds(insets.left+120,insets.top+56,44,24);
        parent.getComponent(4).setBounds(insets.left+170,insets.top+56,44,24);
        parent.getComponent(5).setBounds(insets.left+220,insets.top+56,56,24);        
        parent.getComponent(6).setBounds(insets.left+284,insets.top+56,140,24);        
        parent.getComponent(7).setBounds(insets.left+432,insets.top+56,44,24);
        parent.getComponent(8).setBounds(insets.left+482,insets.top+56,44,24);
        parent.getComponent(9).setBounds(insets.left+532,insets.top+56,44,24);        
        parent.getComponent(10).setBounds(insets.left+32,insets.top+88,88,24);        
        parent.getComponent(11).setBounds(insets.left+120,insets.top+88,44,24);
        parent.getComponent(12).setBounds(insets.left+170,insets.top+88,44,24);
        parent.getComponent(13).setBounds(insets.left+220,insets.top+88,56,24);        
        parent.getComponent(14).setBounds(insets.left+284,insets.top+88,170,24);        
        parent.getComponent(15).setBounds(insets.left+432,insets.top+88,44,24);
        parent.getComponent(16).setBounds(insets.left+482,insets.top+88,44,24);
        parent.getComponent(17).setBounds(insets.left+532,insets.top+88,44,24);        
        parent.getComponent(18).setBounds(insets.left+32,insets.top+152,128,24);
        parent.getComponent(19).setBounds(insets.left+32,insets.top+184,128,24);
        parent.getComponent(20).setBounds(insets.left+32,insets.top+216,128,24);
        parent.getComponent(21).setBounds(insets.left+32,insets.top+248,128,24);
        parent.getComponent(22).setBounds(insets.left+32,insets.top+280,128,24);
        parent.getComponent(23).setBounds(insets.left+32,insets.top+312,128,24);
        parent.getComponent(24).setBounds(insets.left+32,insets.top+344,128,24);
        parent.getComponent(25).setBounds(insets.left+32,insets.top+376,128,24);
        parent.getComponent(26).setBounds(insets.left+32,insets.top+408,128,24);
        parent.getComponent(27).setBounds(insets.left+32,insets.top+440,136,24);
        parent.getComponent(28).setBounds(insets.left+176,insets.top+120,288,24);        
        parent.getComponent(29).setBounds(insets.left+392,insets.top+152,72,24);
        parent.getComponent(30).setBounds(insets.left+392,insets.top+184,72,24);
        parent.getComponent(31).setBounds(insets.left+392,insets.top+216,72,24);
        parent.getComponent(32).setBounds(insets.left+392,insets.top+312,72,24);
        parent.getComponent(33).setBounds(insets.left+392,insets.top+248,72,24);
        parent.getComponent(34).setBounds(insets.left+392,insets.top+280,72,24);
        parent.getComponent(35).setBounds(insets.left+392,insets.top+376,72,24);
        parent.getComponent(36).setBounds(insets.left+392,insets.top+344,72,24);
        parent.getComponent(37).setBounds(insets.left+392,insets.top+408,72,24);
        parent.getComponent(38).setBounds(insets.left+392,insets.top+440,72,24);
        parent.getComponent(39).setBounds(insets.left+168,insets.top+152,208,24);
        parent.getComponent(40).setBounds(insets.left+168,insets.top+184,208,24);
        parent.getComponent(41).setBounds(insets.left+168,insets.top+216,208,24);
        parent.getComponent(42).setBounds(insets.left+168,insets.top+248,208,24);
        parent.getComponent(43).setBounds(insets.left+168,insets.top+280,208,24);
        parent.getComponent(44).setBounds(insets.left+168,insets.top+312,208,24);
        parent.getComponent(45).setBounds(insets.left+168,insets.top+344,208,24);
        parent.getComponent(46).setBounds(insets.left+168,insets.top+376,208,24);
        parent.getComponent(47).setBounds(insets.left+168,insets.top+408,208,24);
        parent.getComponent(48).setBounds(insets.left+168,insets.top+440,208,24);
        parent.getComponent(49).setBounds(insets.left+472,insets.top+152,104,24);
        parent.getComponent(50).setBounds(insets.left+472,insets.top+184,104,24);
        parent.getComponent(51).setBounds(insets.left+472,insets.top+216,104,24);
        parent.getComponent(52).setBounds(insets.left+472,insets.top+248,104,24);
        parent.getComponent(53).setBounds(insets.left+472,insets.top+280,104,24);
        parent.getComponent(54).setBounds(insets.left+472,insets.top+312,104,24);
        parent.getComponent(55).setBounds(insets.left+472,insets.top+344,104,24);
        parent.getComponent(56).setBounds(insets.left+472,insets.top+376,104,24);
        parent.getComponent(57).setBounds(insets.left+472,insets.top+408,104,24);
        parent.getComponent(58).setBounds(insets.left+472,insets.top+440,104,24);        
        parent.getComponent(59).setBounds(insets.left+152,insets.top+488,136,24);
        parent.getComponent(60).setBounds(insets.left+328,insets.top+488,136,24);
    }
}
