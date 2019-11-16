public class ChooseOfFour extends ChooseOfThree {
    String one;
    String two;
    String three;
    String four;

    ChooseOfFour(String one, String two, String three, String four) {
        super(one, two, three);
        this.four = four;
    }

    public String getFour() {
        return four;
    }
}
