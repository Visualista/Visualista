package io.github.visualista.visualista.playercontroller;

import io.github.visualista.visualista.model.IGetScene;
import io.github.visualista.visualista.view.IGetPlayerController;

public interface IPlayerView {

    public void updateScene(IGetScene sceneToDisplay);
    
    public boolean getIsReady();
    
    public void setController(IGetPlayerController controller);
    
    public void removeFileLoadListeners();
}
