package boardgame.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private static GameState gs;

    @BeforeEach
    public void setup() {
        Player player = new Player("testPlayer");
        gs = new GameState(player);
    }

    @Test
    public void testInitialKnightPositions() {

        Pair<Integer, Integer>[] blackKnightLocation = Arrays.stream(gs.getPlayer().getBlackKnights())
                .map(Knight::getCurrentLocation)
                .toArray(Pair[]::new);

        Pair<Integer, Integer>[] whiteKnightLocation = Arrays.stream(gs.getPlayer().getWhiteKnights())
                .map(Knight::getCurrentLocation)
                .toArray(Pair[]::new);

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

        assertArrayEquals(blackKnightExpectedLocation, blackKnightLocation);
        assertArrayEquals(whiteKnightExpectedLocation, whiteKnightLocation);
    }

    @Test
    public void testGameStateIsGoalAchievedMethod() {

        Arrays.stream(gs.getPlayer().getWhiteKnights()).forEach(whiteKnight -> whiteKnight.setRow(0));
        Arrays.stream(gs.getPlayer().getBlackKnights()).forEach(blackKnight -> blackKnight.setRow(3));

        assertTrue(gs.isGoalAchieved());
    }

    @Test
    public void testKnightMovesMethod() {
        List<BlackKnight> blackKnights = Arrays.stream(gs.getPlayer().getBlackKnights())
                .collect(Collectors.toList());
        List<Knight> whiteKnights = Arrays.stream(gs.getPlayer().getWhiteKnights())
                .collect(Collectors.toList());

        assertEquals(gs.getKnightMoves(whiteKnights.get(1)).size(), 0);

        whiteKnights.get(0).setCol(1);
        whiteKnights.get(0).setRow(1);

        assertEquals(gs.getKnightMoves(blackKnights.get(0)).size(),
                1);

        assertEquals(gs.getKnightMoves(blackKnights.get(0)).get(0),
                new ImmutablePair<>(1, 2));
    }


}
