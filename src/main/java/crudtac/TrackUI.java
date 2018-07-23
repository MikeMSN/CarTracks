package crudtac;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import crudtac.model.Track;
import crudtac.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI
public class TrackUI extends UI {

    private final TrackRepository repo;


    private final TextField filter = new TextField();;
    private final Button addNewBtn = new Button("New track", VaadinIcons.PLUS);;
    private final Grid<Track> grid = new Grid<>(Track.class);
    private final TrackEditor editor;
    private final CarUI cars;

    @Autowired
    public TrackUI(TrackRepository repo, TrackEditor editor, CarUI cars) {
        this.repo = repo;
        this.editor = editor;
        this.cars = cars;
    }

    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout trackEditorAndCars = new HorizontalLayout(editor,cars);
        VerticalLayout mainLayout = new VerticalLayout(actions,grid,trackEditorAndCars);

        setContent(mainLayout);

        grid.setHeightByRows(3);
        grid.setWidth(80, Unit.PERCENTAGE);
        grid.setColumns();
        grid.setColumns("id", "name", "description", "length");

        filter.setPlaceholder("Filter by name");

        // READ
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(v -> listTrack(v.getValue()));

        // UPDATE DELETE
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTrack(e.getValue());
            cars.listCars(e.getValue());
        });

        // CREATE
        addNewBtn.addClickListener(e -> editor.editTrack(new Track()));


        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTrack(filter.getValue());
        });

        // Initialize listing
        listTrack(null);

    }

    void listTrack(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
        }
    }



}
