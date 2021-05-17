package com.example.application.views.about;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.service.ContactService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    private ContactService contactService;
    private Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();

    public AboutView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("about-view");
        add(extra());
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();
        add(filterText, grid);
        updateList();
    }

    private Component extra() {
        
        VerticalLayout ret = new VerticalLayout();       
        VerticalLayout todosList = new VerticalLayout();
        TextField taskField = new TextField();
        Button addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {

            Checkbox checkbox = new Checkbox(taskField.getValue());
            todosList.add(checkbox);
        });
        ret.add(
                new H1("Vaadin Todo"),
                todosList,
                new HorizontalLayout(
                        taskField,
                        addButton
                )
        );

        return ret;
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...ARUR");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void updateList() {
        // grid.setItems(contactService.findAll());
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

}
