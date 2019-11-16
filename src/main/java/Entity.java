import java.util.ArrayList;
import java.util.List;

public class Entity {
    static List<String> listOfQuestions = new ArrayList<>();
    static List<ChooseOfFour> listOfPossibleAnswers = new ArrayList<>();
    static List<ChooseOfFour> trueFalse = new ArrayList<>();

    static void fillListOfQuestions() {
        listOfQuestions.add("Время поиска в сбалансированном бинарном дереве:");
        listOfQuestions.add("Какова будет сложность поиска элемента по ключу в hashmap, если ключами являются объекты, hashcode всегда равен 1?");
        listOfQuestions.add("Среднее время доступа к элементу в LinkedList:");
    }

    static void fillListOfPossibleAnswers() {
        listOfPossibleAnswers.add(new ChooseOfFour("log(n)", "n", "n^2", "2^n"));
        listOfPossibleAnswers.add(new ChooseOfFour("log(n)", "n", "n^2", "1"));
        listOfPossibleAnswers.add(new ChooseOfFour("log(n)", "n", "n^2", "n^n"));
    }

    static void fillTrueFalse() {
        trueFalse.add(new ChooseOfFour("1", "0", "0", "0"));
        trueFalse.add(new ChooseOfFour("0", "1", "0", "0"));
        trueFalse.add(new ChooseOfFour("0", "1", "0", "0"));
    }
}
