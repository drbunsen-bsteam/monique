package com.example.application.views.serviceentry;

import com.example.application.data.entity.ServiceEntry;
import com.example.application.data.service.ServiceEntryService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "serviceentry", layout = MainView.class)
@PageTitle("ServiceEntry")
@CssImport("./views/about/about-view.css")
public class ServiceEntryView extends Div {
    private ServiceEntryService serviceEntryService;
    private Grid<ServiceEntry> grid = new Grid<>(ServiceEntry.class);
    private TextField filterText = new TextField();

    public ServiceEntryView(ServiceEntryService serviceEntryService) {
        this.serviceEntryService = serviceEntryService;
        addClassName("serviceentry-view");
        add(new Text("Content placeholder"));
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();
        add(filterText, grid);
        updateList();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...ARUR");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grid.addClassName("service-entry-grid");
        grid.setSizeFull();
        //grid.removeColumnByKey("company");
        grid.setColumns("date","dlNbr", "workType", "descritpion",  "duration");
        /*
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
*/
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }
    private void updateList() {
         grid.setItems(serviceEntryService.findAll());
        //grid.setItems(serviceEntryService.findAll(filterText.getValue()));
    }


}
