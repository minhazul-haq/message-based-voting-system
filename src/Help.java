//opens the help file and shows it in a JTextArea

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Help extends JFrame implements ActionListener
{
	private JTextArea ta_help;
    private JScrollPane sp_ta_help;
    private JButton b_ok;
    private String strHelp;
    
	public Help()
	{
		super("Help");
        setLayout(new helpLayout());

		try
		{
			File file = new File("data/help.dat");
			FileReader fileReader = new FileReader(file);
					
			char arrayHelp[] = new char[(int)(file.length())];
			fileReader.read(arrayHelp);
			strHelp = new String(arrayHelp);
			
			fileReader.close();
			
			ta_help = new JTextArea(strHelp);
	        ta_help.setEditable(false);
	        ta_help.setBackground(new Color(238,238,238));
            ta_help.setLineWrap(true);
            ta_help.setWrapStyleWord(true);
            ta_help.setSelectionColor(new Color(238,238,238));
            add(ta_help);
						
	        b_ok = new JButton("    OK    ");
	        b_ok.setActionCommand("ok");
			b_ok.addActionListener(this);
	        add(b_ok);
	
			setIconImage((new ImageIcon("images/icon_help.png")).getImage());
			setSize(404,340);
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error in opening the help file !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("ok"))
		{
			setVisible(false);
		}
	}	
}

class helpLayout implements LayoutManager 
{
    public helpLayout()
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
        dim.width = 391 + insets.left + insets.right;
        dim.height = 310 + insets.top + insets.bottom;

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

        parent.getComponent(0).setBounds(insets.left+4,insets.top+2,390,266);
		parent.getComponent(1).setBounds(insets.left+152,insets.top+272,100,24);
    }
}
