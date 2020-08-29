//ongoing class that contains everything of ongoing vote portion

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ongoing implements ActionListener,Runnable
{
	private JLabel l_noVote,l_start1,l_start2,l_end1,l_end2,l_summary,l_votename,l_sort;
    private JButton b_vote,b_sender,b_time;
    private JTextArea ta_summaryName,ta_summaryVote;
    private QueryOngoing queryOngoing;
	private JPanel p_smsOngoing;
	private Thread threadOngoing;
	public static boolean newSms=false;

	public Ongoing(JPanel pnl)
	{
		threadOngoing = new Thread(this);
		
		pnl.setLayout(new ongoingLayout());

		queryOngoing = new QueryOngoing();

        l_start1 = new JLabel("Starting date & time :");
        l_start1.setFont(new Font("Arial",Font.PLAIN,12));
        pnl.add(l_start1);

        l_start2 = new JLabel("-");
        l_start2.setFont(new Font("Arial",Font.PLAIN,12));
        pnl.add(l_start2);

		l_end1 = new JLabel("Ending date & time :");
        l_end1.setFont(new Font("Arial",Font.PLAIN,12));
        pnl.add(l_end1);

        l_end2 = new JLabel("-");
        l_end2.setFont(new Font("Arial",Font.PLAIN,12));
        pnl.add(l_end2);

		l_summary = new JLabel("Result summary :");
        l_summary.setFont(new Font("Arial",Font.BOLD,13));
        pnl.add(l_summary);

        l_votename = new JLabel("");
        l_votename.setFont(new Font("Arial",Font.BOLD,12));
        pnl.add(l_votename);

		p_smsOngoing = new JPanel();
		p_smsOngoing.setLayout(new BorderLayout());
		queryOngoing.createSmsList(p_smsOngoing,l_votename,1);
		queryOngoing.createDateTime(l_start2,l_end2);
		pnl.add(p_smsOngoing);

        l_sort = new JLabel("Sort SMS list by");
        l_sort.setFont(new Font("Arial",Font.BOLD,12));
        pnl.add(l_sort);

        b_vote = new JButton("Vote");
		b_vote.setActionCommand("vote");
		b_vote.addActionListener(this);
        pnl.add(b_vote);

        b_sender = new JButton("Sender");
		b_sender.setActionCommand("sender");
        b_sender.addActionListener(this);
        pnl.add(b_sender);

        b_time = new JButton("Time");
		b_time.setActionCommand("time");
        b_time.addActionListener(this);
        pnl.add(b_time);

		l_noVote = new JLabel("No vote is running now");
		l_noVote.setFont(new Font("Georgia",Font.BOLD,14));
		pnl.add(l_noVote);

		ta_summaryName = new JTextArea("");
		ta_summaryName.setFont(new Font("Arial",Font.PLAIN,13));
		ta_summaryName.setEditable(false);
		ta_summaryName.setBackground(new Color(238,238,238));
        ta_summaryName.setSelectionColor(new Color(238,238,238));

		ta_summaryVote = new JTextArea("");
		ta_summaryVote.setFont(new Font("Arial",Font.PLAIN,13));
		ta_summaryVote.setEditable(false);
		ta_summaryVote.setBackground(new Color(238,238,238));
        ta_summaryVote.setSelectionColor(new Color(238,238,238));
		
		if (queryOngoing.running_vote!=0)
        {
        	l_start1.setVisible(true);
			l_start2.setVisible(true);
			l_end1.setVisible(true);
			l_end2.setVisible(true);
			l_summary.setVisible(true);
			l_votename.setVisible(true);
			l_sort.setVisible(true);
			b_vote.setVisible(true);
			b_sender.setVisible(true);
			b_time.setVisible(true);
			ta_summaryName.setVisible(true);
			ta_summaryVote.setVisible(true);
			p_smsOngoing.setVisible(true);
			l_noVote.setVisible(false);
			queryOngoing.createRanklist(ta_summaryName,ta_summaryVote);
        }
		else
		{
			l_start1.setVisible(false);
			l_start2.setVisible(false);
			l_end1.setVisible(false);
			l_end2.setVisible(false);
			l_summary.setVisible(false);
			l_votename.setVisible(false);
			l_sort.setVisible(false);
			b_vote.setVisible(false);
			b_sender.setVisible(false);
			b_time.setVisible(false);
			ta_summaryName.setVisible(false);
			ta_summaryVote.setVisible(false);
			l_noVote.setVisible(true);
			p_smsOngoing.setVisible(false);
		}

		pnl.add(ta_summaryName);
		pnl.add(ta_summaryVote);

		threadOngoing.start();
	}	
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("vote"))
		{
			queryOngoing.createSmsList(p_smsOngoing,l_votename,3);
			p_smsOngoing.validate();
			//queryOngoing.createRanklist(ta_summaryName,ta_summaryVote);
		}
		else if(ae.getActionCommand().equals("sender"))
		{
			queryOngoing.createSmsList(p_smsOngoing,l_votename,2);
			p_smsOngoing.validate();
			queryOngoing.createRanklist(ta_summaryName,ta_summaryVote);
		}
		else if(ae.getActionCommand().equals("time"))
		{
			queryOngoing.createSmsList(p_smsOngoing,l_votename,1);
			p_smsOngoing.validate();
			queryOngoing.createRanklist(ta_summaryName,ta_summaryVote);
		}
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				if (newSms)
				{
					if (queryOngoing.running_vote!=0)
					{
						queryOngoing.createSmsList(p_smsOngoing,l_votename,1);
						p_smsOngoing.validate();
						queryOngoing.createRanklist(ta_summaryName,ta_summaryVote);
					}
					newSms=false;
				}

				Thread.sleep(1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class ongoingLayout implements LayoutManager
{
    public ongoingLayout()
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

        parent.getComponent(0).setBounds(insets.left+480,insets.top+396,120,24);
        parent.getComponent(1).setBounds(insets.left+600,insets.top+396,160,24);
        parent.getComponent(2).setBounds(insets.left+480,insets.top+421,120,24);
        parent.getComponent(3).setBounds(insets.left+600,insets.top+421,160,24);
        
		parent.getComponent(4).setBounds(insets.left+480,insets.top+12,144,24);
        parent.getComponent(5).setBounds(insets.left+16,insets.top+12,200,24);
        parent.getComponent(6).setBounds(insets.left+16,insets.top+42,448,470);
        parent.getComponent(7).setBounds(insets.left+580,insets.top+458,140,24);

		parent.getComponent(8).setBounds(insets.left+480,insets.top+488,90,24);
        parent.getComponent(9).setBounds(insets.left+580,insets.top+488,90,24);
        parent.getComponent(10).setBounds(insets.left+680,insets.top+488,90,24);

		parent.getComponent(11).setBounds(insets.left+310,insets.top+42,300,410);
        parent.getComponent(12).setBounds(insets.left+480,insets.top+42,230,350);
        parent.getComponent(13).setBounds(insets.left+710,insets.top+42,40,350);
    }
}