//creates graph based on the past voting results

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Graph extends JFrame implements ActionListener
{	
	private int[] vote;
	private String[] name;
	private JButton b_ok;

	public Graph(String[] n,int[] v)
	{
		super("Graphical representation");
		setLayout(new graphLayout());

		b_ok = new JButton("    OK    ");
		b_ok.setActionCommand("ok");
	    b_ok.addActionListener(this);
	    add(b_ok);

		vote = new int[10];
		name = new String[10];
		
		for(int i=0;i<10;i++)
		{
			vote[i] = v[i];	
			name[i] = n[i];
		}
		
		setIconImage((new ImageIcon("images/icon_graph.png")).getImage());
		setSize(500,370);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("ok"))
		{
			this.setVisible(false);
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int color[][] = { {65,105,225}  , {255,160,122} , {100,149,237} , {85,107,47} ,
						{238,232,170} , {218,165,32}  , {119,136,153} , {0,255,127} , 
						{205,133,63}  , {255,250,250} };
		
		int i,total=0;
		
		for(i=0;i<10;i++)
		{
			total += vote[i];			
		}
		
		if (total==0)
		{
			g.setFont(new Font("Times new roman",Font.PLAIN,20));
			g.setColor(new Color(0,0,0));
			g.drawString("Total vote 0 for this event",140,180);	
		}
		else
		{
			g.setColor(new Color(0,0,0));
			g.drawArc(27,49,251,251,0,360);
		
			int past=0;
			
			for(i=0;i<10;i++)
			{
				if (vote[i]!=0)
				{
					g.setFont(new Font("Times new roman",Font.PLAIN,12));
					g.setColor(new Color(0,0,0));
					g.drawString(name[i],330,100+(i*20));	
					
					g.setColor(new Color(color[i][0],color[i][1],color[i][2]));
					g.fillRect(300,87+(i*20),20,16);		
					
					if (i==0) g.fillArc(28,50,250,250,0,vote[i]*360/total);
					else 
					{
						past += vote[i-1];
							
						g.fillArc(28,50,250,250,past*360/total,vote[i]*360/total); 		
					}
				}
			}
		}
	}
}

class graphLayout implements LayoutManager
{
    public graphLayout()
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
        dim.width = 484 + insets.left + insets.right;
        dim.height = 339 + insets.top + insets.bottom;

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

        parent.getComponent(0).setBounds(insets.left+194,insets.top+304,100,24);
    }
}
