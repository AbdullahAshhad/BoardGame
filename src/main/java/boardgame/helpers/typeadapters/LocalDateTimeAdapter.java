package boardgame.helpers.typeadapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, dateFormat);
    }

    public String marshal(LocalDateTime v) {
        return v.format(dateFormat);
    }
}