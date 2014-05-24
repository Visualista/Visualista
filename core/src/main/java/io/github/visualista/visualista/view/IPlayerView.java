package io.github.visualista.visualista.view;

import io.github.visualista.visualista.model.IGetScene;

public interface IPlayerView {

    public void updateScene(IGetScene sceneToDisplay);

    public boolean getIsReady();

    public void setController(IGetPlayerController controller);

    public void removeFileLoadListeners();
}
