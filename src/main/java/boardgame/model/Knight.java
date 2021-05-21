/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static boardgame.model.GameState.TOTAL_COLS;
import static boardgame.model.GameState.TOTAL_ROWS;

/**
 * <p>Knight class.</p>
 */
@Getter
@Setter
public class Knight implements Comparable {

    @Setter(AccessLevel.PACKAGE)
    private String color = "\u2658";

    @Setter(AccessLevel.NONE)
    private final int font = 80;

    private int knightId;

    private int row;

    private int col;

    private Pair<Integer, Integer> previousLocation;


    /**
     * <p>Create new Knight.</p>
     *
     * @param knightId the id of knight according to {@link boardgame.model.GameState} object
     */
    public Knight(int knightId) {
        this.knightId = knightId;
    }

    /**
     * <p>
     * Method for getting Location of player.</p>
     *
     * @return the current Position of Player on Chess baord
     */
    public Pair<Integer, Integer> getCurrentLocation() {
        return new ImmutablePair<>(row, col);
    }

    /**
     * <p>
     * Method for getting Available Moves of player.</p>
     *
     * @return possible moves of player
     */
    protected ArrayList<Pair<Integer, Integer>> getMoves() {
        System.out.println("res");
        GameState.getRestrictedSquares().forEach(System.out::println);
        Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
        List<Pair<Integer, Integer>> moves = new ArrayList<>();
        // All possible moves of a knight
        int X[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int Y[] = {1, 2, 2, 1, -1, -2, -2, -1};

        // Check if each possible move is valid or not
        for (int i = 0; i < 8; i++) {

            // Position of knight after move
            int x = this.row + X[i];
            int y = this.col + Y[i];

            // count valid moves
            if (x >= 0 && y >= 0 && x < TOTAL_ROWS && y < TOTAL_COLS
                    && !GameState.getRestrictedSquares().contains(new ImmutablePair<>(x, y))) {
                possibleMoves.add(new ImmutablePair<>(x, y));
            }

        }
        return new ArrayList<>(possibleMoves);
    }

    @Override
    public String toString() {
        return "Knight{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knight knight = (Knight) o;
        return knightId == knight.knightId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(knightId);
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.col, ((Knight) o).col);
    }
}
