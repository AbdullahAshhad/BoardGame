package boardgame.model;

/**
 * <p>
 * This class is used to make the Black knight.
 * </p>
 */

public class BlackKnight extends Knight{
    /**
     * <p>
     * Create new BlackKnight which is actually extending the knight.</p>
     *
     * @param knightId Id of knight.
     */
    public BlackKnight(int knightId) {
        super(knightId);
        super.setColor("\u265E");
    }
}
