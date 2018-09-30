package control.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Parsers {

  public static String removeNonNum(String str) {
    return str.replaceAll("\\D", "");
  }


  public static String removeNonNum(Object str) {
    return removeNonNum(str.toString());
  }

  public static String cepParse(Object CEP) {
    String str = removeNonNum(CEP.toString());
    return str.length() == 8 ? str : null;
  }

  public static String cpfParse(Object CPF) {
    String str = removeNonNum(CPF.toString());
    return str.length() == 11 ? str : null;
  }

  public static String dateParse(Object data) {
    
    DateFormat bdFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    try {

      return format.format(bdFormat.parse(data.toString()));
    } catch (ParseException e) {

      e.printStackTrace();
    }
    
    return format.format(data.toString());
  }
}
