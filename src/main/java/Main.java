import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(LocalDateTime.now());
		
		System.out.println(formatSKUNumber(""));
		System.out.println(formatSKUNumber("000000000010082901"));
		System.out.println(formatSKUNumber("10082901"));
		System.out.println(formatSKUNumber("0000000000100829"));
	}
	
	public static final String formatSKUNumber(String catalogRefId) {
        int sizeOffset = 18 - catalogRefId.length();
        String zeros = "";
        for (int i = 0; i < sizeOffset; i++) {
            zeros += "0";
        }

        return zeros + catalogRefId;
    }

}
