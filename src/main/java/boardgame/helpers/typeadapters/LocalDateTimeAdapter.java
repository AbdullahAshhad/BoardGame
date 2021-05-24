package boardgame.helpers.typeadapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>This class is used to get the local time and date in desired format.</p>
 */

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>Convert the Date Time from string to {@link LocalDateTime} object.</p>
     *
     * @param v is the string in which time and date is stored.
     * @return it returns the {@link LocalDateTime} object.
     */
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, dateFormat);
    }

    /**
     * <p>This method convert Java objects to string format defined above. </p>
     *
     * @param v is the {@link LocalDateTime} format date..
     * @return the date and time in string format.
     */
    public String marshal(LocalDateTime v) {
        return v.format(dateFormat);
    }
}