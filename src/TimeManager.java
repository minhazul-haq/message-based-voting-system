//class for converting all time & date between java & mySql
//and comparing 2 dates or 2 times

import java.util.Date;

public class TimeManager
{
	private String monthName[] = {"Jan","Feb","Mar","Apr","May","Jun",
		"Jul","Aug","Sep","Oct","Nov","Dec"};

	//converts dd.mm.yy date to sql format date
	public String dateToSqlDate(String s)
	{
		String str = new String();
		str = "20" + s.substring(6,8) + "-" + s.substring(3,5) + "-" + s.substring(0,2);
		return str;
	}

	//converts SQL date to dd.mm.yy
	public String toDate(String s)
	{
		String str = new String();
		str = s.substring(8,10) + '.' + s.substring(5,7) + '.' + s.substring(2,4);
		return str;
	}

	//converts SQL time to hh:mm:ss AM/PM
	public String toTime(String s)
	{
		String str = new String();
		
		int hour = Integer.parseInt(s.substring(0,2));
		
		if (hour>12) 
		{
			hour-=12;
			
			if (hour<10) str += '0';

			str += Integer.toString(hour) + s.substring(2,8);
			str += " PM";
		}
		else
		{
			if (hour<10) str += '0';

			str += Integer.toString(hour) + s.substring(2,8);
			str += " AM";
		}
		
		return str;
	}

	//return integer calculating SQL time
	public int sqlTimetoInt(String s)
	{
		String str = new String( s.substring(0,2) + s.substring(3,5) + s.substring(6,8) );
		return Integer.parseInt(str);
	}
	
	//return integer calculating SQL date
	public int sqlDatetoInt(String s)
	{
		String str = new String( s.substring(2,4) + s.substring(5,7) + s.substring(8,10) );
		return Integer.parseInt(str);
	}
	
	//return integer calculating JAVA time
	public int javaTimetoInt(String s)
	{
		String str = new String( s.substring(11,13) + s.substring(14,16) + s.substring(17,19) );
		return Integer.parseInt(str);
	}
	
	//return integer calculating JAVA date
	public int javaDatetoInt(String s)
	{
		String str = new String( s.substring(26,28) );
		String month = new String( s.substring(4,7) );
		
		for(int i=0;i<=11;i++)
		{
			if (month.equals(monthName[i])) 
			{
				if ( (i+1)<10 )	str+='0'+String.valueOf(i+1);
				else str+=String.valueOf(i+1);
				
				break;
			}
		}
		
		str += s.substring(8,10);
		return Integer.parseInt(str);
	}

	//return true if strDate is past
	public boolean isDatePast(String strDate)
	{
		Date date = new Date();
		String strNow = new String(date.toString());
		
		return ( javaDatetoInt(strNow)>sqlDatetoInt(strDate) );
	}

	//return true if strDate is today
	public boolean isDateToday(String strDate)
	{
		Date date = new Date();
		String strNow = new String(date.toString());
		
		return ( javaDatetoInt(strNow)==sqlDatetoInt(strDate) );
	}

	//return true if strTime is past
	public boolean isTimePast(String strTime)
	{
		Date date = new Date();
		String strNow = new String(date.toString());
		
		return ( javaTimetoInt(strNow)>sqlTimetoInt(strTime) );
	}

	//return strNow to sql time
	public String javaTimetoSql(String strNow)
	{
		return ( strNow.substring(11,19) );
	}

	//return strNow to sql date
	public String javaDatetoSql(String strNow)
	{
		String str = new String();
		String month = new String( strNow.substring(4,7) );
		
		str += strNow.substring(24,28);
		str += "-";
		
		for(int i=0;i<=11;i++)
		{
			if (month.equals(monthName[i])) 
			{
				if ( (i+1)<10 )	str+='0'+String.valueOf(i+1);
				else str+=String.valueOf(i+1);
				
				break;
			}
		}
		str += "-";
		
		str += strNow.substring(8,10);
				
		return str;
	}	
}