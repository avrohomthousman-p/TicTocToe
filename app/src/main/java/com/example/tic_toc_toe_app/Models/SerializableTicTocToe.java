package com.example.tic_toc_toe_app.Models;

import java.io.Serializable;


/**
 * A version of the TicTocToe game model that is serializable. This is useful for saving and
 * restoring the state of the game when an activity is destroyed and re-created.
 */
public interface SerializableTicTocToe extends TicTocToeGame, Serializable {
}
