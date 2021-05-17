package com.example.application.views.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard")
//@CssImport("./views/dashboard/dashboard-view.css")
@CssImport("./views/about/about-view.css")
public class DashboardView extends Div {

    private Grid<HealthGridItem> grid = new Grid<>();
    private Grid<HealthGridItem> grid1 = new Grid<>();

    private Chart monthlyVisitors = new Chart();
    private Chart responseTimes = new Chart();
    private final H2 usersH2 = new H2();
    private final H2 eventsH2 = new H2();
    private final H2 conversionH2 = new H2();
    private final H2 fTotal = new H2();
    private final H2 fVerbr = new H2();
    private final H2 fGeplant = new H2();
    private final H2 fRest = new H2();

    public DashboardView() {
        addClassName("dashboard-view");
        Board board = new Board();

        grid.addColumn(HealthGridItem::getCity).setHeader("Wochentag");
        grid.addColumn(HealthGridItem::getTagesZeit).setHeader("Rapp Zeit");
        grid.addColumn(HealthGridItem::getDate).setHeader("Datum").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(HealthGridItem::getTagesZeit).setHeader("Wochentag");
        grid.addColumn(new ComponentRenderer<>(item -> {
            Span span = new Span(item.getStatus());
            span.getElement().getThemeList().add(item.getTheme());
            return span;
        })).setHeader("Status").setFlexGrow(0).setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        WrapperCard gridWrapper = new WrapperCard("wrapper", new Component[]{new H3("Wochenübersicht 2020 KW 19"), grid}, "card");
        responseTimes.getConfiguration().setTitle("Monatszeiten 2020");
        WrapperCard responseTimesWrapper = new WrapperCard("wrapper", new Component[]{responseTimes}, "card");
        board.addRow(gridWrapper, responseTimesWrapper);        

        fTotal.setText("25.0");
        fVerbr.setText("11.0");
        fGeplant.setText("5.0");
        fRest.setText("9.0");

        board.addRow(createBadge("Ferientage TOTAL", fTotal, "primary-text", "Total", "badge"),
                createBadge("Verbrauchte", fVerbr, "success-text", "Verbr", "badge success"),
                createBadge("Geplante", fGeplant, "error-text", "Geplant", "badge error"),
                createBadge("Rest", fRest, "error-text", "Rest", "badge error"));
        
        /*
        board.addRow(createBadge("Users", usersH2, "primary-text", "Current users in the app", "badge"),
                createBadge("Events", eventsH2, "success-text", "Events from the views", "badge success"),
                createBadge("Conversion", conversionH2, "error-text", "User conversion rate", "badge error"));
*/        


  /*      
        monthlyVisitors.getConfiguration().setTitle("Monthly visitors per city");
        monthlyVisitors.getConfiguration().getChart().setType(ChartType.COLUMN);
        WrapperCard monthlyVisitorsWrapper = new WrapperCard("wrapper", new Component[]{monthlyVisitors}, "card");
        board.add(monthlyVisitorsWrapper);
*/
        add(new H2("Dashboard: Auswertungen"), board);

        populateCharts();
    }

    private WrapperCard createBadge(String title, H2 h2, String h2ClassName, String description, String badgeTheme) {
        Span titleSpan = new Span(title);
        titleSpan.getElement().setAttribute("theme", badgeTheme);

        h2.addClassName(h2ClassName);

        Span descriptionSpan = new Span(description);
        descriptionSpan.addClassName("secondary-text");

        return new WrapperCard("wrapper", new Component[]{titleSpan, h2, descriptionSpan}, "card", "space-m");
    }

    private void populateCharts() {
        // Set some data when this view is displayed.

        // Top row widgets
        usersH2.setText("745");
        eventsH2.setText("54.6k");
        conversionH2.setText("18%");

        // First chart
        Configuration configuration = monthlyVisitors.getConfiguration();
        configuration.addSeries(new ListSeries("SOLL", 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4,
                194.1, 95.6, 54.4));
        configuration.addSeries(
                new ListSeries("IST", 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3));
        /*
        configuration.addSeries(
                new ListSeries("London", 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
        configuration.addSeries(
                new ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));
*/
        XAxis x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        configuration.addyAxis(y);

        // Grid
        List<HealthGridItem> gridItems = new ArrayList<>();
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "MO", "7.25", "Failing", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "DI", "9.00", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "MI", "8.00", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "DO", "0.00", "Failing", "badge success"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "FR", "11.00", "Excellent", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "SA", "0.00", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2019, 1, 14), "SO", "0.00", "Good", "badge"));

        grid.setItems(gridItems);

        // Second chart
        configuration = responseTimes.getConfiguration();
        configuration
                .addSeries(new ListSeries("SOLL", 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6));
        configuration
                .addSeries(new ListSeries("IST", 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8));

        x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        configuration.addxAxis(x);

        y = new YAxis();
        y.setMin(0);
        configuration.addyAxis(y);
    }
}