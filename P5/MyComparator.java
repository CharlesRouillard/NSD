import java.util.Comparator;

public class MyComparator implements Comparator<BigNode>
{
	@Override
	public int compare(BigNode o1, BigNode o2) {
		// TODO Auto-generated method stub
		return o1.getDegree() - o2.getDegree();
	}
}
