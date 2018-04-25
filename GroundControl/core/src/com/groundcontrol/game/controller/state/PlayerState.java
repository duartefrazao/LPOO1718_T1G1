package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.elements.PlayerView;

public interface PlayerState {

    void handleInput(PlayerView context, GameView.StateInput input);

}
