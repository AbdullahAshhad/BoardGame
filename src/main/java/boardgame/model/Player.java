/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;


import boardgame.helpers.typeadapters.LocalDateTimeAdapter;
import lombok.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;


/**
 * <p>
 * Class representing Player.
 * <p>
 * <p>it implements the methods of serializable and comparable.</p>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Serializable, Comparable {

    private String name;

    private int numSteps;

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime gameStarted;

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime gameFinished;

    private Boolean isGoalAchieved;

    @XmlTransient
    private Knight[] whiteKnights;

    @XmlTransient
    private BlackKnight[] blackKnights;

    /**
     * <p>this method counts the number of steps.</p>
     */
    public void incrementStep() {
        this.numSteps++;
    }

    /**
     * <p>
     * Constructor of Player.</p>
     *
     * <p>
     * It will make new instance of {@link Player} object.</p>
     *
     *
     * @param name player name
     */
    public Player(String name) {
        this.blackKnights = new BlackKnight[GameState.getNumBlackKnights()];
        this.whiteKnights = new Knight[GameState.getNumWhiteKnights()];
        int i = 0;
        for (; i < blackKnights.length; i++) blackKnights[i] = new BlackKnight(i);

        for (int j = 0; j < whiteKnights.length; j++, i++) whiteKnights[j] = new Knight(i);
        this.name = name;
        this.numSteps = 0;
        this.isGoalAchieved = false;
        this.gameStarted = LocalDateTime.now();
    }

    @Override
    public int compareTo(Object o) {
        Player other = (Player) o;
        int compar = -1 * Boolean.compare(this.isGoalAchieved, other.isGoalAchieved);

        if(compar==0){
            if (this.numSteps > other.numSteps) return 1;
            else if (this.numSteps < other.numSteps) return -1;
            else {

                long secondsElapsedThisPlayer = Duration.between(this.gameStarted, this.gameFinished).toSeconds();
                long secondsElapsedOtherPlayer = Duration.between(other.gameStarted, other.gameFinished).toSeconds();
                return Long.compare(secondsElapsedThisPlayer, secondsElapsedOtherPlayer);
            }
        }
        return compar;
    }
}
