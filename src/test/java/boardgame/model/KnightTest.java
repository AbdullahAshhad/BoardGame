package boardgame.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnightTest {
    private static GameState gs;


    @BeforeEach
    public void setup() {
        Player player = new Player("testPlayer");
        gs = new GameState(player);
    }

    @Test
    public void testKnightActualMoves() {
        List<ImmutablePair<Integer, Integer>> blackKnightActualMoves = Arrays.asList(
                new ImmutablePair<>(2, 1),
                new ImmutablePair<>(1, 2));

        assertEquals(gs.getPlayer().getBlackKnights()[0].getMoves(), blackKnightActualMoves);

    }


}
