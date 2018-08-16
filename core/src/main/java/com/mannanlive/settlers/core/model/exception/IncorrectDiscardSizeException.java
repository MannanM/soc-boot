package com.mannanlive.settlers.core.model.exception;

import java.util.List;

public class IncorrectDiscardSizeException extends GameException {
    public IncorrectDiscardSizeException(int expectedSize, List discardList) {
        super(String.format("You must discard %d resources, resources %s.", expectedSize, discardList));
    }
}
