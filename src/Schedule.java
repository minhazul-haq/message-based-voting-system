//class for all contents in schedule portion

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Schedule extends JFrame implements ActionListener,ListSelectionListener
{
	private JLabel l_upcoming,l_votename,l_startdate,l_starttime,l_enddate,l_endtime,l_candlist;
    private JButton b_delete,b_change,b_create;
    public JList lst_schedule;
    public JScrollPane sp_schedule;
    public DefaultListModel listModel;
   	public JLabel l_a[]=new JLabel[16];
   	public QuerySchedule querySchedule;

	public Schedule(JPanel pnl)
	{
        pnl.setLayout(new scheduleLayout());

		querySchedule = new QuerySchedule();
				
        l_upcoming = new JLabel("Upcoming votes :");
        l_upcoming.setFont(new Font("Arial",Font.BOLD,12));
        pnl.add(l_upcoming);

        b_create = new JButton("Create",new ImageIcon("images/button_create.png"));
        b_create.setActionCommand("create");
		b_create.addActionListener(this);
        pnl.add(b_create);

        b_delete = new JButton("Delete",new ImageIcon("images/button_delete.png"));
        b_delete.setActionCommand("delete");
		b_delete.addActionListener(this);
        pnl.add(b_delete);

        b_change = new JButton("Change",new ImageIcon("images/button_change.png"));
        b_change.setActionCommand("change");
		b_change.addActionListener(this);
        pnl.add(b_change);
        
        l_votename = new JLabel("Vote name :");
		l_votename.setFont(new Font("Arial",Font.PLAIN,13));
        l_votename.setVisible(false);
        pnl.add(l_votename);

        l_startdate = new JLabel("Starting date :");
        l_startdate.setFont(new Font("Arial",Font.PLAIN,13));
        l_startdate.setVisible(false);
		pnl.add(l_startdate);

        l_enddate = new JLabel("Ending date :");
        l_enddate.setFont(new Font("Arial",Font.PLAIN,13));
        l_enddate.setVisible(false);
		pnl.add(l_enddate);

        l_starttime = new JLabel("Starting time :");
        l_starttime.setFont(new Font("Arial",Font.PLAIN,13));
        l_starttime.setVisible(false);
		pnl.add(l_starttime);

        l_endtime = new JLabel("Ending time :");
        l_endtime.setFont(new Font("Arial",Font.PLAIN,13));
        l_endtime.setVisible(false);
		pnl.add(l_endtime);

        l_candlist = new JLabel("Cadidates list :");
        l_candlist.setFont(new Font("Arial",Font.PLAIN,13));
        l_candlist.setVisible(false);
		pnl.add(l_candlist);

		for(int i=0;i<=14;i++)
		{
			l_a[i] = new JLabel("a"+i);
			l_a[i].setFont(new Font("Arial",Font.PLAIN,13));
			pnl.add(l_a[i]);
		}

		l_a[15] = new JLabel("No vote available in schedule");
		l_a[15].setFont(new Font("Georgia",Font.BOLD,14));
		pnl.add(l_a[15]);

        listModel = new DefaultListModel();
        querySchedule.createScheduleList(listModel);
		lst_schedule = new JList(listModel);
        lst_schedule.setFont(new Font("Arial",Font.PLAIN,13));
		lst_schedule.setBackground(new Color(238,233,233));

        if (querySchedule.totalSchedules()!=0)
        {
        	for(int i=0;i<=14;i++)
	    	{
	    		l_a[i].setText("");
	    	}

			l_a[15].setVisible(false);
	    }
	    else
	    {
	    	for(int i=0;i<=14;i++)
	    	{
	    		l_a[i].setText("");
	    	}

			l_a[15].setVisible(true);
	    }
	
		lst_schedule.addListSelectionListener(this);
	    sp_schedule = new JScrollPane(lst_schedule);
        pnl.add(sp_schedule);
	}	

	@Override
	public void valueChanged(ListSelectionEvent event)
	{
		if (lst_schedule.getLastVisibleIndex()!=-1)
		{
			if (!lst_schedule.isSelectionEmpty())
			{
				l_votename.setVisible(true);
				l_startdate.setVisible(true);
				l_enddate.setVisible(true);
				l_starttime.setVisible(true);
				l_endtime.setVisible(true);
				l_candlist.setVisible(true);

				querySchedule.getSchdeduleDataForLabel(l_a,(lst_schedule.getSelectedValue()).toString());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("create"))
		{
			CreateSchedule CS = new CreateSchedule(listModel);

			l_a[15].setVisible(false);
			lst_schedule.setSelectedIndex(0);
		}
		else if(ae.getActionCommand().equals("delete"))
		{
			if (lst_schedule.isSelectionEmpty())
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"You must select a schedule !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else
			{
				String str_rem = (lst_schedule.getSelectedValue()).toString();
				
				int reply = JOptionPane.showConfirmDialog(
					new JFrame(),
					"Are you sure to delete " + str_rem + " ?",
					"SMS Based Voting System",
					0,
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_info.png") );

				if (reply==0)
				{
					querySchedule.deleteSchedule(str_rem);
					
					if (lst_schedule.getSelectedIndex()>0) 
						lst_schedule.setSelectedIndex(0);
					else
					{
						if (lst_schedule.getMaxSelectionIndex()!=0)
							lst_schedule.setSelectedIndex(lst_schedule.getSelectedIndex()+1);	
					}
					
			    	listModel.removeElement(str_rem);

					if (listModel.isEmpty())
					{
						l_a[15].setVisible(true);
										
						for(int i=0;i<=14;i++)
						{
							l_a[i].setText("");
						}

						l_votename.setVisible(false);
						l_startdate.setVisible(false);
						l_enddate.setVisible(false);
						l_starttime.setVisible(false);
						l_endtime.setVisible(false);
						l_candlist.setVisible(false);
					}
				}
			}			
		}
		else if(ae.getActionCommand().equals("change"))
		{
			if (lst_schedule.isSelectionEmpty())
			{
				JOptionPane.showMessageDialog(
					new JFrame(),
					"You must select a schedule !",
					"SMS Based Voting System",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon("images/icon_error.png") );
			}
			else
			{
				EditSchedule CS = new EditSchedule((lst_schedule.getSelectedValue()).toString(),l_a);
			}
		}
	}
}

class scheduleLayout implements LayoutManager 
{
    public scheduleLayout()
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
        dim.width = 795 + insets.left + insets.right;
        dim.height = 552 + insets.top + insets.bottom;

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

        parent.getComponent(0).setBounds(insets.left+24,insets.top+20,120,24);
        parent.getComponent(1).setBounds(insets.left+234,insets.top+484,102,24);
        parent.getComponent(2).setBounds(insets.left+348,insets.top+484,102,24);
        parent.getComponent(3).setBounds(insets.left+462,insets.top+484,102,24);
		//
		parent.getComponent(4).setBounds(insets.left+330,insets.top+20,90,24);
        parent.getComponent(5).setBounds(insets.left+330,insets.top+58,90,24);
        parent.getComponent(6).setBounds(insets.left+330,insets.top+120,90,24);
        parent.getComponent(7).setBounds(insets.left+330,insets.top+82,90,24);
        parent.getComponent(8).setBounds(insets.left+330,insets.top+144,90,24);
        parent.getComponent(9).setBounds(insets.left+330,insets.top+182,176,24);
		//a
        parent.getComponent(10).setBounds(insets.left+424,insets.top+20,312,24);
 	    parent.getComponent(11).setBounds(insets.left+424,insets.top+58,120,24);
        parent.getComponent(12).setBounds(insets.left+424,insets.top+82,104,24);
        parent.getComponent(13).setBounds(insets.left+424,insets.top+120,120,24);
        parent.getComponent(14).setBounds(insets.left+424,insets.top+144,104,24);
        parent.getComponent(15).setBounds(insets.left+330,insets.top+208,224,24);
        parent.getComponent(16).setBounds(insets.left+330,insets.top+232,224,24);
        parent.getComponent(17).setBounds(insets.left+330,insets.top+256,224,24);
        parent.getComponent(18).setBounds(insets.left+330,insets.top+280,224,24);
        parent.getComponent(19).setBounds(insets.left+330,insets.top+304,224,24);
        parent.getComponent(20).setBounds(insets.left+330,insets.top+328,224,24);
        parent.getComponent(21).setBounds(insets.left+330,insets.top+352,224,24);
        parent.getComponent(22).setBounds(insets.left+330,insets.top+376,224,24);
        parent.getComponent(23).setBounds(insets.left+330,insets.top+400,224,24);
        parent.getComponent(24).setBounds(insets.left+330,insets.top+424,224,24);
        parent.getComponent(25).setBounds(insets.left+450,insets.top+220,312,24);
 	    //
        parent.getComponent(26).setBounds(insets.left+24,insets.top+50,280,420);
    }
}
