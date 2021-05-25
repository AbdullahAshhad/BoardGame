package boardgame.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>This class is to make the list of the players to store in XML format.</p>
 *
 */

@XmlRootElement(name = "players")
@XmlAccessorType (XmlAccessType.FIELD)
@Setter
@Getter
public class Players
{
    @XmlElement(name = "player")
    private List<Player> players = null;
}
