package vms.scheduling;

import net.redhogs.cronparser.CronExpressionDescriptor;

import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CronFormatConverter {

	private static Locale Russian = new Locale("ru","RU");
	private static Locale English = Locale.ENGLISH;
	private static Locale Default = Locale.getDefault();


	ResourceBundle resourceBundle = ResourceBundle.getBundle(String.valueOf(English));



	public static void main(String[] args) {
		cron2date("*/33 * * * * *");
	}




	public static String cron2date(String cronExpr){
		try {
			return CronExpressionDescriptor.getDescription(cronExpr,Locale.ENGLISH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cronExpr;
	}
}
