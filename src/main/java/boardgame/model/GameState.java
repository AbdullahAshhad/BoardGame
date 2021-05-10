/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;

import lombok.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * Class for Representing the Game State.</p>
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class GameState {

    @Getter
    private static final int numBlackKNights = 3;

    @Getter
    private static final int numWhiteKnights = 3;

    private Player player;
    private boolean isWhiteMove;

    /**
     * <p>
     * Constructs the instance of GameState.</p>
     *
     * @param player first player
     */
    public GameState(Player player) {
        this.player = player;
        isWhiteMove = true;

        int i = 0;
        for (var blackKnight : this.player.getBlackKnights()) {
            blackKnight.setRow(0);
            blackKnight.setCol(i++);
        }
        i = 0;
        for (var whiteKnight : this.player.getWhiteKnights()) {
            whiteKnight.setRow(3);
            whiteKnight.setCol(i++);
        }
    }

    /**
     * <p>
     * Total Number of Rows in Chess Board.</p>
     */
    @Getter
    public static final int TOTAL_ROWS = 8;

    /**
     * <p>
     * Total Number of Cols in Chess Board.</p>
     */
    @Getter
    public static final int TOTAL_COLS = 8;

    @Setter(AccessLevel.NONE)
    @Getter
    private static Set<Point> restrictedSquares = new HashSet<>();

    public static void AddRestricted(Point p) {
        restrictedSquares.add(p);
    }

    public static void RemoveRestricted(Point p) {
        restrictedSquares.remove(p);
    }

    public boolean isGoalAchieved() {
        boolean isTrue = true;
        List<BlackKnight> blackKnights = Arrays.stream(getPlayer().getBlackKnights())
                .collect(Collectors.toList());

        Collections.sort(blackKnights);

        for (int i = 0; i < blackKnights.size(); i++) {
            if (blackKnights.get(i).getRow() != 3) isTrue = false;
            if (blackKnights.get(i).getCol() != i) isTrue = false;
        }
        List<Knight> whiteKnights = Arrays.stream(getPlayer().getWhiteKnights())
                .collect(Collectors.toList());
        Collections.sort(whiteKnights);
        for (int i = 0; i < whiteKnights.size(); i++) {
            if (whiteKnights.get(i).getRow() != 0) isTrue = false;
            if (whiteKnights.get(i).getCol() != i) isTrue = false;
        }
        return isTrue;
    }

    public ArrayList<Point> getKnightMoves(Knight knight) {

        ArrayList<Point> knightMoves = knight.getMoves();

        if (this.isWhiteMove()) {
            for (var blackKnight : this.getPlayer().getBlackKnights())
                IntStream.range(0, knightMoves.size())
                        .filter(i -> blackKnight.getMoves().contains(knightMoves.get(i)))
                        .forEachOrdered(knightMoves::remove);
        } else {
            for (var blackKnight : this.getPlayer().getWhiteKnights())
                IntStream.range(0, knightMoves.size())
                        .filter(i -> blackKnight.getMoves().contains(knightMoves.get(i)))
                        .forEachOrdered(knightMoves::remove);
        }
        return knightMoves;
    }

}
