package com.example.application.views.stopwatch;

import com.example.application.data.entity.StopWatchEntry;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.ContactService;
import com.example.application.data.service.StopWatchEntryService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Route(value = "todo", layout = MainView.class)
@PageTitle("StopWatch")
@CssImport("./views/about/about-view.css")
public class StopWatchView extends VerticalLayout {

    StopWatchEntryService stopWatchService;
    ContactService contactService;
    CompanyService companyService;
    private Grid<StopWatchEntry> grdStopWachEntries = new Grid<>(StopWatchEntry.class);
    private TextField filterText = new TextField();
    private boolean firstTime = false;
    DatePicker datePicker = new DatePicker();
    Icon prev = new Icon(VaadinIcon.ANGLE_LEFT);
    Button btnPreviousDay = new Button(prev);
    Icon nxt = new Icon(VaadinIcon.ANGLE_RIGHT);
    Button btnNextDay = new Button(nxt);

    public StopWatchView(StopWatchEntryService stopWatchServicee1,
             ContactService contactService,
            CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;
        this.stopWatchService = stopWatchServicee1;
        addClassName("stopwatch-view");
        addClassName("stopwatch-grid-view");
        setSizeFull();
        configureGrid();

        add(computedDlDiv());

        //datePicker.setLabel("Datum");
        btnPreviousDay.addClickListener(e -> {
            LocalDate ld = datePicker.getValue();
            datePicker.setValue(ld.minusDays(1));
        });
        btnPreviousDay.setText("prev");
        btnNextDay.setText("next");

        btnNextDay.addClickListener(e -> {
            LocalDate ld = datePicker.getValue();
            datePicker.setValue(ld.plusDays(1));
        });

        Div dateValue = new Div();
        dateValue.setVisible(false); // for debug only
        dateValue.setText("Select a value");
        datePicker.setHelperText("Tagesdatum");
        datePicker.setValue(LocalDate.now());
        datePicker.setLocale(Locale.GERMAN);
        datePicker.setClearButtonVisible(false);
        datePicker.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                dateValue.setText("No date selected");
            } else {
                dateValue.setText("Selected date: " + event.getValue());
            }
        });
        TextField txtProgrUpTime = new TextField();
        txtProgrUpTime.setHelperText("'program up'-Zeit");
        txtProgrUpTime.setValue("05h 12 min");
        //progUpTimer.setLabel("Program up time");
        txtProgrUpTime.setReadOnly(true);
        txtProgrUpTime.setInvalid(false);
        TextField txtTotalTime = new TextField();
        txtTotalTime.setValue("05h35min");
        txtTotalTime.setHelperText("Arbeitszeit TOTAL");
        txtTotalTime.setReadOnly(true);
        txtProgrUpTime.setInvalid(false);

        add(new H2("Stop Watch: Erfassung"),
                new HorizontalLayout(btnPreviousDay, datePicker, btnNextDay, dateValue),
                new HorizontalLayout(txtProgrUpTime, txtTotalTime),
                extra(), grdStopWachEntries);
        //addClassName("list-view");
        //setSizeFull();
        //configureFilter();
        //configureGrid();
        //add(filterText, grdStopWachEntries);
        updateList();
    }

    private Component extra() {
        Div message = new Div();
        ComboBox<String> comboBox = new ComboBox<>("DL-Name");

        comboBox.setItems("SRMAcceptance", "Monica", "WSG", "QPortal", "DiesUndDas");
        StopWatchEntry.DlName[] dd = StopWatchEntry.DlName.values();
        List<StopWatchEntry.DlName> days = Arrays.asList(StopWatchEntry.DlName.values());
        Collection<String> lst = new HashSet<>();
        for (StopWatchEntry.DlName day : StopWatchEntry.DlName.values()) {
            //System.out.println(day);
            lst.add(day.toString());
        }

        comboBox.setItems(lst);
        comboBox.setValue("WSG");

        /**
         * Allow users to enter a value which doesn't exist in the data set, and
         * set it as the value of the ComboBox.
         */
        comboBox.addCustomValueSetListener(
                event -> comboBox.setValue(event.getDetail()));

        comboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                message.setText("No fruit selected");
            } else {
                message.setText("Selected value: " + event.getValue());
            }
        });
        comboBox.setVisible(false);
        VerticalLayout ret = new VerticalLayout();
        VerticalLayout todosList = new VerticalLayout();

        TextField taskField = new TextField("Task descr.");
        taskField.setVisible(false);
        Icon plus = new Icon(VaadinIcon.PLUS);
        Button addButton = new Button("Add Task", plus);
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            computeInner(message, taskField, todosList);
        });

        ret.add(new HorizontalLayout(addButton, comboBox, taskField),
                todosList
        );

        return ret;
    }

    private void computeInner(Div message, TextField taskField, VerticalLayout todosList) {
        ComboBox<String> cmbDlName = new ComboBox<>();
        cmbDlName.setItems("SRM Acceptance", "Monica", "WSG", "Q-Portal", "DiesUndDas");
        cmbDlName.setValue("WSG");
        cmbDlName.setHelperText("DL");

        /**
         * Allow users to enter a value which doesn't exist in the data set, and
         * set it as the value of the ComboBox.
         */
        cmbDlName.addCustomValueSetListener(
                event -> cmbDlName.setValue(event.getDetail()));

        cmbDlName.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                message.setText("No fruit selected");
            } else {
                message.setText("Selected value: " + event.getValue());
            }
        });

        TextField txtMessage = new TextField();
        TextField dlNumber = new TextField();
        ComboBox<String> cmbWorkType = new ComboBox<>();
        cmbWorkType.setItems("WREAL", "WMEET", "WSUPP");
        cmbWorkType.setValue("WREAL");
        cmbWorkType.setHelperText("Arbeitstyp");
        //dlArbeitstyp.setClearButtonVisible(true);
        //dlArbeitstyp.setValue("0");
        TextField txtSumOfMinutes = new TextField();
        txtMessage.setValue(taskField.getValue());
        txtMessage.setHelperText("Message");
        dlNumber.setValue(cmbDlName.getValue());
        dlNumber.setHelperText("DL number");
        txtSumOfMinutes.setHelperText("Arbeitszeit (min.)");
        txtSumOfMinutes.setClearButtonVisible(true);
        txtSumOfMinutes.setValue("0");

        ToggleButton startStopIcon = new ToggleButton("start rec.");
        startStopIcon.addValueChangeListener(evt
                -> {

            message.setText(String.format("Toggle button value changed from '%s' to '%s'", evt.getOldValue(), evt.getValue()));

            if (evt.getSource().getLabel().equals("start rec.")) {
                startStopIcon.setLabel("pause rec.");
            } else {
                startStopIcon.setLabel("start rec.");

            }
            Notification.show(String.format("Toggle button value changed from '%s' to '%s'", evt.getOldValue(), evt.getValue()));
        });
        // VaadinIcon.PLAY
        //startStopIcon.setId("play");
        startStopIcon.getStyle().set("cursor", "pointer");
        /*
        startStopIcon.addClickListener(event
                ->
        {
          //      Icon icon = event.getSource();
                if(icon.equals(new Icon(VaadinIcon.SIGN_IN)))
                {
                this.startStopIcon = new Icon(VaadinIcon.PLAY);
                }
                else
                {
                
                }
                Notification.show("Aufnahme gestartet");
                //String id = startStopIcon.getId();
                 //= new Icon(VaadinIcon.PAUSE);
                        });
         */
        Icon bookingIcon = new Icon(VaadinIcon.SIGN_IN);
        bookingIcon.getStyle().set("cursor", "pointer");
        bookingIcon.addClickListener(event
                -> {
            StopWatchEntry entry = new StopWatchEntry(LocalDate.now(), txtMessage.getValue(), StopWatchEntry.DlName.valueOf(cmbDlName.getValue()), StopWatchEntry.Worktype.valueOf(cmbWorkType.getValue()), txtSumOfMinutes.getValue());
            Notification.show("Wird in Navision erfasst...: " + entry);
            //Notification.show("Wird in Navision erfasst...: " + entry);
            System.out.println("entry: " + entry);
            //          StopWatchEntry entry1 =new StopWatchEntry(LocalDate.now(), "message1", StopWatchEntry.DlName.Monica, StopWatchEntry.Worktype.WSUPP, "10");
//                    List<StopWatchEntry> entries = new ArrayList<>();
            // LocalDate dateOfTask, String selectedDL, String taskDescr, long sumOfMinutes, DlName dlName, Worktype workType
//entries.add(new StopWatchEntry(LocalDate.now(), "message1", StopWatchEntry.DlName.Monica, StopWatchEntry.Worktype.WSUPP, "10"));
//entries.add(new StopWatchEntry(LocalDate.now(), "message2", StopWatchEntry.DlName.DiesUndDas, StopWatchEntry.Worktype.WREAL, "23"));
//entries.add(new StopWatchEntry(LocalDate.now(), "message3", StopWatchEntry.DlName.QPortal, StopWatchEntry.Worktype.WOTHER, "130"));

//            System.out.println("entry1: " + entry1);      
//            stopWatchService.saveAll(entries);
            //stopWatchService.save(entry);
            saveContact(entry);
        }
        );

        Icon deleteIcon = new Icon(VaadinIcon.CLOSE_CIRCLE);
        deleteIcon.setColor("red");
        deleteIcon.getStyle().set("cursor", "pointer");
        deleteIcon.addClickListener(event -> Notification.show("Wird gelöscht..."));
        Icon timer = new Icon(VaadinIcon.TIMER);
        todosList.add(new HorizontalLayout(timer, txtMessage, cmbDlName, cmbWorkType, txtSumOfMinutes, startStopIcon, bookingIcon, deleteIcon));
    }

    private void saveContact(StopWatchEntry entry) {
        //contactService.save();

        //closeEditor();
        this.stopWatchService.save(entry);
        updateList();

    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...ARUR");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grdStopWachEntries.addClassName("contact-grid");
        grdStopWachEntries.setSizeFull();
        //grid.removeColumnByKey("company");
        //grid.setColumns("firstName", "lastName", "email", "status");
        /*
        grdStopWachEntries.addColumn(contact -> {
            StopWatchEntry company = contact.getCompany();
            return company == null ? "-" : company.getName();
          
        }).setHeader("Company");
         */

        grdStopWachEntries.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void updateList() {
        grdStopWachEntries.setItems(stopWatchService.findAll());
        //grid.setItems(stopWatchService.findAll(filterText.getValue()));
    }

    private Div computedDlDiv() {
        Span selectedDL = new Span();
        List<DlEntry> dlEntries = Arrays.asList(
                new DlEntry("DL05333", "JDA Roll-out CH-WINT"),
                new DlEntry("DL05306", "WINT B2B E-Shop"),
                new DlEntry("DL05289", "Support Q-Portal "),
                new DlEntry("DL05274", "Prüfplanerstellungs-Applikation"),
                new DlEntry("DL05221", "MONICA V2.0 WIT-Leistungskontrolle"),
                new DlEntry("DL04995", "ADminator"),
                new DlEntry("DL04981", "JDA Betrieb EA"),
                new DlEntry("DL04980", "JDA Support & Consulting"),
                new DlEntry("DL04956", "WIT Intern - FocusNow"),
                new DlEntry("DL04943", "Support CS Engineering"),
                new DlEntry("DL04876", "Serialnummer CSV-Upload"),
                new DlEntry("DL04777", "Niederlassung Applikation"),
                new DlEntry("DL04774", "Betrieb Servicelandschaft"),
                new DlEntry("DL04726", "SRM Lieferantenportal"),
                new DlEntry("DL04698", "GKB Fiori Apps CR533201"),
                new DlEntry("DL04361", "Support UI5 Apps"),
                new DlEntry("DL04192", "CS P Atlassian Server"),
                new DlEntry("DL03980", "WS1 CH-WINT"),
                new DlEntry("DL03645", "Internes Internal Services-Team BS"),
                new DlEntry("DL03570", "WIT - Ausbildung Lernende"),
                new DlEntry("DL03299", "Kleinanpassungen TMIS EDI WüLo CH "),
                new DlEntry("DL02742", "Wartung & Betrieb / Schnittstelle o"),
                new DlEntry("DL02635", "Internes Internal Services - Teamle"),
                new DlEntry("DL02618", "Support MIS - Monitoring"),
                new DlEntry("DL02617", "Support TMIS - Monitoring"));

        Grid<DlEntry> grdDlList = new Grid<>();
        grdDlList.setSizeFull();
        grdDlList.setItems(dlEntries);
        grdDlList.addColumn(DlEntry::getName).setHeader("Name").setFrozen(true);
        grdDlList.addColumn(DlEntry::getDescritpion).setHeader("Description");
        grdDlList.setColumnReorderingAllowed(true);
        grdDlList.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grdDlList.setSelectionMode(SelectionMode.SINGLE);
        SingleSelect<Grid<DlEntry>, DlEntry> dlSelect = grdDlList.asSingleSelect();
        dlSelect.addValueChangeListener(e -> {
            DlEntry selectedDlEntry = e.getValue();
            Notification.show("Selected DL: " + selectedDlEntry, 2, Notification.Position.MIDDLE);
            selectedDL.setText("-- Selected DL: " + selectedDlEntry);
        });

        Dialog dlgShowDlList = new Dialog();
        dlgShowDlList.add(new Button("Overtake selection"), new Text("Close me with the esc-key or an outside click"), grdDlList);

        dlgShowDlList.setWidth("800px");
        dlgShowDlList.setHeight("1200px");
        Button btnDlList = new Button("Show DL List");
        btnDlList.addClickListener(event -> dlgShowDlList.open());
        return new Div(btnDlList, selectedDL);
    }

}
