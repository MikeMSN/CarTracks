package crudtac;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import crudtac.model.Track;
import crudtac.model.embedded.Length;
import crudtac.model.embedded.enums.UnitLen;
import crudtac.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringUI
public class TrackEditor extends VerticalLayout {

    private final TrackRepository repository;
    private Track track;


    private TextField name = new TextField("Track's name");
    private TextField description = new TextField("Description");
    private TextField length = new TextField("Length");
    private ComboBox<String> lengthUnits = new ComboBox<>("LengthUnits");



    private Button save = new Button("Save", VaadinIcons.CHECK);
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcons.TRASH);

    private CssLayout actions = new CssLayout(save, cancel, delete);
    private Binder<Track> binder = new Binder<>(Track.class);

    @Autowired
    public TrackEditor(TrackRepository repository) {
        this.repository = repository;

        addComponents(name, description, description, length, lengthUnits, actions);

        binder.forField(name).bind(Track::getName, Track::setName);
        binder.forField(description).bind(Track::getDescription, Track::setDescription);
        length.addStyleName("numerical");
        binder.forField(length).bind(t -> String.valueOf(t.getLength().getValue()),
                (t, v) -> {
                    if (v.matches("\\d+\\.?\\d+")) {
                        t.getLength().setValue(Double.parseDouble(v));
                    } else {
                        t.getLength().setValue(0);
                    }
                });
        lengthUnits.setItems(Arrays.stream(UnitLen.values()).map(String::valueOf).collect(Collectors.toList()));
        binder.forField(lengthUnits)
                .bind(track -> {
                            Length len = track.getLength();
                            if (len.getUnitLen() == null)
                                return "";
                            else return len.getUnitLen().toString();
                        },
                        (track, s) -> track.getLength().setUnitLen(UnitLen.valueOf(s)));


        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> repository.save(track));

        delete.addClickListener(e -> repository.delete(track));

        cancel.addClickListener(e -> editTrack(track));

        setVisible(false);

    }

    public void editTrack(Track inTrack) {
        if (inTrack == null) {
            setVisible(false);
            return;
        }
        boolean persisted = (inTrack.getId() != null);

        if (persisted) {
            track = repository.findById(inTrack.getId()).orElse(null);
        } else {
            track = inTrack;
        }
        cancel.setVisible(persisted);
        binder.setBean(track);
        setVisible(true);
        save.focus();
        name.selectAll();
    }


    public void setChangeHandler(ChangeHandler h) {
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

    @FunctionalInterface
    interface ChangeHandler {
        void onChange();
    }
}
