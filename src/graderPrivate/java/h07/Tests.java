package h07;

import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.match.Match;
import org.tudalgo.algoutils.tutor.general.match.MatcherFactories;
import org.tudalgo.algoutils.tutor.general.match.Stringifiable;

import org.tudalgo.algoutils.tutor.general.match.*;

public class Tests {

    private static final double MINIMUM_SIMILARITY = .75;

    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    @SuppressWarnings("removal")
    public static <T extends Stringifiable> Matcher<T> stringMatcher(String string) {

        return new Matcher<T>() {

            @Override
            public <ST extends T> Match<ST> match(ST object) {
                return new Match<>() {

                    final double similarity = org.tudalgo.algoutils.reflect.TestUtils.similarity(object.string(), string);

                    @Override
                    public boolean matched() {
                        return similarity >= MINIMUM_SIMILARITY;
                    }

                    @Override
                    public ST object() {
                        return object;
                    }

                    @Override
                    public int compareTo(Match<ST> other) {
                        if (!other.matched()) {
                            return matched() ? 1 : 0;
                        } else if (!matched()) {
                            return -1;
                        }
                        double otherSimilarity = org.tudalgo.algoutils.reflect.TestUtils.similarity(other.object().string(), string);
                        return Double.compare(similarity, otherSimilarity);
                    }
                };
            }
        };
    }
}
