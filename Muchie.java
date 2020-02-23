import java.util.ArrayList;

public class Muchie
{
	int sursa;
	int destinatie;
	
	Muchie(int sursa, int destinatie)
	{
		this.sursa = sursa;
		this.destinatie = destinatie;
	}
	
	public static ArrayList<Muchie> muchiiCarePornescDinX(int x, ArrayList<Muchie> array)
	{
		ArrayList<Muchie> muchii = new ArrayList<>();
		 for (Muchie M : array)
        {
            if(M.sursa == x)
            {
                muchii.add(M);
            }
        }
		 return muchii;
	}
	
	@Override
	public String toString()
	{
		return "Muchie{" +
				"sursa=" + sursa +
				", destinatie=" + destinatie +
				'}';
	}
	
	public static ArrayList<Muchie> muchiiCareContinX(int x, ArrayList<Muchie> array)
	{
		ArrayList<Muchie> muchii = new ArrayList<>();
		 for (Muchie M : array)
        {
            if(M.sursa == x || M.destinatie == x)
            {
                muchii.add(M);
            }
        }
		 return muchii;
	}
	public static ArrayList<Muchie> muchiiCareContinXFara1(int x, ArrayList<Muchie> array)
	{
		ArrayList<Muchie> muchii = new ArrayList<>();
		 for (Muchie M : array)
        {
            if(M.sursa == x || M.destinatie == x && M.sursa != 1 && M.destinatie != 1)
            {
                muchii.add(M);
            }
        }
		 return muchii;
	}
	static boolean existaMuchie(int sursa, int destinatie, ArrayList<Muchie> muchii)
	{
		ArrayList<Muchie> aux = Muchie.muchiiCareContinX(sursa, muchii);
		for (Muchie muchie : aux)
		{
			if(muchie.sursa == destinatie || muchie.destinatie == destinatie)
				return true;
		}
		return false;
	}
}
