package boardgame.model;

public class BlackKnight extends Knight{
    /**
     * <p>
     * Create new BlackKnight which is actually extending the knight.</p>
     *
     * @param knightId
     */
    public BlackKnight(int knightId) {
        super(knightId);
        super.setColor("\u265E");
    }
}
