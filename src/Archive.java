//class for showing contents in Archive portion

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Archive implements ActionListener
{
	public JLabel l_select,l_totalVote,l_totalCand,l_maxVote,l_winner,l_candVote,l_noVote;
	public JLabel l_allMessage,l_data[];
    public JButton b_reject,b_graph;
    public JTextArea ta_summary[];
    public JComboBox c_vote;
    public String graphName[];
    public int graphVote[];
    private QueryArchive queryArchive;
    public JPanel p_smsArchive;
			
    public Archive(JPanel pnl)
	{
		pnl.setLayout(new archiveLayout());

		l_noVote = new JLabel("No vote result in the archive");
		l_noVote.setFont(new Font("Georgia",Font.BOLD,14));
		l_noVote.setVisible(false);
		pnl.add(l_noVote);
		
		l_data = new JLabel[4];
		ta_summary = new JTextArea[2];
		graphName = new String[10];
        graphVote = new int[10];
        queryArchive = new QueryArchive(this);
		
        l_select = new JLabel("Please select a vote : ");
		l_select.setFont(new Font("Arial",Font.BOLD,12));
        pnl.add(l_select);

        l_totalVote = new JLabel("Total votes :");
        l_totalVote.setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_totalVote);

        l_data[0] = new JLabel("");
        l_data[0].setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_data[0]);

        l_totalCand = new JLabel("Total candidates :");
        l_totalCand.setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_totalCand);

        l_data[1] = new JLabel("");
        l_data[1].setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_data[1]);

        l_maxVote = new JLabel("Max. votes :");
        l_maxVote.setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_maxVote);

        l_data[2] = new JLabel("");
        l_data[2].setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_data[2]);

        l_candVote = new JLabel("Candidate-wise votes :");
        l_candVote.setFont(new Font("Arial",Font.BOLD,13));
        pnl.add(l_candVote);

		l_winner = new JLabel("Winner :");
        l_winner.setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_winner);

        l_data[3] = new JLabel("");
        l_data[3].setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_data[3]);

        b_reject = new JButton("Reject SMS",new ImageIcon("images/button_reject.png"));
		b_reject.setActionCommand("reject");
        b_reject.addActionListener(this);
        pnl.add(b_reject);
        
        b_graph = new JButton("  Graph  ",new ImageIcon("images/button_graph.png"));
        b_graph.setActionCommand("graph");
		b_graph.addActionListener(this);
        pnl.add(b_graph);

        l_allMessage = new JLabel("Please select a vote");
        l_allMessage.setFont(new Font("Arial",Font.PLAIN,13));
        pnl.add(l_allMessage);
        
        ta_summary[0] = new JTextArea("");
        ta_summary[0].setEditable(false);
        ta_summary[0].setFont(new Font("Arial",Font.PLAIN,13));
        ta_summary[0].setBackground(new Color(238,238,238));
        ta_summary[0].setSelectionColor(new Color(238,238,238));
        pnl.add(ta_summary[0]);
        
        ta_summary[1] = new JTextArea("");
		ta_summary[1].setEditable(false);
        ta_summary[1].setFont(new Font("Arial",Font.PLAIN,13));
        ta_summary[1].setBackground(new Color(238,238,238));
        ta_summary[1].setSelectionColor(new Color(238,238,238));
        pnl.add(ta_summary[1]);

		p_smsArchive = new JPanel();
		p_smsArchive.setLayout(new BorderLayout());
		pnl.add(p_smsArchive);

		c_vote = new JComboBox();
		c_vote.setFont(new Font("Arial",Font.PLAIN,12));
        queryArchive.addItemToCombo();
        
        c_vote.addItemListener
        (
        	new ItemListener()
        	{
				@Override
        		public void itemStateChanged(ItemEvent event)
        		{
        			if (event.getStateChange()==ItemEvent.SELECTED)
        			{
        				queryArchive.addAllToPanel();
        			}
        		}
        	}
        );
                
        queryArchive.addAllToPanel();
        pnl.add(c_vote);
    }
    
	@Override
    public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("reject"))
		{
			queryArchive.rejectSMS();
		}
		else if(ae.getActionCommand().equals("graph"))
		{
			Graph graph = new Graph(graphName,graphVote);
		}
	}
}

class archiveLayout implements LayoutManager 
{
	public archiveLayout() 
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

        parent.getComponent(0).setBounds(insets.left+300,insets.top+240,300,24);
        parent.getComponent(1).setBounds(insets.left+16,insets.top+10,168,24);
        parent.getComponent(2).setBounds(insets.left+16,insets.top+86,104,24);
        parent.getComponent(3).setBounds(insets.left+124,insets.top+86,88,24);
        parent.getComponent(4).setBounds(insets.left+16,insets.top+106,104,24);
        parent.getComponent(5).setBounds(insets.left+124,insets.top+106,88,24);
        parent.getComponent(6).setBounds(insets.left+16,insets.top+126,104,24);
        parent.getComponent(7).setBounds(insets.left+124,insets.top+126,88,24);
        parent.getComponent(8).setBounds(insets.left+16,insets.top+188,192,24);
        parent.getComponent(9).setBounds(insets.left+16,insets.top+146,104,24);
        parent.getComponent(10).setBounds(insets.left+70,insets.top+146,170,24);
        parent.getComponent(11).setBounds(insets.left+56,insets.top+485,128,24);
        parent.getComponent(12).setBounds(insets.left+56,insets.top+450,128,24);
        parent.getComponent(13).setBounds(insets.left+246,insets.top+10,350,24);
        parent.getComponent(14).setBounds(insets.left+16,insets.top+216,170,246);
        parent.getComponent(15).setBounds(insets.left+192,insets.top+216,40,246);
        parent.getComponent(16).setBounds(insets.left+244,insets.top+36,540,480);
		parent.getComponent(17).setBounds(insets.left+16,insets.top+36,220,24);
	}
}
