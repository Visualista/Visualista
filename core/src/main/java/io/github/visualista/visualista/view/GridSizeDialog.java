package io.github.visualista.visualista.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GridSizeDialog extends Dialog {

    private static final String TITLE = "Input Grid Size";

    public GridSizeDialog(final Skin skin) {
        super(TITLE, skin);
        // TODO Auto-generated constructor stub
    }

    public GridSizeDialog(final Skin skin, final String windowStyleName) {
        super(TITLE, skin, windowStyleName);
        // TODO Auto-generated constructor stub
    }

    public GridSizeDialog(final WindowStyle windowStyle) {
        super(TITLE, windowStyle);
        // TODO Auto-generated constructor stub
    }

}
