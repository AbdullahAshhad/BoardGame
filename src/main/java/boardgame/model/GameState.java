/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Class for Representing the Game State.</p>
 */
@Setter
@Getter
@NoArgsConstructor
public class GameState {

    @Getter
    private static final int numBlackKnights = 3;

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
            whiteKnight.setRow(3); //TODO
            whiteKnight.setCol(i++);
        }
    }

    /**
     * <p>
     * Total Number of Rows in Chess Board.</p>
     */
    @Getter
    public static final int TOTAL_ROWS = 4;

    /**
     * <p>
     * Total Number of Cols in Chess Board.</p>
     */
    @Getter
    public static final int TOTAL_COLS = 3;

    @Setter(AccessLevel.NONE)
    @Getter
    private static Set<Pair<Integer, Integer>> restrictedSquares = new HashSet<>();

    /**
     * <p>Add the box to restricted boxes.</p>
     * @param p is the box number.
     */
    public static void AddRestricted(Pair<Integer, Integer> p) {
        restrictedSquares.add(p);
    }

    /**
     * <p>Reset the boxes that are restricted.</p>
     */
    public static void resetRestricted() {
        restrictedSquares = new HashSet<>();
    }

    /**
     * <p>Remove the boxes from restricted to open.</p>
     * @param p is the box number.
     */
    public static void RemoveRestricted(Pair<Integer, Integer> p) {
        restrictedSquares.remove(p);
    }


    /**
     * <p>This method checks if the goal of the game is achieved or not.</p>
     *
     * It collects the position of the black knights and check if they are on the desired place,
     * same thing happens for the white knights as well.
     *
     * If the black knights are in bottom row and the white knights are in top row,
     * then the goal state is achieved.
     *
     * @return true if the black knights swap the places with white knights. i.e. white knights go to top row and black knights go to bottom row.
     */
    public boolean isGoalAchieved() {
        boolean isTrue = true;
        List<BlackKnight> blackKnights = Arrays.stream(getPlayer().getBlackKnights()).collect(Collectors.toList());
        Collections.sort(blackKnights);
        for (int i = 0; i < blackKnights.size(); i++) {
            if (blackKnights.get(i).getRow() != 3) isTrue = false;
            if (blackKnights.get(i).getCol() != i) isTrue = false;
        }
        List<Knight> whiteKnights = Arrays.stream(getPlayer().getWhiteKnights()).collect(Collectors.toList());
        Collections.sort(whiteKnights);
        for (int i = 0; i < whiteKnights.size(); i++) {
            if (whiteKnights.get(i).getRow() != 0) isTrue = false;
            if (whiteKnights.get(i).getCol() != i) isTrue = false;
        }
        return isTrue;
    }

    /**
     * <p>It is to get the moves of the knights.</p>
     *
     *
     * @param knight is the knight for which we are getting the possible moves.
     * @return it will return the possible moves.
     */
    public ArrayList<Pair<Integer, Integer>> getKnightMoves(Knight knight) {

        ArrayList<Pair<Integer, Integer>> knightMoves = knight.getMoves();

        if (this.isWhiteMove()) {
            for (BlackKnight blackKnight : this.getPlayer().getBlackKnights()) {
                for (int i = 0; i < knightMoves.size(); i++) {
                    if (blackKnight.getMoves().contains(knightMoves.get(i))) {
                        knightMoves.remove(i);
                    }
                }
            }
        } else {
            for (Knight blackKnight : this.getPlayer().getWhiteKnights()) {
                for (int i = 0; i < knightMoves.size(); i++) {
                    if (blackKnight.getMoves().contains(knightMoves.get(i))) {
                        knightMoves.remove(i);
                    }
                }
            }
        }
        return knightMoves;
    }

}
