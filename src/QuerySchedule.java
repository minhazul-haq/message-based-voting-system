//class for querying everything related to schedule portion

import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class QuerySchedule extends TimeManager
{
	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://localhost/smsvoting";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private int i,totalVote=0;
	
	public QuerySchedule()
	{
		try
 		{
 			Class.forName( DRIVER ); 			
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 		
	}

	public void getSchdeduleDataForLabel(JLabel[] lbl, String s)
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_name,starting_date,starting_time,"+
			"ending_date,ending_time,cand1_name,cand2_name,cand3_name,cand4_name,"+
			"cand5_name,cand6_name,cand7_name,cand8_name,cand9_name,cand10_name "+
			"from schedule where vote_name='"+s+"'");

			while( resultSet.next() )
			{
				lbl[0].setText(resultSet.getObject(1).toString());
				lbl[1].setText(toDate(resultSet.getObject(2).toString()));
				lbl[2].setText(toTime(resultSet.getObject(3).toString()));
				lbl[3].setText(toDate(resultSet.getObject(4).toString()));
				lbl[4].setText(toTime(resultSet.getObject(5).toString()));

				for(i=0;i<10;i++)
				{
					if (resultSet.getObject(i+6).toString().equals("-"))
						lbl[i+5].setText("");
					else
					 	lbl[i+5].setText(Integer.toString(i+1)+". " +resultSet.getObject(i+6).toString());
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error while extracting data for schedule !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}

	public void createScheduleList(DefaultListModel lm)
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_name,starting_date," +
				"starting_time from schedule order by vote_id");

			String strDate = new String();

			Date date = new Date();
			strDate = date.toString();

			while( resultSet.next() )
			{
				String s1=new String();
				String s2=new String();

				s1 += resultSet.getObject(2);
				s2 += resultSet.getObject(3);

				if ( (javaDatetoInt(strDate) < sqlDatetoInt(s1)) || ( (javaDatetoInt(strDate) == sqlDatetoInt(s1)) && (javaTimetoInt(strDate) < sqlTimetoInt(s2)) ) )
				{
					lm.addElement(resultSet.getObject(1));
					totalVote++;
				}
			}
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error while creating schedule list !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}

	public void createNewSchedule(JTextField[] tf,JComboBox[] cb)
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_id from schedule order by vote_id");

			int vote_id=0,total_cand=10;
			String s_date,s_time,e_date,e_time;

			s_date = new String();
			s_time = new String();
			e_date = new String();
			e_time = new String();

			s_date = cb[2].getSelectedItem()  + "-" + cb[1].getSelectedItem()  + "-" + cb[0].getSelectedItem();
			s_time = cb[3].getSelectedItem()  + ":" + cb[4].getSelectedItem()  + ":" + cb[5].getSelectedItem();

			e_date = cb[8].getSelectedItem()  + "-" + cb[7].getSelectedItem()  + "-" + cb[6].getSelectedItem();
			e_time = cb[9].getSelectedItem()  + ":" + cb[10].getSelectedItem()  + ":" + cb[11].getSelectedItem();

			while( resultSet.next() )
			{
				vote_id = Integer.parseInt(resultSet.getObject(1).toString());
			}

			vote_id++;

			for(i=1;i<=20;i++)
			{
				if (tf[i].getText().equals(""))
				{
					if (i>=1 && i<=10) total_cand--;
					tf[i].setText("-");
				}
			}

			int update = statement.executeUpdate("INSERT schedule VALUES("+vote_id+",'"+tf[0].getText()+
			"','"+ s_date +"','"+ s_time +"','"+ e_date +"','"+	e_time +"',"+
			total_cand+",'"+tf[1].getText()+"','"+tf[11].getText()+"','"+tf[2].getText()+"','"+
			tf[12].getText()+"','"+tf[3].getText()+"','"+tf[13].getText()+"','"+tf[4].getText()+
			"','"+tf[14].getText()+"','"+tf[5].getText()+"','"+tf[15].getText()+"','"+tf[6].getText()+
			"','"+tf[16].getText()+"','"+tf[7].getText()+"','"+tf[17].getText()+"','"+tf[8].getText()+
			"','"+tf[18].getText()+"','"+tf[9].getText()+"','"+tf[19].getText()+"','"+tf[10].getText()+
			"','"+tf[20].getText()+"')");

			JOptionPane.showMessageDialog(
				new JFrame(),
				"A new schdeule has been created",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_ok.png") );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error occured while creating a new schedule ",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}

	public int totalSchedules()
	{
		return totalVote;
	}
	
	public void deleteSchedule(String voteName)
	{
		try
 		{
 			statement.executeUpdate("DELETE from schedule where vote_name='" + voteName + "'");

			JOptionPane.showMessageDialog(
				new JFrame(),
				voteName + " has been deleted from sechedule",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_ok.png") );
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error while deleting schedule !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}
	
	public void getSchdeduleDataForTextField(JTextField[] tf,String s)
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_name,starting_date,starting_time,"+
			"ending_date,ending_time,cand1_name,cand2_name,cand3_name,cand4_name,cand5_name,"+
			"cand6_name,cand7_name,cand8_name,cand9_name,cand10_name,cand1_shortname,cand2_shortname,"+
			"cand3_shortname,cand4_shortname,cand5_shortname,cand6_shortname,cand7_shortname,"+
			"cand8_shortname,cand9_shortname,cand10_shortname from schedule where vote_name='"+s+"'");

			while( resultSet.next() )
			{
				tf[0].setText(resultSet.getObject(1).toString());

				tf[1].setText( toDate(resultSet.getObject(2).toString()) );
				tf[2].setText( resultSet.getObject(3).toString() );
				tf[3].setText( toDate(resultSet.getObject(4).toString()) );
				tf[4].setText( resultSet.getObject(5).toString() );

				for(i=5;i<=24;i++)
				{
					tf[i].setText(resultSet.getObject(i+1).toString());
				}
			}
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error occured while extracting data for this schedule !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}

	public void saveScheduleData(JTextField[] tf)
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_id from schedule where vote_name='"+
					tf[0].getText()+"'");

			int total_cand=10,vote_id=0;

			while( resultSet.next() )
			{
				vote_id = Integer.parseInt(resultSet.getObject(1).toString());
			}

			for(i=5;i<=24;i++)
			{
				if ((tf[i].getText().equals("")) || (tf[i].getText().equals("-")))
				{
					if (i>=5 && i<=14) total_cand--;
					tf[i].setText("-");
				}
			}

			int update = statement.executeUpdate("update schedule set starting_date='"+dateToSqlDate(tf[1].getText())+
			"',starting_time='"+tf[2].getText()+"',ending_date='"+dateToSqlDate(tf[3].getText())+"',ending_time='"+
			tf[4].getText()+"',total_cand="+total_cand+",cand1_name='"+tf[5].getText()+"',cand1_shortname='"+tf[15].getText()+
			"',cand2_name='"+tf[6].getText()+"',cand2_shortname='"+tf[16].getText()+"',cand3_name='"+
			tf[7].getText()+"',cand3_shortname='"+tf[17].getText()+"',cand4_name='"+tf[8].getText()+
			"',cand4_shortname='"+tf[18].getText()+"',cand5_name='"+tf[9].getText()+"',cand5_shortname='"+
			tf[19].getText()+"',cand6_name='"+tf[10].getText()+"',cand6_shortname='"+tf[20].getText()+
			"',cand7_name='"+tf[11].getText()+"',cand7_shortname='"+tf[21].getText()+"',cand8_name='"+
			tf[12].getText()+"',cand8_shortname='"+tf[22].getText()+"',cand9_name='"+tf[13].getText()+
			"',cand9_shortname='"+tf[23].getText()+"',cand10_name='"+tf[14].getText()+"',cand10_shortname='"+
			tf[24].getText()+"' where vote_id="+vote_id);

			JOptionPane.showMessageDialog(
				new JFrame(),
				"Schedule has been changed.",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_ok.png") );
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog(
				new JFrame(),
				"Error occured while saving schedule !",
				"SMS Based Voting System",
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images/icon_error.png") );
		}
	}

	@Override
	public void finalize() //default destructor
	{
		try
		{
			resultSet.close();
			statement.close();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}