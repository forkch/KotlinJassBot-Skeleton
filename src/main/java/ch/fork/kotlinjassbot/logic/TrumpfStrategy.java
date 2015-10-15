package ch.fork.kotlinjassbot.logic;

import ch.fork.kotlinjassbot.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * Created by fork on 08.07.15.
 */
public class TrumpfStrategy {

    public Trumpf chooseTrumpf(List<JassCard> myCards) {
        return new Trumpf(TrumpfMode.OBEABE, null);
    }

    public Trumpf chooseTrumpfGschobe(List<JassCard> myCards) {
        return new Trumpf(TrumpfMode.OBEABE, null);
    }
}
