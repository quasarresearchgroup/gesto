package gesto;

public enum SubjectType
{
	FRESHMAN('1', "FRESHMAN"), FINALIST('0', "FINALIST");

	private Character code;
	private String name;

	private SubjectType(Character code, String name)
	{
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public Character getCode()
	{
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public static SubjectType convertCode(Character c)
	{
		for (SubjectType st : SubjectType.values())
			if (st.getCode() == c)
				return st;
		return null;
	}
	
	public String toString()
	{
		return name;
	}

}