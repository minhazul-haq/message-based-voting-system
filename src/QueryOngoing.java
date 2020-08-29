//class for querying all related to ongoing portion

import javax.swing.*;
import java.sql.*;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

public class QueryOngoing extends TimeManager implements Runnable
{
	private String DRIVER = "com.mysql.jdbc.Driver";
	private String DATABASE_URL = "jdbc:mysql://localhost/smsvoting";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet=null,resultSetForThread=null;
	public int running_vote=0;
	private JTable t_smsList;
	private JScrollPane sp_table;
	private String[] tableHeads = {" Sender :"," Date :"," Time :"," Vote :"};
	private int i,intRunning=0,temp;
	private Thread threadQueryOngoing;
	public static int isThreadRunning=0;

	public QueryOngoing() //default constructor
	{
		try
 		{
			Class.forName( DRIVER );
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();
			threadQueryOngoing = new Thread(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createSmsList(JPanel panel, JLabel lvn, int sortHow)
	{
		running_vote=0;
		String s1=new String();
		String s2=new String();
		String s3,s4,s5,s6;
		String strDate = new String();

		try
 		{
			Class.forName( DRIVER );
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT vote_name,vote_id,starting_date,starting_time,ending_date,ending_time from schedule");
			Date date = new Date();
			strDate = date.toString();

			while( resultSet.next() )
			{
				s3=new String();
				s4=new String();
				s5=new String();
				s6=new String();

				s3 += resultSet.getObject(3);
				s4 += resultSet.getObject(4);
				s5 += resultSet.getObject(5);
				s6 += resultSet.getObject(6);

				boolean a=false,b=false,c=false,d=false;

				a = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
				b = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );
				c = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
				d = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );

				if (a || b || c || d)
				{
					s1 += resultSet.getObject(2);
					running_vote = Integer.parseInt(s1);
					intRunning = running_vote;
					s2 += resultSet.getObject(1);
					lvn.setText(s2);
					break;
				}
			}

			if (running_vote == 0)
			{
				lvn.setText("No vote is running now");

				String tableData[][] = new String[0][4];

				panel.removeAll();
				t_smsList = new JTable(tableData,tableHeads);
				t_smsList.setEnabled(false);
				t_smsList.setRowMargin(5);
				((DefaultTableCellRenderer)t_smsList.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
				sp_table = new JScrollPane(t_smsList);
				panel.add(sp_table);
			}
			else
			{
				if (sortHow==1) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by date,time");
				else if (sortHow==2) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by sender");
				else if (sortHow==3) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by vote");

				resultSet.last();

				String tableData[][] = new String[resultSet.getRow()][4];
				int row=0;

				if (sortHow==1) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by date,time");
				else if (sortHow==2) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by sender");
				else if (sortHow==3) resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id="+running_vote+" order by vote");

				if (running_vote!=0)
				{
					while( resultSet.next() )
					{
						tableData[row][0] = resultSet.getObject(1).toString();
						tableData[row][1] = toDate(resultSet.getObject(2).toString());
						tableData[row][2] = toTime(resultSet.getObject(3).toString());
						tableData[row][3] = resultSet.getObject(4).toString();

						row++;
					}
				}

				panel.removeAll();
				t_smsList = new JTable(tableData,tableHeads);
				t_smsList.setEnabled(false);
				t_smsList.setRowMargin(5);
				((DefaultTableCellRenderer)t_smsList.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
				sp_table = new JScrollPane(t_smsList);
				panel.add(sp_table);
			}

			if (isThreadRunning==0)
			{
				isThreadRunning=1;
				threadQueryOngoing.start();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void createDateTime(JLabel l_start,JLabel l_end)
	{
		try
		{
			Class.forName( DRIVER );
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT starting_date,starting_time,ending_date,ending_time from schedule where vote_id="+running_vote);

			while(resultSet.next())
			{
				l_start.setText( toDate(resultSet.getObject(1).toString()) + "  -  " +
								  toTime(resultSet.getObject(2).toString()) );

				l_end.setText( toDate(resultSet.getObject(3).toString()) + "  -  " +
								toTime(resultSet.getObject(4).toString()) );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void createRanklist(JTextArea ta_cand,JTextArea ta_vote)
	{
		try
 		{
 			Class.forName( DRIVER );
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT sender from sms where vote_id="+running_vote);
			ta_cand.setText("");
			ta_vote.setText("");

			int total_vote=0;

			while( resultSet.next() )
			{
			    total_vote++;
			}

			//now for candidates achieved votes

			resultSet = statement.executeQuery("SELECT total_cand from schedule where vote_id="+running_vote);

			int total_cand=0;

			while( resultSet.next() )
			{
				total_cand = Integer.parseInt(resultSet.getObject(1).toString()) ;
			}

			CandidateData CD[] = new CandidateData[10];

			for(i=0;i<=9;i++)
			{
				resultSet = statement.executeQuery("SELECT cand" + (i+1) + "_name,cand" + (i+1) +
					"_shortname from schedule where vote_id=" + running_vote);
				
				while( resultSet.next() )
				{
					CD[i] = new CandidateData();
					CD[i].setName(resultSet.getObject(1).toString(),resultSet.getObject(2).toString());
				}
			}

			for(i=0;i<total_cand;i++)
			{
				String s_name = CD[i].shortName;
				
				resultSet = statement.executeQuery("SELECT vote from sms where vote_id=" +running_vote+ " and vote='" + s_name + "'");

				while( resultSet.next() )
				{
					CD[i].vote++;
				}
			}

			int j;
			CandidateData temp = new CandidateData();

			for(i=0;i<total_cand-1;i++)
			{
				for(j=i+1;j<total_cand;j++)
				{
					if ( CD[j].vote > CD[i].vote )
					{
						temp = CD[j];
						CD[j] = CD[i];
						CD[i] = temp;
					}
				}
			}

			for(i=0;i<total_cand;i++)
			{
				if(i==total_cand-1)
				{
					ta_cand.append(CD[i].fullName + "\n");
					ta_vote.append("    " + CD[i].vote + "\n");
				}
				else
				{
					ta_cand.append(CD[i].fullName + "\n\n");
					ta_vote.append("    " + CD[i].vote + "\n\n");
				}
			}

			ta_cand.append("____________________________________\n\nTotal votes : ");
			ta_vote.append("____________\n\n    "+Integer.toString(total_vote));
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public int running()
	{
		return running_vote;
	}


	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				Class.forName( DRIVER );
				connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
				statement = connection.createStatement();

				temp=0;

				resultSetForThread = statement.executeQuery("SELECT vote_name,vote_id,"+
					"starting_date,starting_time,ending_date,ending_time from schedule");
				Date date = new Date();
				String strDate = date.toString();

				while( resultSetForThread.next() )
				{
					String s3=new String();
					String s4=new String();
					String s5=new String();
					String s6=new String();

					s3 = resultSetForThread.getObject(3).toString();
					s4 = resultSetForThread.getObject(4).toString();
					s5 = resultSetForThread.getObject(5).toString();
					s6 = resultSetForThread.getObject(6).toString();

					boolean a=false,b=false,c=false,d=false;

					a = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
					b = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );
					c = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
					d = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );

					if (a || b || c || d)
					{
						String strRunning = resultSetForThread.getObject(2).toString();
						temp = Integer.parseInt(strRunning);
						break;
					}
				}

				if (temp!=intRunning)
				{
					SmsVotingScreen.reload = true;
					System.out.println("It's time to reload");
					intRunning = temp;
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

