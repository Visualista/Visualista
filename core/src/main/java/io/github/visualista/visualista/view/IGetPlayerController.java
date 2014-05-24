package io.github.visualista.visualista.view;

import io.github.visualista.visualista.model.IGetActor;

public interface IGetPlayerController {

    void addDataToView();

    void tileClicked(IGetActor actor);

    void openNovelFile();
}
