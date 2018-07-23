package crudtac.uicontroller;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Setter;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import crudtac.model.Car;
import crudtac.model.Track;
import crudtac.model.embedded.enums.AI;
import crudtac.model.embedded.enums.Transmission;
import crudtac.model.embedded.enums.UnitVelocity;
import crudtac.repository.CarRepository;
import crudtac.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;


@SpringUI
public class CarEditor extends VerticalLayout {

    private final TrackRepository trackRepository;
    private final CarRepository carRepository;
    private Car car;


    private TextField name = new TextField("Name");

    private ComboBox<String> transmission = new ComboBox<>("Transmission");

    private ComboBox<String> aiUsage = new ComboBox<>("AI usage");

    private TextField valueOfSpeed = new TextField("Value of Speed");
    private ComboBox<String> unitsOfSpeed = new ComboBox<>("Units of Speed");



    private Button save = new Button("Save", VaadinIcons.CHECK);
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcons.TRASH);

    private CssLayout actions = new CssLayout(save, cancel, delete);
    private Binder<Car> binder = new Binder<>(Car.class);



    @Autowired
    public CarEditor(TrackRepository trackRepository, CarRepository carRepository) {
        this.trackRepository = trackRepository;
        this.carRepository = carRepository;

        addComponents(name,transmission,aiUsage,valueOfSpeed,unitsOfSpeed,actions);

        binder.forField(name).bind(Car::getName, Car::setName);

        transmission.setItems(Arrays.stream(Transmission.values()).map(String::valueOf).collect(Collectors.toList()));
        binder.forField(transmission).bind((ValueProvider<Car, String>) car -> {
            if (car.getTransmission() == null) {
                return "";
            } else {
                return car.getTransmission().toString();
            }
        }, (Setter<Car, String>) (car, s) -> {
            if(!"".equals(s))
                car.setTransmission(Transmission.valueOf(s));
        });

        aiUsage.setItems(Arrays.stream(AI.values()).map(String::valueOf).collect(Collectors.toList()));
        binder.forField(aiUsage).bind((ValueProvider<Car, String>) car -> {
            if (car.getAi() == null)
                return "";
            else
                return car.getAi().toString();
        }, (Setter<Car, String>) (car, s) -> {
            if(!"".equals(s))
                car.setAi(AI.valueOf(s));
        });

        binder.forField(valueOfSpeed).bind(new ValueProvider<Car, String>() {
            @Override
            public String apply(Car car) {
                if(car.getMaxSpeed() == null)
                    return "0.0";
                return String.valueOf(car.getMaxSpeed().getValue());
            }
        }, (Setter<Car, String>) (car, s) -> {
            if (s.matches("\\d+\\.?\\d*")) {
                if (car.getMaxSpeed()!= null)
                    car.getMaxSpeed().setValue(Double.parseDouble(s));
            }
        });

        unitsOfSpeed.setItems(Arrays.stream(UnitVelocity.values()).map(String::valueOf).collect(Collectors.toList()));

        binder.forField(unitsOfSpeed).bind((ValueProvider<Car, String>) car -> {
            if (car.getMaxSpeed() == null || car.getMaxSpeed().getUnit() == null) {
                return "";
            }
            return car.getMaxSpeed().getUnit().toString();
        }, (Setter<Car, String>) (car, s) -> {
            if(!"".equals(s) && car.getMaxSpeed() != null){
                car.getMaxSpeed().setUnit(UnitVelocity.valueOf(s));
            }
        });

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setSpacing(true);
        System.out.println("CAR: + " + car);



        save.addClickListener(event -> carRepository.save(car));

        delete.addClickListener(e -> carRepository.delete(car));

        //cancel.addClickListener(e -> editCar(car));
         setVisible(false);
        /*binder.forField(transmission)
                .bind((ValueProvider<Car, String>) car -> car.getTransmission().toString(),
                      (Setter<Car, String>) (car, s) -> car.setTransmission(Transmission.valueOf(s)));
*/
    }



    public void editCar(Car car, Track track) {
        System.out.println("ediCar car:" + car);
        if(track == null || car == null) {
            setVisible(false);
            return;
        }
        boolean persisted = (car.getId() != null);

        if (persisted) {
            this.car = carRepository.findById(car.getId()).orElse(null);
        } else {
            this.car = car;
        }

        cancel.setVisible(persisted);
        if(this.car != null)
         binder.setBean(this.car);

        setVisible(true);
        save.focus();

    }

    public void createCar(Car car, Track track) {
        if(track == null || car == null) {
            setVisible(false);
            return;
        }
            if(car.getTrack() == null)
                car.setTrack(track);

            this.car = car;

        cancel.setVisible(false);
        binder.setBean(car);
        setVisible(true);
        save.focus();
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
