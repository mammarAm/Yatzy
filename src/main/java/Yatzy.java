import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Yatzy {

    public static int chance(int... dices) {
        return IntStream.of(dices).boxed().reduce(0, Integer::sum);
    }

    public static int yatzy(Integer... dices) {
        List<Integer> dicesList = Arrays.asList(dices);
       Set<Integer> targetSet = new HashSet<>(dicesList);
        if (targetSet.size() == 1)
            return 50;
        return 0;
    }

    public static int ones(int... dices) {
        return sumDicesWithCategory(1, dices);
    }

    public static int twos(int... dices) {
        return sumDicesWithCategory(2, dices);
    }

    public static int threes(int... dices) {
        return sumDicesWithCategory(3, dices);
    }

    protected int[] dice;

    public Yatzy(int... dices) {
        dice = dices;
    }

    public int fours() {
        return sumDicesWithCategory(4, dice);
    }

    public int fives() {
        return sumDicesWithCategory(5, dice);
    }

    public int sixes() {
        return sumDicesWithCategory(6, dice);
    }

    public static int score_pair(int... dices) {
        Set<Integer> pairSet = calculatePair(2, dices);
        return pairSet.stream().mapToInt(v -> v).max().getAsInt() * 2;
    }

    public static int two_pair(int... dices) {
        Set<Integer> pairSet = calculatePair(2, dices);
        return pairSet.stream().reduce(0, Integer::sum) * 2;
    }

    public static int four_of_a_kind(int... dices) {
        Set<Integer> pairSet = calculatePair(4, dices);
        return pairSet.stream().reduce(0, Integer::sum) * 4;
    }

    public static int three_of_a_kind(int... dices) {
        Set<Integer> pairSet = calculatePair(3, dices);
        return pairSet.stream().reduce(0, Integer::sum) * 3;
    }

    public static int smallStraight(int... dices) {
        int[] expected = new int[]{1, 2, 3, 4, 5};
        Arrays.sort(dices);
        if (Arrays.equals(dices, expected)) {
            return 15;
        }
        return 0;
    }

    public static int largeStraight(int... dices) {
        int[] expected = new int[]{2, 3, 4, 5, 6};
        Arrays.sort(dices);
        if (Arrays.equals(dices, expected)) {
            return 20;
        }
        return 0;
    }

    public static int fullHouse(int... dices) {
        Map<Integer, Long> values = IntStream.of(dices).boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        Long value = values.entrySet().stream().findFirst().get().getValue();
        if (values.entrySet().size() == 2 && (value == 2 || value == 3)) {
            return IntStream.of(dices).boxed().reduce(0, Integer::sum);
        }
        return 0;
    }

    private static int sumDicesWithCategory(int category, int... dices) {
        int score = 0;
        for (int dice : dices) {
            if (dice == category) {
                score += category;
            }
        }
        return score;
    }

    private static Set<Integer> calculatePair(int pair, int[] dices) {
        return IntStream.of(dices).boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(p -> p.getValue() >= pair)
                .map(e -> e.getKey()).collect(Collectors.toSet());
    }
}
