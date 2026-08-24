package de.dielunze.gmscreen.app.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Placeholder for the game master view. Filled in from phase 8 onwards.
 */
@Route("")
@PageTitle("GM Screen")
public class CombatView extends VerticalLayout {

    public CombatView() {
        add(new H1("GM Screen"));
        add(new Paragraph("Phase 0: the scaffolding stands. No combat yet."));
    }
}
