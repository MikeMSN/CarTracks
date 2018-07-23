package crudtac.uicontroller;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import crudtac.model.Car;
import crudtac.model.Track;
import crudtac.repository.CarRepository;
import crudtac.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
public class CarUI extends VerticalLayout {

    private final TrackRepository trackRepository;
    private final CarRepository carRepository;
    private Track track;

    private final CarEditor carEditor;

    private final Button addNewBtn = new Button("New car", VaadinIcons.PLUS);
    private final Grid<Car> grid = new Grid<>(Car.class);

    @Autowired
    public CarUI(TrackRepository trackRepository, CarRepository carRepository, CarEditor carEditor) {
        this.trackRepository = trackRepository;
        this.carRepository = carRepository;
        this.carEditor = carEditor;

        setVisible(false);

        VerticalLayout verticalLayout = new VerticalLayout(addNewBtn, grid);
        HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayout, carEditor);
        addComponent(horizontalLayout);

        grid.setHeightByRows(5);
        grid.setWidth(50, Unit.PERCENTAGE);
        grid.setColumns("id", "name", "transmission", "ai", "maxSpeed");



        grid.asSingleSelect().addValueChangeListener(e -> {
            carEditor.editCar(e.getValue(), track);
        });

        carEditor.setChangeHandler(() -> {
            carEditor.setVisible(false);
            listCars(track);
        });


        addNewBtn.addClickListener(e -> {
            Car c = new Car();
            c.setTrack(this.track);
            carEditor.editCar(c, this.track);
        });

    }


    public void listCars(Track track) {
        if(track == null){
            setVisible(false);
            this.track = null;
        } else {
            setVisible(true);
            List<Car> cars = carRepository.findAllByTrackId(track.getId());
            grid.setItems(cars);
            this.track = track;
        }
    }
}
