//class for querying everything in archive portion

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;

public class QueryArchive extends TimeManager
{
	private String DRIVER = "com.mysql.jdbc.Driver";	
	private String DATABASE_URL = "jdbc:mysql://localhost/smsvoting";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private Archive archive;
	private CandidateData CD[],temp;
	private JTable table;
	private JScrollPane sp_table;
	private String[] tableHeads = {" Sender :"," Date :"," Time :"," Vote :"};
	private String[][] tableData;
	
	public QueryArchive(Archive arch) //default constructor
	{
		try
 		{
 			archive = arch;
 			Class.forName( DRIVER ); 			
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}

	public void addAllToPanel()
	{
		try
 		{
 			if (archive.c_vote.getItemCount()>0)
			{
				archive.l_allMessage.setText("All message for " +
					archive.c_vote.getSelectedItem().toString() + " :");
					
				resultSet = statement.executeQuery("SELECT vote_id from schedule where vote_name='" +
					archive.c_vote.getSelectedItem().toString() + "'");
			
				int vote_ID=0,total_vote=0,total_cand=0,i,j,cand;

				resultSet.first();
				vote_ID = Integer.parseInt(resultSet.getObject(1).toString());

				resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms " +
					"where vote_id='" + vote_ID + "' order by date,time");

				int row=0;

				resultSet.last();

				tableData = new String[resultSet.getRow()][4];

				resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms " +
					"where vote_id='" + vote_ID + "' order by date,time");

				while(resultSet.next())
				{
					tableData[row][0] = resultSet.getObject(1).toString();
					tableData[row][1] = toDate(resultSet.getObject(2).toString());
					tableData[row][2] = toTime(resultSet.getObject(3).toString());
					tableData[row][3] = resultSet.getObject(4).toString();

					total_vote++;
					row++;
				}

				archive.p_smsArchive.removeAll();
				table = new JTable(tableData,tableHeads);
				table.setEnabled(false);
				table.setRowMargin(5);
				((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
				sp_table = new JScrollPane(table);
				archive.p_smsArchive.add(sp_table);

				archive.ta_summary[0].setText("");
				archive.ta_summary[1].setText("");

				resultSet = statement.executeQuery("SELECT total_cand from schedule " +
					"where vote_id='" + vote_ID + "'");

				resultSet.first();
				total_cand = Integer.parseInt(resultSet.getObject(1).toString()) ;

				CD = new CandidateData[10];

				for(cand=1;cand<=10;cand++)
				{
					resultSet = statement.executeQuery("SELECT cand" + cand + "_name,cand" +
						cand + "_shortname from schedule where vote_id='" + vote_ID + "'");

					resultSet.first();
					CD[cand-1] = new CandidateData();
					CD[cand-1].setName(resultSet.getObject(1).toString(),resultSet.getObject(2).toString());

					if (cand-1<total_cand)
					{
						String name = CD[cand-1].shortName;

						resultSet = statement.executeQuery("SELECT vote from sms where "
							+ "vote_id='" + vote_ID + "' and vote='" + name + "'");

						resultSet.last();
						CD[cand-1].vote = resultSet.getRow();
					}
				}

				temp = new CandidateData();

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

				for(i=0;i<10;i++)
				{
					if (i<total_cand)
					{
						archive.ta_summary[0].append(CD[i].fullName + "\n");
						archive.ta_summary[1].append(CD[i].vote + "\n");
					}

					archive.graphName[i] = CD[i].fullName;
					archive.graphVote[i] = CD[i].vote;
				}

				archive.l_data[0].setText(Integer.toString(total_vote));
				archive.l_data[1].setText(Integer.toString(total_cand));
				archive.l_data[2].setText(Integer.toString(CD[0].vote));
				archive.l_data[3].setText(CD[0].fullName);
			}
			else
			{
				 archive.b_reject.setVisible(false);
				 archive.b_graph.setVisible(false);
				 archive.l_allMessage.setVisible(false);
				 archive.l_select.setVisible(false);
				 archive.l_totalVote.setVisible(false);
				 archive.l_totalCand.setVisible(false);
				 archive.l_maxVote.setVisible(false);
				 archive.l_winner.setVisible(false);
				 archive.l_candVote.setVisible(false);
				 archive.c_vote.setVisible(false);
				 archive.l_noVote.setVisible(true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}	
	
	public void addItemToCombo()
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_name,ending_date,ending_time from schedule order by vote_id");
			
			while(resultSet.next())
			{
				String s1 = new String(resultSet.getObject(2).toString());
				String s2 = new String(resultSet.getObject(3).toString());

				if ( (isDatePast(s1)) || (isDateToday(s1) && isTimePast(s2)) ) 
					archive.c_vote.addItem(resultSet.getObject(1));
			} 
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public void rejectSMS()
	{
		try
 		{
 			resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id=0 order by date,time");

			resultSet.last();

			String rejectedData[][] = new String[resultSet.getRow()][4];

			int row=0;
			resultSet = statement.executeQuery("SELECT sender,date,time,vote from sms where vote_id=0 order by date,time");

			while(resultSet.next())
			{
				rejectedData[row][0] = resultSet.getObject(1).toString();
				rejectedData[row][1] = toDate(resultSet.getObject(2).toString());
				rejectedData[row][2] = toTime(resultSet.getObject(3).toString());
				rejectedData[row][3] = resultSet.getObject(4).toString();

				row++;
			}
			
			RejectSms rs = new RejectSms(rejectedData);
       	}	
		catch(Exception e)
		{
			e.printStackTrace();
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