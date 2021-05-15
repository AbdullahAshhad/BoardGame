package boardgame.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>This class is to make the list of the players.</p>
 *
 */

@XmlRootElement(name = "players")
@XmlAccessorType (XmlAccessType.FIELD)

public class Players
{
    @XmlElement(name = "player")
    private List<Player> players = null;

    /**
     * <p>it is method that will return the list of players.</p>
     * @return it returns the list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param employees
     */
    public void setPlayers(List<Player> employees) {
        this.players = employees;
    }
}
