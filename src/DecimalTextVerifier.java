import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;

/**
 * Provides a utility for verifying and formatting decimal text input.
 * <p>
 * This class offers a method to obtain a TextFormatter that ensures
 * the text input adheres to a valid decimal format.
 * </p>
 * @author Adam Abusang
 */
public class DecimalTextVerifier {

    /**
     * Returns a TextFormatter that verifies the input text to ensure it's a valid decimal.
     *
     * <p>
     * The returned formatter checks the new text input against a decimal format.
     * If the input is not a valid decimal, it rejects the input.
     * </p>
     *
     * @param <V> the type parameter for the TextFormatter
     * @return a TextFormatter that validates the input as a decimal
     */
    public static <V>TextFormatter getFormatter() {
        TextFormatter<V> tf = new TextFormatter<>(c -> {
            DecimalFormat dfNP = new DecimalFormat("#");

            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = dfNP.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return  c;
            }

        });

        return tf;
    }
}
