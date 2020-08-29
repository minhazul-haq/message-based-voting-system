import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RejectSms extends JFrame implements ActionListener
{
    private JButton b_ok;
    private JTable table;
	private JScrollPane sp_reject;
    private String[] tableHeads = {" Sender :"," Date :"," Time :"," Vote :"};

	public RejectSms(String[][] data)
	{
        super("All reject SMS");
        setLayout(new rejectSmsLayout());



		table = new JTable(data,tableHeads);
		table.setEnabled(false);
		table.setRowMargin(5);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
		sp_reject = new JScrollPane(table);
        add(sp_reject);

        b_ok = new JButton("   OK   ");
        b_ok.setActionCommand("ok");
		b_ok.addActionListener(this);
		add(b_ok);

		setIconImage((new ImageIcon("images/icon_reject.png")).getImage());
		setSize(350,340);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
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

class rejectSmsLayout implements LayoutManager
{
    public rejectSmsLayout()
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
        dim.width = 338 + insets.left + insets.right;
        dim.height = 319 + insets.top + insets.bottom;

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

        parent.getComponent(0).setBounds(insets.left+2,insets.top+2,341,266);
		parent.getComponent(1).setBounds(insets.left+128,insets.top+276,88,24);
    }
}