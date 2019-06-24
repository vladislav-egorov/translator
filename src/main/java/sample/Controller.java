package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    public TextField text;
    public Button runButton;
    public Text caption;


    public void startCalc(ActionEvent actionEvent) {
        String s = text.getText();
        if (s.isEmpty()){
            caption.setText("Пустая строка!");
        }
        String result =  Rextester.translateToNumbers(s);
        caption.setText(result);
    }
}
