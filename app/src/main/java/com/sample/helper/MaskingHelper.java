package com.sample.helper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingHelper {
  String NAME_MASKING_PATTERN = "(?<=.{1})(?<masking>.*)(?=.$)";
  String EMAIL_MASKING_PATTERN = "([^@]+)@(.*)\\.(.*)";
  String PHONE_MASKING_PATTERN = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
  String MASKING_SYMBOL = "*";

  public MaskingHelper() {}

  public String nameMasking(String name) {
    if (name.length() < 3) {
      return name.substring(0, name.length() - 1) + MASKING_SYMBOL;
    }
    return name.replaceFirst(NAME_MASKING_PATTERN, MASKING_SYMBOL.repeat(name.length() - 2));
  }

  public String emailMasking(String email) {
    Matcher m = Pattern.compile(EMAIL_MASKING_PATTERN).matcher(email);
    if (m.find()) {
      StringBuilder sb = new StringBuilder("");
      sb.append(m.group(1).charAt(0));
      sb.append(m.group(1).substring(1).replaceAll(".", "*"));
      sb.append("@");
      sb.append(m.group(2).replaceAll(".", "*"));
      sb.append(".").append(m.group(3));
      return sb.toString();
    }
    return email;
  }

  public String phoneMasking(String phone) {
    Matcher m = Pattern.compile(PHONE_MASKING_PATTERN).matcher(phone);
    if (m.find()) {
      String replaceTarget = m.group(2);
      char[] c = new char[replaceTarget.length()];
      Arrays.fill(c, '*');
      return phone.replace(replaceTarget, String.valueOf(c));
    }
    return phone;
  }
}
