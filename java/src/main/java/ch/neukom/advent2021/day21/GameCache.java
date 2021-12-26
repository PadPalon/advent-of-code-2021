package ch.neukom.advent2021.day21;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class GameCache {
    private final Table<Game, Integer, Map<String, Long>> rollCache = HashBasedTable.create();
    private final Map<Game, Map<String, Long>> resultCache = new HashMap<>();

    @Nullable
    public Map<String, Long> getRoll(Game game, int roll) {
        return rollCache.get(game, roll);
    }

    public void putRoll(Game source, int roll, Map<String, Long> result) {
        rollCache.put(source, roll, result);
    }

    @Nullable
    public Map<String, Long> getResult(Game game) {
        return resultCache.get(game);
    }

    public void putResult(Game source, Map<String, Long> result) {
        resultCache.put(source, result);
    }
}
