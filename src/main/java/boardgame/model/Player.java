/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;


import lombok.*;

import java.io.Serializable;


/**
 * <p>
 * Class representing Player.
 * <p>
 *
 */
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Serializable {

    private String name;

    private int numSteps;

    private Knight[] whiteKnights;
    private BlackKnight[] blackKnights;

    public void incrementStep(){
        this.numSteps ++;
    }

    /**
     * <p>
     * Constructor of Player.</p>
     *
     * <p>
     * It will make new instance of {@link Player} object.</p>
     *
     * @param name player name
     */
    public Player(String name) {
        this.blackKnights = new BlackKnight[GameState.getNumBlackKNights()];
        this.whiteKnights = new Knight[GameState.getNumWhiteKnights()];
        int i=0;
        for(;i<blackKnights.length;i++) blackKnights[i] = new BlackKnight(i);

        for (int j =0;j< whiteKnights.length;j++,i++) whiteKnights[j] = new Knight(i);
        this.name = name;
        this.numSteps = 0;
    }
}
