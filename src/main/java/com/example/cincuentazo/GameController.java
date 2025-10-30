package com.example.cincuentazo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private Label lblOpponents;
    @FXML
    private Label lblTableSum;

    private int numberOfOpponents = 0;

    // Metodo para recibir los oponentes desde el menú
    public void setNumberOfOpponents(int opponents) {
        this.numberOfOpponents = opponents;
        if (lblOpponents != null) {
            lblOpponents.setText("Playing against " + numberOfOpponents + " machine opponent(s)");
        }
    }


    @FXML
    private void initialize() {
        if (lblOpponents != null) {
            lblOpponents.setText("Opponents: " + numberOfOpponents);
        }
        if (lblTableSum != null) {
            lblTableSum.setText("Table Sum: 0");
        }
    }

}
