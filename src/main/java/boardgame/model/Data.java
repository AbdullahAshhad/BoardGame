/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class made to store some data temporarily.<p>
 *
 */
public class Data {

    @Setter
    @Getter
    private static String player1;

    @Getter
    @Setter
    private static List<Player> playerList = new ArrayList<>();


}