package boardgame.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private static GameState gs;


    @BeforeAll
    public static void setup() {
        Player player = new Player("testPlayer");
        gs = new GameState(player);
    }

    @Test
    public void testInitialKnightPositions() {

        Pair<Integer, Integer>[] blackKnighttLocation = Arrays.stream(gs.getPlayer().getBlackKnights()).map(Knight::getCurrentLocation).toArray(Pair[]::new);

        Pair<Integer, Integer>[] whiteKnightLocation = Arrays.stream(gs.getPlayer().getWhiteKnights()).map(Knight::getCurrentLocation).toArray(Pair[]::new);

        ImmutablePair<Integer, Integer>[] blackKnightExpectedLocation = new ImmutablePair[]{
                new ImmutablePair<>(0, 0),
                new ImmutablePair<>(0, 1),
                new ImmutablePair<>(0, 2)
        };

        ImmutablePair<Integer, Integer>[] whiteKnightExpectedLocation = new ImmutablePair[]{
                new ImmutablePair<>(3, 0),
                new ImmutablePair<>(3, 1),
                new ImmutablePair<>(3, 2)
        };

        assertArrayEquals(blackKnightExpectedLocation, blackKnighttLocation);
        assertArrayEquals(whiteKnightExpectedLocation, whiteKnightLocation);
    }

    @Test
    public void testGameStateIsGoalAchievedMethod() {

        Arrays.stream(gs.getPlayer().getWhiteKnights()).forEach(whiteKnight -> {
            whiteKnight.setRow(0);
        });
        Arrays.stream(gs.getPlayer().getBlackKnights()).forEach(blackKnight -> {
            blackKnight.setRow(3);
        });
        assertTrue(gs.isGoalAchieved());
    }


}
