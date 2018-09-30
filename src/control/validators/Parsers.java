package control.validators;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Parsers {
  
  
  public static void main(String[] args) {
    
    System.out.println(dateParse("08/04/2002"));
  }
  
  
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

  public static Date dateParse(Object data) {
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    try {

      return new Date(format.parse(data.toString()).getTime());
    } catch (ParseException e) {

      e.printStackTrace();
    }

    return null;
  }
}