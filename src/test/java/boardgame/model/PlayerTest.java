package boardgame.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player = new Player("testPlayer");

    @Test
    public void testMethodIncrementStep(){
        int numSteps = player.getNumSteps();
        player.incrementStep();
        assertEquals(numSteps+1, player.getNumSteps());
    }
}
