package safast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ParseException {
		String datascadidoneita = "2016-10-23 00:00:00";
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date a = sf.parse(datascadidoneita);
        //datascadidoneita = (sf.parse(datascadidoneita));
        Date d = new Date(datascadidoneita); 
        String out = sf.format(a);
        System.out.println(out);

	}

}
