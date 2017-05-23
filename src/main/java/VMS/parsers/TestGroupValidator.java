package VMS.parsers;

/**
 * Created by Кирилл on 09.05.2017.
 */
public class TestGroupParser {
	public static void main(String[] args) {
		GroupValidator groupValidator = new GroupValidator();
		String nameGroup = "snyat_kvartiru_ekb";
		System.out.println(groupValidator.ifGroupExistCheckById(nameGroup));
	}
}
