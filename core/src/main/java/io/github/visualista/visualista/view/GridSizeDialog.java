package io.github.visualista.visualista.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GridSizeDialog extends Dialog {

    private static final String title = "Input Grid Size";

    public GridSizeDialog(final Skin skin) {
        super(title, skin);
        // TODO Auto-generated constructor stub
    }

    public GridSizeDialog(final Skin skin, final String windowStyleName) {
        super(title, skin, windowStyleName);
        // TODO Auto-generated constructor stub
    }

    public GridSizeDialog(final WindowStyle windowStyle) {
        super(title, windowStyle);
        // TODO Auto-generated constructor stub
    }

}
