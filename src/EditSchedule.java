//class for editing a schedule

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditSchedule extends JFrame implements ActionListener
{	
	private JLabel l_votename,l_starting1,l_starting2,l_ending1,l_ending2,l_notice,
		l_candFull[],l_candShort[];
    private JButton b_saveChng,b_cancel;
    private JTextField tf_s[];
	private QuerySchedule querySchedule;
    private int i;
	private JLabel[] l_a;
	
	public EditSchedule(String voteName,JLabel[] l_b)
	{
		super("Edit schedule");
		
		setLayout(new editScheduleLayout());

		l_a = l_b;

		tf_s = new JTextField[26];
		l_candFull = new JLabel[10];
		l_candShort = new JLabel[10];
		querySchedule = new QuerySchedule();

        l_votename = new JLabel("Vote name :");
        add(l_votename);

        tf_s[0] = new JTextField("");
        tf_s[0].setEditable(false);
		tf_s[0].setBorder(null);
		tf_s[0].setSelectionColor(new Color(238,238,238));
        add(tf_s[0]);

        l_starting1 = new JLabel("Starting date (dd.mm.yy) :");
        add(l_starting1);

        tf_s[1] = new JTextField("");
        add(tf_s[1]);

        l_starting2 = new JLabel("Starting time in 24hr format (hh:mm:ss) :");
        add(l_starting2);

        tf_s[2] = new JTextField("");
        add(tf_s[2]);

        l_ending1 = new JLabel("Ending date (dd.mm.yy) :");
        add(l_ending1);

        tf_s[3] = new JTextField("");
        add(tf_s[3]);

        l_ending2 = new JLabel("Ending time in 24 hr format (hh:mm:ss) :");
        add(l_ending2);

        tf_s[4] = new JTextField("");
        add(tf_s[4]);

		for(i=0;i<=9;i++)
		{
			l_candFull[i] = new JLabel("Candidate " + (i+1) +" fullname :");
			add(l_candFull[i]);
		}

		l_notice = new JLabel("Keep untouched if any candidate does not exist");
        add(l_notice);

        b_saveChng = new JButton("Save changes");
		b_saveChng.setActionCommand("save");
        b_saveChng.addActionListener(this);
        add(b_saveChng);

        for(i=0;i<=9;i++)
		{
			l_candShort[i] = new JLabel("Shortname :");
			add(l_candShort[i]);
		}

		for(i=5;i<=24;i++)
		{
			tf_s[i] = new JTextField("");
	        add(tf_s[i]);
		}
		
        b_cancel = new JButton("Cancel");
		b_cancel.setActionCommand("cancel");
        b_cancel.addActionListener(this);
        add(b_cancel);
        
        querySchedule.getSchdeduleDataForTextField(tf_s,voteName);

        setIconImage((new ImageIcon("images/icon_change.png")).getImage());
		setSize(612,560);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("save"))
		{
			boolean legal=true;
			int totalCand=0;

			//error cheching
			if ( (tf_s[1].getText().isEmpty()) || (tf_s[11].getText().isEmpty()) )
			{
				legal=false;
			}

			if (legal)
			{
				for(i=5;i<=14;i++)
				{
					if (!tf_s[i].getText().equals("-"))
					{
						totalCand++;

						if ( (tf_s[i+10].getText().equals("-")) )
						{
							legal=false;
							break;
						}
					}
				}

				for(i=5+totalCand;i<=14;i++)
				{
					if ( (!tf_s[i].getText().equals("-")) || (!tf_s[i+10].getText().equals("-")) )
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
				String strVoteName = new String(tf_s[0].getText());
				setVisible(false);
				querySchedule.saveScheduleData(tf_s);
				querySchedule.getSchdeduleDataForLabel(l_a,strVoteName);
			}
		}
		else if(ae.getActionCommand().equals("cancel"))
		{
			setVisible(false);
		}	
	}
}

class editScheduleLayout implements LayoutManager 
{
    public editScheduleLayout() 
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
        parent.getComponent(2).setBounds(insets.left+32,insets.top+56,160,24);
        parent.getComponent(3).setBounds(insets.left+186,insets.top+56,60,24);
        parent.getComponent(4).setBounds(insets.left+278,insets.top+56,230,24);
        parent.getComponent(5).setBounds(insets.left+518,insets.top+56,60,24);
        parent.getComponent(6).setBounds(insets.left+32,insets.top+88,160,24);
        parent.getComponent(7).setBounds(insets.left+186,insets.top+88,60,24);
        parent.getComponent(8).setBounds(insets.left+278,insets.top+88,230,24);
        parent.getComponent(9).setBounds(insets.left+518,insets.top+88,60,24);
        parent.getComponent(10).setBounds(insets.left+32,insets.top+152,128,24);
        parent.getComponent(11).setBounds(insets.left+32,insets.top+184,128,24);
        parent.getComponent(12).setBounds(insets.left+32,insets.top+216,128,24);
        parent.getComponent(13).setBounds(insets.left+32,insets.top+248,128,24);
        parent.getComponent(14).setBounds(insets.left+32,insets.top+280,128,24);
        parent.getComponent(15).setBounds(insets.left+32,insets.top+312,128,24);
        parent.getComponent(16).setBounds(insets.left+32,insets.top+344,128,24);
        parent.getComponent(17).setBounds(insets.left+32,insets.top+376,128,24);
        parent.getComponent(18).setBounds(insets.left+32,insets.top+408,128,24);
        parent.getComponent(19).setBounds(insets.left+32,insets.top+440,136,24);
        parent.getComponent(20).setBounds(insets.left+176,insets.top+120,288,24);
        parent.getComponent(21).setBounds(insets.left+176,insets.top+488,120,24);
        parent.getComponent(22).setBounds(insets.left+392,insets.top+152,72,24);
        parent.getComponent(23).setBounds(insets.left+392,insets.top+184,72,24);
        parent.getComponent(24).setBounds(insets.left+392,insets.top+216,72,24);
        parent.getComponent(25).setBounds(insets.left+392,insets.top+312,72,24);
        parent.getComponent(26).setBounds(insets.left+392,insets.top+248,72,24);
        parent.getComponent(27).setBounds(insets.left+392,insets.top+280,72,24);
        parent.getComponent(28).setBounds(insets.left+392,insets.top+376,72,24);
        parent.getComponent(29).setBounds(insets.left+392,insets.top+344,72,24);
        parent.getComponent(30).setBounds(insets.left+392,insets.top+408,72,24);
        parent.getComponent(31).setBounds(insets.left+392,insets.top+440,72,24);
        parent.getComponent(32).setBounds(insets.left+168,insets.top+152,208,24);
        parent.getComponent(33).setBounds(insets.left+168,insets.top+184,208,24);
        parent.getComponent(34).setBounds(insets.left+168,insets.top+216,208,24);
        parent.getComponent(35).setBounds(insets.left+168,insets.top+248,208,24);
        parent.getComponent(36).setBounds(insets.left+168,insets.top+280,208,24);
        parent.getComponent(37).setBounds(insets.left+168,insets.top+312,208,24);
        parent.getComponent(38).setBounds(insets.left+168,insets.top+344,208,24);
        parent.getComponent(39).setBounds(insets.left+168,insets.top+376,208,24);
        parent.getComponent(40).setBounds(insets.left+168,insets.top+408,208,24);
        parent.getComponent(41).setBounds(insets.left+168,insets.top+440,208,24);
        parent.getComponent(42).setBounds(insets.left+472,insets.top+152,104,24);
        parent.getComponent(43).setBounds(insets.left+472,insets.top+184,104,24);
        parent.getComponent(44).setBounds(insets.left+472,insets.top+216,104,24);
        parent.getComponent(45).setBounds(insets.left+472,insets.top+248,104,24);
        parent.getComponent(46).setBounds(insets.left+472,insets.top+280,104,24);
        parent.getComponent(47).setBounds(insets.left+472,insets.top+312,104,24);
        parent.getComponent(48).setBounds(insets.left+472,insets.top+344,104,24);
        parent.getComponent(49).setBounds(insets.left+472,insets.top+376,104,24);
        parent.getComponent(50).setBounds(insets.left+472,insets.top+408,104,24);
        parent.getComponent(51).setBounds(insets.left+472,insets.top+440,104,24);
        parent.getComponent(52).setBounds(insets.left+316,insets.top+488,120,24);
    }
}
