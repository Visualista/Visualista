package io.github.visualista.visualista.view;

import io.github.visualista.visualista.model.IGetScene;

public interface IPlayerView {

    void updateScene(IGetScene sceneToDisplay);

    boolean getIsReady();

    void setController(IGetPlayerController controller);

    void removeFileLoadListeners();
}
