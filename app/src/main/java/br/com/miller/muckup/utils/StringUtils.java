package br.com.miller.muckup.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String value =
            "À Á Â Ã Ä Å Æ Ç È É Ê Ë Ì " +
                    "Í Î Ï Ð Ñ Ò Ó Ô Õ Ö Ø Ù Ú Û Ü Ý Þ ß " +
                    "à á â ã ä å æ ç è é ê ë ì í î ï ð ñ " +
                    "ò ó ô õ ö ø ù ú û ü ý þ ÿ ";

    public static boolean isValidPhone(String phoneNumber) {
        return phoneNumber.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
                phoneNumber.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
    }

    public static String formatDate(Date date){

        return new SimpleDateFormat("dd/MM HH:mm",Locale.getDefault()).format(date);

    }

    public static String cleanMoneyString(String value, Locale locale){

        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());

        return value.replaceAll(replaceable, "");

    }

    public static Date parseDate(String date){

        try {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static boolean isValidEmail(String email){

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static String normalizer(String title){

        title = title.toLowerCase();

        if(title.contains(" "))
            title = title.replaceAll(" ", "_");

        String nfdNormalizedString = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean isValidDate(String date) {

        String dateFormat = "dd/MM/yyyy";

        DateTimeFormatter dateTimeFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTimeFormatter = DateTimeFormatter
                    .ofPattern(dateFormat)
                    .withResolverStyle(ResolverStyle.STRICT);

            try {
                LocalDate.parse(date, dateTimeFormatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }else {

            DateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
            df.setLenient(false); // aqui o pulo do gato
            try {
                df.parse(date);
                return true;
            } catch (ParseException ex) {
                return false;
            }
        }
    }
}
