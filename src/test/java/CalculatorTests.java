import myproject.Calculator;
import org.junit.Test;

public class CalculatorTests {
    @Test
    public void standardInputTest() {
        Calculator calculator = new Calculator();
        assert (calculator != null);
    }
}
