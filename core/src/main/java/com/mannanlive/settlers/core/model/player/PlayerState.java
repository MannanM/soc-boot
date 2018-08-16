package com.mannanlive.settlers.core.model.player;

import com.mannanlive.settlers.core.model.board.Piece;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    private List<Piece> pieces = new ArrayList<>();

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
