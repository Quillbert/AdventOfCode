import java.util.*;
import java.security.*;
import java.math.*;

public class Day4P1 {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		Scanner s = new Scanner(System.in);

		String input = s.nextLine().trim();

		int i = 0;
		
		while(true) {
			
			String in = input + i;
			
			MessageDigest md = MessageDigest.getInstance("md5");

			byte[] messageDigest = md.digest(in.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);

			String hashtext = no.toString(16);
			
			if(hashtext.length() <= 27) {
				System.out.println(i);
				break;
			}
			
			i++;

		}
	}

}
