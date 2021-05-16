package boardgame.controller;

import boardgame.model.Knight;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import lombok.Data;

@Data
public class KnightController extends Label {
    private Knight knight;

    public KnightController(Knight knight) {
        super(knight.getColor());
        super.setFont(new Font(knight.getFont()));
        this.knight = knight;

    }
}
