package boardgame.controller.customControls;

import boardgame.model.Knight;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import lombok.Data;

/**
 * <p>This class is to show Knight in GUI.</p>
 *
 *
 * Knights get their appearance on board from here.
 *
 */

@Data
public class KnightController extends Label {
    private Knight knight;

    /**
     * this method is used for the images of knights on board.
     * @param knight is the knight that appear on board.
     */
    public KnightController(Knight knight) {
        super(knight.getColor());
        super.setFont(new Font(knight.getFont()));
        this.knight = knight;

    }
}
