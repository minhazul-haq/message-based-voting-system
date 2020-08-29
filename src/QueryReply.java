//class for querying answer when a message arrives

import java.sql.*;
import java.util.Date;

public class QueryReply extends TimeManager
{	
	int running_vote_id=0;
	private int i;
			
	static final String DRIVER = "com.mysql.jdbc.Driver";	
	static final String DATABASE_URL = "jdbc:mysql://localhost/smsvoting";

	private Connection connection = null; 	// manages connection
	private Statement statement = null; 	// query statement
	private ResultSet resultSet = null; 	// manages results
	
	private Date date = new Date();
	private String strDate = new String(date.toString());
			
	public QueryReply()
	{
		try
 		{
 			Class.forName( DRIVER );
			connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT vote_name,vote_id,starting_date," +
				"starting_time,ending_date,ending_time from schedule");
			
			while( resultSet.next() )
			{
				String s3=new String(resultSet.getObject(3).toString());
				String s4=new String(resultSet.getObject(4).toString());
				String s5=new String(resultSet.getObject(5).toString());
				String s6=new String(resultSet.getObject(6).toString());
			
				boolean a=false,b=false,c=false,d=false;
				
				a = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
				b = ( javaDatetoInt(strDate) > sqlDatetoInt(s3) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );
				c = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) < sqlDatetoInt(s5) );
				d = ( javaDatetoInt(strDate) == sqlDatetoInt(s3) ) && ( javaTimetoInt(strDate) >= sqlTimetoInt(s4) ) && ( javaDatetoInt(strDate) == sqlDatetoInt(s5) ) && ( javaTimetoInt(strDate) <= sqlTimetoInt(s6) );
				
				if (a || b || c || d)
				{
					running_vote_id = Integer.parseInt(resultSet.getObject(2).toString());
					break;	
				}
			}
		}	
		catch( Exception e )
		{
			e.printStackTrace();
		} 
	}

	public String smsInfo()
	{
		String reply = new String();
			
		try
 		{
 			if (running_vote_id == 0) 
			{
				reply="Sorry, no vote is running now";	
			}
			else
			{
				resultSet = statement.executeQuery("SELECT vote_name,cand1_shortname,cand2_shortname,"+
				"cand3_shortname,cand4_shortname,cand5_shortname,cand6_shortname,cand7_shortname,"+
				"cand8_shortname,cand9_shortname,cand10_shortname from schedule "+
				"where vote_id="+running_vote_id);
			
				while( resultSet.next() )
				{
					reply += resultSet.getObject(1).toString() + " is running now.Candidates: ";		
					
					for(i=2;i<=11;i++)
					{
						if ( !(resultSet.getObject(i).toString()).equals("-") )
						{
							if (i==2) reply += resultSet.getObject(i).toString();
							else reply += "," + resultSet.getObject(i).toString();
						}		
					}
				}
			}
			
			reply += ".";
		}	
		catch( Exception e)
		{
			e.printStackTrace();
		}
		
		return reply;
	}
	
	public String smsResult()
	{
		String reply = new String();
		String voteName = new String();
		
		try
 		{
 			resultSet = statement.executeQuery("SELECT vote_id,ending_date,ending_time," +
				"total_cand,vote_name from schedule order by vote_id");
			
			int vote_id=0,total_cand=0;
			
			while( resultSet.next() )
			{
				String s1=new String(resultSet.getObject(2).toString());
				String s2=new String(resultSet.getObject(3).toString());
				
				if ( (javaDatetoInt(strDate)>sqlDatetoInt(s1)) || ( (javaDatetoInt(strDate)==sqlDatetoInt(s1)) && (javaTimetoInt(strDate)>sqlTimetoInt(s2)) ) )
				{
					vote_id = Integer.parseInt(resultSet.getObject(1).toString());
					total_cand = Integer.parseInt(resultSet.getObject(4).toString()) ;	
					voteName = 	resultSet.getObject(5).toString();		
				}
			}
						
			CandidateData CD[] = new CandidateData[10];

			for(i=0;i<=9;i++)
			{
				resultSet = statement.executeQuery("SELECT cand" + (i+1) +
					"_shortname from schedule where vote_id="+vote_id);

				while( resultSet.next() )
				{
					CD[i] = new CandidateData();
					CD[i].shortName = resultSet.getObject(1).toString();
				}
			}
			
			for(i=0;i<total_cand;i++)
			{
				String s_name = CD[i].shortName;
				
				resultSet = statement.executeQuery("SELECT vote from sms where vote_id=" + vote_id +
					" and vote='" + s_name + "'");
				
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
			
			reply += "Result for " + voteName + ": "; 
						
			for(i=0;i<total_cand;i++)
			{
				if (i==0) reply += CD[i].nameVoteForSms();
				else reply += "," + CD[i].nameVoteForSms();
			}
			
			reply += ".";
		}	
		catch( Exception e )
		{
			e.printStackTrace();
		} 
		
		return reply;
	}
	
	public String smsVote(String sender,String vote)
	{
		String reply = new String();
		
		if (running_vote_id==0)
			reply = "Sorry, no vote is running now.";
		else
		{
			try
	 		{
	 			resultSet = statement.executeQuery("SELECT cand1_shortname,cand2_shortname,"+
				"cand3_shortname,cand4_shortname,cand5_shortname,cand6_shortname,cand7_shortname,"+
				"cand8_shortname,cand9_shortname,cand10_shortname from schedule "+
				"where vote_id="+running_vote_id);
				
				int legal=3;
				
				while( resultSet.next() )
				{
					for(i=1;i<=10;i++)
					{
						if ((resultSet.getObject(i).toString()).equals(vote))
						{
							legal=1;
							break;
						}		
					}
				}
				
				if (legal!=3)
				{
					resultSet = statement.executeQuery("SELECT sender from sms where vote_id="+running_vote_id);
					
					while( resultSet.next() )
					{
						if ((resultSet.getObject(1).toString()).equals(sender))
						{
							legal=2;
							break;
						}
					}
				}

				TimeManager t = new TimeManager();

                Date now = new Date();
                String strNow = new String(now.toString());
				
				if (legal==1)
				{
					int update = statement.executeUpdate("INSERT sms VALUES(" + running_vote_id+ ",'" +
					sender + "','" + javaDatetoSql(strNow) + "','" + javaTimetoSql(strNow) + "','" + vote + "')");

					resultSet = statement.executeQuery("SELECT ending_date,ending_time from schedule" +
						" where vote_id="+running_vote_id);

					while(resultSet.next())
					{
						reply = "Your vote has been cast.Type \"result\" & send to know the result after " +
							toTime(resultSet.getObject(2).toString()) + " @ " + toDate(resultSet.getObject(1).toString());
					}
				}
				else if (legal==2)
				{
					int update = statement.executeUpdate("INSERT sms VALUES(" + 0 + ",'" +
					sender + "','" + javaDatetoSql(strNow) + "','" + javaTimetoSql(strNow) + "','" + vote + "')");
					
					reply = "Your vote has been rejected.You already casted your vote before this time.";
				}				
				else if (legal==3)
				{
					int update = statement.executeUpdate("INSERT sms VALUES(" + 0 + ",'" +
					sender + "','" + javaDatetoSql(strNow) + "','" + javaTimetoSql(strNow) + "','" + vote + "')");
				
					reply = "Sorry, no candidate exist naming \"" + vote + "\".";	
				}
			}	
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}	
			
		return reply;
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
