//classes for candidate Data : fullname,shortname,vote

public class CandidateData
{
	public String fullName;
	public String shortName;
	public int vote;

	public CandidateData()
	{
		fullName = new String();
		shortName = new String();
		vote = 0;
	}

	public void setName(String s1, String s2)
	{
		fullName = s1;
		shortName = s2;
	}

	public String nameVote()
	{
		return ( fullName + " - " + Integer.toString(vote) );
	}

	public String nameVoteForSms()
	{
		return ( shortName + " " + Integer.toString(vote) + " votes" );
	}

}